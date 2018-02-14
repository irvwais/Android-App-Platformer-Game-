package com.example.irving.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Irving on 4/16/2017.
 */

public class Platforms extends GameObject{


    private int speed;
    private Random randSpeed = new Random(); // Random speed of platforms
    private Animation anim = new Animation();
    private Bitmap spritesheet;

    public Platforms(Bitmap res, int x, int y, int w, int h, int numFrames) {
        super.x = x;
        super.y = y;
        height = h;
        width = w;


        // As score increases speed platforms do as well
        speed = 7 + (int) (randSpeed.nextDouble()* 3);

        // Cap Speed of Platform
        if(speed > 15)
            speed = 15;

        Bitmap[] image  = new Bitmap[numFrames];

        spritesheet = res;

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, 0, 0, width, height);
        }

        // Set array to animation class
        anim.setFrames(image);
        anim.setDelay(100 - speed);
    }

    public void update() {
        x -= speed;
        anim.update();
    }

    public void draw (Canvas canvas) {
        try {
            canvas.drawBitmap(anim.getImage(), x, y, null);
        } catch (Exception e) {}
    }

    @Override
    public int getHeight() {
        //offset so that collision is only on top of Platform
        return height + 89;
    }
}
