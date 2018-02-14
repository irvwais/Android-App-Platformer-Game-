package com.example.irving.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;


/**
 * Created by Irving on 4/14/2017.
 */

public class Player extends  GameObject{
    private Bitmap spritesheet;
    private double dya; // acceleration
    private boolean jump;
    private boolean gravity;
    private boolean playing;
    private Animation anim = new Animation();
    private long startTime;

    public Player (Bitmap res, int w, int h, int numFrames){
        x = 100;
        y = GameView.HEIGHT ;
        dy = 0;
        height = h;
        width = w;

        // Storing of all Sprites in the image
        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;
        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
        }

        anim.setFrames(image);
        anim.setDelay(50);
        startTime = System.nanoTime();
    }

    public void setGravity (boolean b) {
        gravity = b;
    }

    public void setJump (boolean b) {
        jump = b;
    }

    public void update() {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if (elapsed > 100) {

            startTime = System.nanoTime();
        }
        anim.update();

        if (gravity) {
            dy += 5;
        }

        if (jump) {
            dy -= 10;
        }

        if (dy > 7)
            dy = 7;
        if (dy < -7)
            dy = -7;

        y += dy*2;
        dy = 0;
    }

    public void draw (Canvas canvas) {
        canvas.drawBitmap(anim.getImage(), x, y, null);
    }

    public boolean getPlaying() {
        return playing;
    }

    public void setPlaying (boolean b) {
        playing = b;
    }

    public void resetDYA() {
        dy = 0;
    }

}
