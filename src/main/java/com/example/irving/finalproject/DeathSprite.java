package com.example.irving.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;

/**
 * Created by Irving on 4/17/2017.
 */

public class DeathSprite extends GameObject{

    private Animation anim = new Animation();
    private Bitmap spritesheet;

    public DeathSprite(Bitmap res, int x, int y, int w, int h, int numFrames) {
        super.x = x;
        super.y = y;
        height = h;
        width = w;

        Bitmap[] image  = new Bitmap[numFrames];
        spritesheet = res;

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, 0, 0, width, height);
        }
        anim.setFrames(image);
        anim.setDelay(100);
    }

    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(anim.getImage(), x, y, null);
        } catch (Exception e) {}
    }

    private void update() {
        anim.update();
    }
}
