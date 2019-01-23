package com.example.bruce.snake_startercode;

import java.util.ArrayList;
import java.util.List;

public class SnakeGame {
  private int mSpriteDim, mBOARD_WIDTH, mBOARD_HEIGHT, mMillisDelay, mScore, mLevel, mCountdown, mXMax, mYMax;
  private int[] mAppleCoord;
  private boolean mGameOver = false;
  private List<SnakeSegment> mSnake;
  private List<PivotPoint> mPivotPoints;
  
  public SnakeGame(int beginningDirection, int beginningSpriteDim, int beginningX, int beginningY, int width, int height){
      mSpriteDim = beginningSpriteDim;
      mBOARD_WIDTH = width;
      mBOARD_HEIGHT = height;
      mXMax = mBOARD_WIDTH / mSpriteDim - 1;
      mYMax = mBOARD_HEIGHT / mSpriteDim - 1;
      mScore = 0;
      mLevel = 1;
      mCountdown = 12;
      mMillisDelay = 400;
      mAppleCoord = new int[2];
      mSnake = new ArrayList<>();
      mPivotPoints = new ArrayList<>();
      mSnake.add(new SnakeSegment(SnakeSegment.BodyPart.HEAD, beginningDirection, beginningX, beginningY));
      mSnake.add(new SnakeSegment(SnakeSegment.BodyPart.BODY, beginningDirection, beginningX - 1, beginningY));
      mSnake.add(new SnakeSegment(SnakeSegment.BodyPart.TAIL, beginningDirection, beginningX - 2, beginningY));
      setAppleCoord();
  }

  protected List<SnakeSegment> getSnake() {return mSnake;}
  protected int getSpriteDim() {return mSpriteDim;}
  protected int getMillisDelay() {return mMillisDelay;}
  protected int[] getAppleCoord() {return mAppleCoord;}
  protected int getScore() {return mScore;}
  protected int getLevel() {return mLevel;}
  protected int getCountdown() {return mCountdown;}
  protected boolean getGameOver() {return mGameOver;}
  
  protected void touched(float xTouched, float yTouched){
    int degrees = mSnake.get(0).getDegrees();
    int xHead = mSnake.get(0).getXLoc();
    int yHead = mSnake.get(0).getYLoc();
    int newDegrees = degrees;
    if (degrees % 180 == 0) {
        if (yTouched > (yHead * mSpriteDim)) newDegrees = 90;
        else if (yTouched < ((yHead + 1) * mSpriteDim)) newDegrees = 270;
    }
    else if ((degrees + 90) % 180 == 0) {
        if (xTouched > ((xHead + 1) * mSpriteDim)) newDegrees = 0;
        else if (xTouched < (xHead * mSpriteDim)) newDegrees = 180;
    }
    if (newDegrees != degrees) mPivotPoints.add(new PivotPoint(xHead, yHead, newDegrees));
  }

  private void setAppleCoord() {
      mAppleCoord[0] = (int) (mXMax * Math.random()) * mSpriteDim;
      mAppleCoord[1] = (int) (mYMax * Math.random()) * mSpriteDim;
  }

  protected void eatApple(){
  
  }
    
  protected boolean play(){
      for (int i = 0; i < mSnake.size(); i++) {
          int xLoc = mSnake.get(i).getXLoc();
          int yLoc = mSnake.get(i).getYLoc();
          if (mPivotPoints.size() != 0) {
              for (int p = 0; p < mPivotPoints.size(); p++) {
                  if (xLoc == mPivotPoints.get(p).getX() && yLoc == mPivotPoints.get(p).getY()) {
                      mSnake.get(i).setDegrees(mPivotPoints.get(p).getDegrees());
                      if (mSnake.get(i).getBodyPart() == SnakeSegment.BodyPart.TAIL) mPivotPoints.remove(p);
                      break;
                  }
              }
          }
          int degrees = mSnake.get(i).getDegrees();
          switch (degrees) {
              case 0:
                  mSnake.get(i).setXLoc(++xLoc);
                  break;
              case 180:
                  mSnake.get(i).setXLoc(--xLoc);
                  break;
              case 90:
                  mSnake.get(i).setYLoc(++yLoc);
                  break;
              case 270:
                  mSnake.get(i).setYLoc(--yLoc);
                  break;
          }
          if (!mGameOver) mGameOver = (xLoc > mXMax || yLoc > mYMax || xLoc < 1 || yLoc < 1);
      }
      return mGameOver;
  }
}
