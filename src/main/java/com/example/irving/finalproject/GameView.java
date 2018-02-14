package com.example.irving.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Irving on 4/13/2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    // Game View Variables
    public static final int WIDTH = 1800;
    public static final int HEIGHT = 950;
    public static final int MOVESPEED = -5;
    private long objectStartTime;
    private long buttonStartTime;
    private boolean newGameCreated; // check game to make new
    private boolean buttonDown;
    private Context gameContext;
    private Intent intentToGameOver;
    private Random rand = new Random();
    private int coinScore;

    // Gameplay Audio
    private MediaPlayer jumpSound, coinCollect;

    // Reference to other Classes Variables
    private GameOverActivity gameOver;
    private GameLoop thread;
    private Background bg;
    private Player player;
    private ArrayList<Platforms> platforms;
    private ArrayList<Coins>coins;
    private ArrayList<DeathSprite> deadSplat;

    public GameView(Context context) {
        super(context);

        gameContext = context;
        // Grap Audio Files that will paly in Game View
        jumpSound = MediaPlayer.create(context, R.raw.jump);
        coinCollect = MediaPlayer.create(context, R.raw.coincollect);

        //add callback to the surfaceHolder to intercept events
        getHolder().addCallback(this);

        thread = new GameLoop(getHolder(), this);

        // make gameView focusable so it can handle events
        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        // grab background image
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.bg));
        // grab player image and split into each frame
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.playerspritesheet02), 150, 150, 27);
        // Grab Platform Image
        platforms = new ArrayList<Platforms>();
        objectStartTime = System.nanoTime();
        // Grab Coin Image
        coins = new ArrayList<Coins>();
        // Grab Death Sprite Image
        deadSplat = new ArrayList<DeathSprite>();

        // Start Game Loop on successful surface Created
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Stop Game Loop on destroy
        boolean retry = true;
        // prevent infinite loop
        int counter = 0;
        while (retry && counter < 1000) {
            counter++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {

        // When Finger is pressed down player jumps up
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            buttonDown = true; // Set Button Down to call gravity if statement in Update
            if (!player.getPlaying()) {
                player.setPlaying(true);
            }
            else {
                player.setGravity(true);
                player.setJump(true);
                jumpSound.start();
            }
            return true;
        }
        // When finger is not pressed player falls down
        if (event.getAction() == MotionEvent.ACTION_UP  ) {
            buttonDown = false;
            player.setGravity(true);
            player.setJump(false);
            return true;
        }


        return super.onTouchEvent(event);
    }

    public void update() {
        if (player.getPlaying()) {
            player.update();

            // Check if Button is Being held down for 1.5s
            long buttonDownTime = (System.nanoTime() - buttonStartTime) / 1000000;
            // Check if Button is Being held down for 1.5s or Player has reached top of boarder
            if (buttonDown && buttonDownTime > 1500 || player.y < 0) {
                player.setGravity(true);
                player.setJump(false);
            }

            // add platforms on timer
            long platformElapsed = (System.nanoTime() - objectStartTime) / 1000000;
            if (platformElapsed >= (500)) {
                // first four platform always spawn on screen
                if (platforms.size() == 0) {
                    platforms.add(new Platforms(BitmapFactory.decodeResource(getResources(), R.drawable.
                            platform), 100, HEIGHT -100, 450, 90,  1));
                }
                if (platforms.size() == 1) {
                    platforms.add(new Platforms(BitmapFactory.decodeResource(getResources(), R.drawable.
                            platform), 800, HEIGHT -300, 450, 90,  1));
                }
                if (platforms.size() == 2) {
                    platforms.add(new Platforms(BitmapFactory.decodeResource(getResources(), R.drawable.
                            platform), 1300, HEIGHT -600, 450, 90,  1));
                }
                if (platforms.size() == 3) {
                    platforms.add(new Platforms(BitmapFactory.decodeResource(getResources(), R.drawable.
                            platform), 1800, HEIGHT -250, 450, 90,  1));
                }
            }
            // Spawn the rest of the platforms and Coins off screen after 3 seconds
            if (platformElapsed > (3000)){
                platforms.add(new Platforms(BitmapFactory.decodeResource(getResources(), R.drawable.
                        platform), WIDTH + 100, (int) (rand.nextDouble() * (700)), 450, 90,  1));

                coins.add(new Coins(BitmapFactory.decodeResource(getResources(), R.drawable.
                        coins), WIDTH + 100, (int) (rand.nextDouble() * (700)), 60, 60,  10));

                // reset timer
                objectStartTime = System.nanoTime();
            }

            // loop through every platform and call update
            for (int j = 0; j < platforms.size(); j++) {
                platforms.get(j).update();
                // loop through every platform and check collision
                for (int i = 0; i < platforms.size(); i++) {
                    // update platform
                    if (collision(platforms.get(i), player)) {
                        player.setGravity(false);
                        buttonStartTime = System.nanoTime(); // Reset Timer to allow player to jump only if they collide with the platform
                        break;
                    } else {
                        player.setGravity(true);
                    }
                    // if platform leaves the screen remove it
                    if (platforms.get(i).getX() < -700) {
                        platforms.remove(i);
                        break;
                    }
                }
            }

            // Update the coins and Check collision
            for (int i = 0; i < coins.size(); i++) {
                coins.get(i).update();
                if (collision (coins.get(i), player)) {
                    coinCollect.start();
                    coins.remove(i);
                    coinScore++;
                    break;
                }
            }

            // If player hits spikes kill him and call Game Over Activity
            if (player.y > 830) {
                player.setPlaying(false);
                // Display Death Sprite
                deadSplat.add(new DeathSprite(BitmapFactory.decodeResource(getResources(), R.drawable.splat), player.x, player.y, 100, 100, 1));
                //Call Class Activity for Game Over Screen and grab Last Coin Score
                intentToGameOver = new Intent (gameContext, GameOverActivity.class);
                intentToGameOver.putExtra("SCORE", coinScore);
                // Add score to addScore Method in High Score Activity
                HighScoresActivity.addScore(coinScore);
                gameContext.startActivity(intentToGameOver);
            }
        }
        else{
            newGameCreated = false;
            if(!newGameCreated) {
                newGame();
            }
        }
    }

    public boolean collision (GameObject a, GameObject b) {
        if (Rect.intersects(a.getRectangle(), b.getRectangle())) {
            return true;
        }
        return false;
    }

    @Override
    public void draw (Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(40);

        if (canvas != null) {
            bg.draw(canvas);
            player.draw(canvas);
            for (Platforms p: platforms) {
                p.draw(canvas);
            }
            for (Coins c: coins) {
                c.draw(canvas);
            }
            for (DeathSprite d: deadSplat) {
                d.draw(canvas);
            }
            canvas.drawText("Coins: " + coinScore, 0, 50, paint);
        }
    }

    public void newGame()
    {
        platforms.clear();
        coins.clear();
        deadSplat.clear();

        coinScore = 0;
        player.resetDYA();
        player.setY(HEIGHT/2);

        newGameCreated = true;
    }
}
