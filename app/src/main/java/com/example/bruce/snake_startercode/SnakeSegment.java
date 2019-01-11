package com.example.bruce.snake_startercode;

public class SnakeSegment {

    enum BodyPart {
        HEAD, BODY, TAIL;
    }

    private BodyPart mBodyPart;
    private int mDegrees, mXLoc, mYLoc;

    public SnakeSegment(BodyPart bodyPart, int degrees, int xLoc, int yLoc) {
        mBodyPart = bodyPart;
        mDegrees = degrees;
        mXLoc = xLoc;
        mYLoc = yLoc;
    }

    protected BodyPart getBodyPart() {return mBodyPart;}
    protected int getDegrees() {return mDegrees;}
    protected void setDegrees(int degrees) {mDegrees = degrees;}
    protected int getXLoc() {return mXLoc;}
    protected void setXLoc(int xLoc) {mXLoc = xLoc;}
    protected int getYLoc() {return mYLoc;}
    protected void setYLoc(int yLoc) {mYLoc = yLoc;}

}
