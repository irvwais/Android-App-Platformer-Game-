package com.example.irving.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Irving on 4/17/2017.
 */

public class Coins extends GameObject {
    private Bitmap spritesheet;

    private int speed;
    private Animation anim = new Animation();

    public Coins(Bitmap res, int x, int y, int w, int h,  int numFrames) {
        super.x = x;
        super.y = y;
        height = h;
        width = w;

        speed = 7 ;

        Bitmap[] image  = new Bitmap[numFrames];
        spritesheet = res;

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        // Set array to animation class
        anim.setFrames(image);
        anim.setDelay(100);
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
}
