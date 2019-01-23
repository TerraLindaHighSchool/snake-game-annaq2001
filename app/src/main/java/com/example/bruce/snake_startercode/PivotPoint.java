package com.example.bruce.snake_startercode;

public class PivotPoint {

    private int mX, mY, mDegrees;

    public PivotPoint(int x, int y, int degrees) {
        mX = x;
        mY = y;
        mDegrees = degrees;
    }

    protected int getX() {return mX;}
    protected int getY() {return mY;}
    protected int getDegrees() {return mDegrees;}

}
