package com.example.irving.finalproject;

import android.graphics.Rect;

/**
 * Created by Irving on 4/14/2017.
 */

public abstract class GameObject { // Class Abstract used for all Game Objects
    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected int width;
    protected int height;

    public void setX (int x) {
        this.x = x;
    }

    public void setY (int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth () {
        return width;
    }

    public void setHeight (int height) { this.height = height; }

    public void setWidth (int width) { this.width = width; }

    public Rect getRectangle() {
        return new Rect(x, y, x + width, y + height);
    }



}
