package com.example.bruce.snake_startercode;

import java.util.ArrayList;
import java.util.List;

public class SnakeGame {
  private int mBeginningDirection, mSpriteDim, mBOARD_WIDTH, mBOARD_HEIGHT, mMillisDelay, mScore, mLevel, mCountdown, mXMax, mYMax;
  private int[] mAppleCoord;
  private boolean mGameOver = false;
  private List<SnakeSegment> mSnake;
  
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
  
  }

  private void setAppleCoord() {
      mAppleCoord[0] = (int) (mXMax * Math.random()) * mSpriteDim;
      mAppleCoord[1] = (int) (mYMax * Math.random()) * mSpriteDim;
  }

  protected void eatApple(){
  
  }
    
  protected boolean play(){
      for (int i = 0; i < mSnake.size(); i++) {
          int degrees = mSnake.get(i).getDegrees();
          int xLoc = mSnake.get(i).getXLoc();
          int yLoc = mSnake.get(i).getYLoc();
          switch (degrees) {
              case 0:
              case 180:
                  mSnake.get(i).setXLoc(++xLoc);
                  break;
              case 90:
              case 270:
                  mSnake.get(i).setYLoc(++yLoc);
                  break;
          }
          if (!mGameOver) mGameOver = (xLoc > mXMax || yLoc > mYMax);
      }
      return mGameOver;
  }
}
