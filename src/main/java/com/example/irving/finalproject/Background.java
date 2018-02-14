package com.example.irving.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Irving on 4/14/2017.
 */

public class Background {

    private Bitmap backGround;
    private  int x, y, dx;

    private int mBGFarMoveX = 0;
    private int mBGNearMoveX = 0;

    public Background (Bitmap res) {
        backGround = res;
        dx = GameView.MOVESPEED;
    }

    public void draw (Canvas canvas) {
        // decrement the far background
        mBGFarMoveX = mBGFarMoveX - 1;

        // decrement the near background
        mBGNearMoveX = mBGNearMoveX - 4;

        // calculate the wrap factor for matching image draw
        int newFarX = backGround.getWidth() - (-mBGFarMoveX);

        // if we have scrolled all the way, reset to start
        if (newFarX <= 0) {
            mBGFarMoveX = 0;
            // only need one draw
            canvas.drawBitmap(backGround, mBGFarMoveX, 0, null);

        } else {
            // need to draw original and wrap
            canvas.drawBitmap(backGround, mBGFarMoveX, 0, null);
            canvas.drawBitmap(backGround, newFarX, 0, null);
        }
    }


}
