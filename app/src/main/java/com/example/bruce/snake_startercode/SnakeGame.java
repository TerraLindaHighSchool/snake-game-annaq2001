package com.example.bruce.snake_startercode;

import java.util.ArrayList;
import java.util.List;

public class SnakeGame {
  private int mSpriteDim, mBOARD_WIDTH, mBOARD_HEIGHT, mMillisDelay, mScore, mLevel, mCountdown, mXMax, mYMax;
  private int[] mAppleCoord;
  private boolean mGameOver = false;
  private List<SnakeSegment> mSnake;
  private List<PivotPoint> mPivotPoints;
  private List<ArrayList> mPoisonAppleCoord;
  
  public SnakeGame(int beginningDirection, int beginningSpriteDim, int beginningX, int beginningY, int width, int height){
      mSpriteDim = beginningSpriteDim;
      mBOARD_WIDTH = width;
      mBOARD_HEIGHT = height;
      mXMax = mBOARD_WIDTH / mSpriteDim;
      mYMax = mBOARD_HEIGHT / mSpriteDim;
      mScore = 0;
      mLevel = 1;
      mCountdown = 10;
      mMillisDelay = 400;
      mAppleCoord = new int[2];
      mSnake = new ArrayList<>();
      mPivotPoints = new ArrayList<>();
      mPoisonAppleCoord = new ArrayList<>();
      mSnake.add(new SnakeSegment(SnakeSegment.BodyPart.HEAD, beginningDirection, beginningX, beginningY));
      mSnake.add(new SnakeSegment(SnakeSegment.BodyPart.BODY, beginningDirection, beginningX - 1, beginningY));
      mSnake.add(new SnakeSegment(SnakeSegment.BodyPart.TAIL, beginningDirection, beginningX - 2, beginningY));
      setAppleCoord();
  }

  protected List<SnakeSegment> getSnake() {return mSnake;}
  protected int getSpriteDim() {return mSpriteDim;}
  protected int getMillisDelay() {return mMillisDelay;}
  protected int[] getAppleCoord() {return mAppleCoord;}
  protected List<ArrayList> getPoisonAppleCoord() {return mPoisonAppleCoord;}
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
      if (newDegrees != degrees && newDegrees != Math.abs(degrees - 180)) mPivotPoints.add(new PivotPoint(xHead, yHead, newDegrees));
  }

  private void setAppleCoord() {
      int x, y;
      boolean sx, sy;
      do {
          sx = false;
          sy = false;
          x = (int) (mXMax * Math.random());
          y = (int) (mYMax * Math.random());
          for (int s = 0; s < mSnake.size(); s++) if (x == mSnake.get(s).getXLoc()) sx = true;
          for (int s = 0; s < mSnake.size(); s++) if (y == mSnake.get(s).getYLoc()) sy = true;
      } while (sx && sy);
      mAppleCoord[0] = x * mSpriteDim;
      mAppleCoord[1] = y * mSpriteDim;
  }

  private void setPoisonAppleCoord() {
      int[] max = {mXMax, mYMax};
      for (int i = 0; i < mPoisonAppleCoord.size(); i++) {
          int x, y;
          boolean sx, sy;
          ArrayList<Integer> coord = new ArrayList<>();
          do {
              sx = false;
              sy = false;
              x = (int) (max[0] * Math.random());
              y = (int) (max[1] * Math.random());
              for (int s = 0; s < mSnake.size(); s++) if (x == mSnake.get(s).getXLoc()) sx = true;
              for (int s = 0; s < mSnake.size(); s++) if (y == mSnake.get(s).getYLoc()) sy = true;
              for (int s = 0; s < i; s++) if (Integer.valueOf(x) == mPoisonAppleCoord.get(s).get(0)) sx = true;
              for (int s = 0; s < i; s++) if (Integer.valueOf(y) == mPoisonAppleCoord.get(s).get(1)) sy = true;
              if (x == mAppleCoord[0]) sx = true;
              if (y == mAppleCoord[1]) sy = true;
          } while (sx && sy);
          coord.add(x * mSpriteDim);
          coord.add(y * mSpriteDim);
          mPoisonAppleCoord.set(i, coord);
      }
  }
    private void addPoisonAppleCoord() {
        int[] max = {mXMax, mYMax};
            int x, y;
            boolean sx, sy;
            ArrayList<Integer> coord = new ArrayList<>();
            do {
                sx = false;
                sy = false;
                x = (int) (max[0] * Math.random());
                y = (int) (max[1] * Math.random());
                for (int s = 0; s < mSnake.size(); s++) if (x == mSnake.get(s).getXLoc()) sx = true;
                for (int s = 0; s < mSnake.size(); s++) if (y == mSnake.get(s).getYLoc()) sy = true;
                for (int s = 0; s < mPoisonAppleCoord.size(); s++) if (Integer.valueOf(x) == mPoisonAppleCoord.get(s).get(0)) sx = true;
                for (int s = 0; s < mPoisonAppleCoord.size(); s++) if (Integer.valueOf(y) == mPoisonAppleCoord.get(s).get(1)) sy = true;
                if (x == mAppleCoord[0]) sx = true;
                if (y == mAppleCoord[1]) sy = true;
            } while (sx && sy);
            coord.add(x * mSpriteDim);
            coord.add(y * mSpriteDim);
            mPoisonAppleCoord.add(coord);
    }

  private void eatApple() {
      if (mSnake.get(0).getXLoc() == mAppleCoord[0] / mSpriteDim && mSnake.get(0).getYLoc() == mAppleCoord[1] / mSpriteDim) {
          setAppleCoord();
          setPoisonAppleCoord();
          growSnake();
          updateScoring();
      }
  }

  private void eatPoisonApple() {
      for (int i = 0; i < mPoisonAppleCoord.size(); i++) {
          if (mSnake.get(0).getXLoc() == Integer.parseInt(mPoisonAppleCoord.get(i).get(0).toString()) / mSpriteDim
                  && mSnake.get(0).getYLoc() == Integer.parseInt(mPoisonAppleCoord.get(i).get(1).toString()) / mSpriteDim) {
              mGameOver = true;
          }
      }
  }

  private void growSnake() {
      mSnake.add(mSnake.size() - 1, new SnakeSegment(SnakeSegment.BodyPart.BODY, mSnake.get(mSnake.size() - 1).getDegrees(), mSnake.get(mSnake.size() - 1).getXLoc(), mSnake.get(mSnake.size() - 1).getYLoc()));
      switch (mSnake.get(mSnake.size() - 1).getDegrees()) {
          case 0: mSnake.get(mSnake.size() - 1).setXLoc(mSnake.get(mSnake.size() - 1).getXLoc() - 1); break;
          case 90: mSnake.get(mSnake.size() - 1).setYLoc(mSnake.get(mSnake.size() - 1).getYLoc() - 1); break;
          case 180: mSnake.get(mSnake.size() - 1).setXLoc(mSnake.get(mSnake.size() - 1).getXLoc() + 1); break;
          case 270: mSnake.get(mSnake.size() - 1).setYLoc(mSnake.get(mSnake.size() - 1).getYLoc() + 1); break;
      }
  }

  private void updateScoring() {
      mScore += (int) Math.ceil((float) mLevel / 3);
      mCountdown--;
      if (mCountdown == 0) updateLevel();
  }

  private void updateLevel() {
      /*********************************
       * LEVEL EXTENSION DETAILS
       * - the apple countdown increases by 1 every 3 levels
       * - the snake moves faster by 20 milliseconds every level
       * - the snake gets smaller by 5 pixels every 5 levels
       * - a new poison apple appears every 5 levels
       * - the location of existing apples changes every time an apple is eaten
       *********************************/
      mLevel++;
      mCountdown = 10 + (mLevel / 3);
      if (mMillisDelay > 100) mMillisDelay -= 20;
      if (mLevel % 5 == 1 && mSpriteDim > 40) {
          mSpriteDim -= 5;
          mXMax = mBOARD_WIDTH / mSpriteDim;
          mYMax = mBOARD_HEIGHT / mSpriteDim;
      }
      if (mLevel % 5 == 0) addPoisonAppleCoord();
  }

  protected boolean play(){
      eatApple();
      for (int i = 0; i < mSnake.size(); i++) {
          int xLoc = mSnake.get(i).getXLoc();
          int yLoc = mSnake.get(i).getYLoc();
          int origdegrees = mSnake.get(i).getDegrees();
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
                  if (origdegrees == degrees && !mGameOver && xLoc + 1 > mXMax) return true;
                  mSnake.get(i).setXLoc(++xLoc);
                  break;
              case 180:
                  if (origdegrees == degrees && !mGameOver && xLoc - 1 < 0) return true;
                  mSnake.get(i).setXLoc(--xLoc);
                  break;
              case 90:
                  if (origdegrees == degrees && !mGameOver && yLoc + 1 > mYMax) return true;
                  mSnake.get(i).setYLoc(++yLoc);
                  break;
              case 270:
                  if (origdegrees == degrees && !mGameOver && yLoc - 1 < 0) return true;
                  mSnake.get(i).setYLoc(--yLoc);
                  break;
          }
          if (!mGameOver) mGameOver = (xLoc > mXMax || yLoc > mYMax || xLoc < 0 || yLoc < 0);
          if (i != 0 && mSnake.get(0).getXLoc() == mSnake.get(i).getXLoc() && mSnake.get(0).getYLoc() == mSnake.get(i).getYLoc()) mGameOver = true;
      }
      eatPoisonApple();
      return mGameOver;
  }
}
