package com.example.bruce.snake_startercode;

import java.util.ArrayList;
import java.util.List;

public class SnakeGame {
  private int mBeginningDirection, mSpriteDim, mBOARD_WIDTH, mBOARD_HEIGHT, mMillisDelay, mWidth, mHeight, mScore, mLevel, mCountdown;
  private int[] mAppleCoord;
  private boolean mGameOver;
  private List<SnakeSegment> mSnake;
  
  public SnakeGame(int beginningDirection, int beginningSpriteDim, int beginningX, int beginningY, int width, int height){
      mSpriteDim = beginningSpriteDim;
      mBOARD_WIDTH = width;
      mBOARD_HEIGHT = height;
      mScore = 0;
      mLevel = 1;
      mCountdown = 12;
      mMillisDelay = 400;
      mAppleCoord = new int[2];
      mSnake = new ArrayList<>();
      mSnake.add(new SnakeSegment(SnakeSegment.BodyPart.HEAD, beginningDirection, beginningX, beginningY));
      mSnake.add(new SnakeSegment(SnakeSegment.BodyPart.BODY, beginningDirection, beginningX - 1, beginningY));
      mSnake.add(new SnakeSegment(SnakeSegment.BodyPart.TAIL, beginningDirection, beginningX - 2, beginningY));
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
    
  protected void eatApple(){
  
  }
    
  protected boolean play(){
        return false;
  }
}
