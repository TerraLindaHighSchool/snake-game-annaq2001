package com.example.bruce.snake_startercode;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private ImageView mImageView;
    private TextView mTextScore, mTextHighScore, mTextCountdown;
    private int mBOARD_WIDTH, mBOARD_HEIGHT;
    private SnakeGame mGame;
    private Bitmap mHeadBitmap, mBodyBitmap, mTailBitmap, mAppleBitmap, mPoisonAppleBitmap;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Get View IDs and Dimension Resources
        mImageView = findViewById(R.id.imageView);
        mTextScore = findViewById(R.id.textView_score);
        mTextHighScore = findViewById(R.id.textView_highScore);
        mTextCountdown = findViewById(R.id.textView_level);
        mBOARD_WIDTH = (int) getResources().getDimension(R.dimen.width);
        mBOARD_HEIGHT = (int) getResources().getDimension(R.dimen.height);

        // Instantiate the game
        mGame = new SnakeGame(0, 80, 3, 4, mBOARD_WIDTH, mBOARD_HEIGHT);

        // Add Bitmaps
        mHeadBitmap = BitmapFactory.decodeResource(mImageView.getResources(), R.drawable.head);
        mBodyBitmap = BitmapFactory.decodeResource(mImageView.getResources(), R.drawable.body);
        mTailBitmap = BitmapFactory.decodeResource(mImageView.getResources(), R.drawable.tail);
        mAppleBitmap = BitmapFactory.decodeResource(mImageView.getResources(), R.drawable.apple);
        mPoisonAppleBitmap = BitmapFactory.decodeResource(mImageView.getResources(), R.drawable.poison_apple);

        // Listen for screen touches
        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float xTouch = event.getX();
                float yTouch = event.getY();
                mGame.touched(xTouch, yTouch);
                return false;
            }
        });

        // Snake Animation
        mHandler = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                boolean localStop = mGame.play();
                paintCanvas();
                mHandler.postDelayed(this, mGame.getMillisDelay());
                if (localStop) {
                    mHandler.removeCallbacks(this);
                    showToast("Game Over");
                } else updateAndDeclareScores();
            }
        };
        if (mGame.getGameOver()) mHandler.removeCallbacks(r);
        mHandler.postDelayed(r, mGame.getMillisDelay());
    }

    public void paintCanvas() {
        List<SnakeSegment> snake = mGame.getSnake();
        int[] appleCoord = mGame.getAppleCoord();
        int appleLeft = appleCoord[0];
        int appleTop = appleCoord[1];
        List<ArrayList> poison = mGame.getPoisonAppleCoord();
        System.out.println(appleLeft + ", " + appleTop);
        Bitmap ourBitmap = Bitmap.createBitmap(mBOARD_WIDTH, mBOARD_HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas window = new Canvas(ourBitmap);
        Rect rectangle = null;
        Bitmap currentBitmap = null;
        window.drawColor(Color.BLACK); // Background color

        // Draw Snake
        for (int segment = 0; segment < snake.size(); segment++) {
            rectangle = new Rect(snake.get(segment).getXLoc() * mGame.getSpriteDim(),
                    snake.get(segment).getYLoc() * mGame.getSpriteDim(),
                    (snake.get(segment).getXLoc() + 1) * mGame.getSpriteDim(),
                    (snake.get(segment).getYLoc() + 1) * mGame.getSpriteDim());
            switch (snake.get(segment).getBodyPart()) {
                case HEAD:
                    currentBitmap = mHeadBitmap;
                    break;
                case BODY:
                    currentBitmap = mBodyBitmap;
                    break;
                case TAIL:
                    currentBitmap = mTailBitmap;
                    break;
            }
            if (snake.get(segment).getDegrees() == 0) window.drawBitmap(currentBitmap, null, rectangle, null);
            else window.drawBitmap(rotateBitmap(currentBitmap, snake.get(segment).getDegrees()), null, rectangle, null);
        }

        // Draw Apple
        rectangle = new Rect(appleLeft, appleTop, appleLeft + mGame.getSpriteDim(), appleTop + mGame.getSpriteDim());
        window.drawBitmap(mAppleBitmap, null, rectangle, null);
        for (int i = 0; i < poison.size(); i++) {
            int left = Integer.parseInt(poison.get(i).get(0).toString());
            int top = Integer.parseInt(poison.get(i).get(1).toString());
            rectangle = new Rect(left, top, left + mGame.getSpriteDim(), top + mGame.getSpriteDim());
            window.drawBitmap(mPoisonAppleBitmap, null, rectangle, null);
        }
        mImageView.setImageBitmap(ourBitmap);
    }

    public Bitmap rotateBitmap(Bitmap original, float degrees) {
        int width = original.getWidth();
        int height = original.getHeight();
        Matrix matrix = new Matrix();
        if (degrees == 180) matrix.postScale(-1, 1, width / 2, height / 2);
        else matrix.postRotate(degrees);
        return Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);
    }

    /*****************************************
     * Misc Concrete Methods
     *****************************************/
    protected void showToast(String toast) {
        Toast correct = Toast.makeText(this, toast, Toast.LENGTH_LONG);
        correct.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0 , 320);
        correct.show();
    }

    protected void updateAndDeclareScores() {
        SharedPreferences prefs = getSharedPreferences("Snake", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        int highScore = prefs.getInt("High Score", 0);
        int currentScore = mGame.getScore();
        String score = "Score: " + currentScore;
        mTextScore.setText(score);
        String highScoreStr = "High Score: " + highScore;
        if (currentScore > highScore) {
            editor.putInt("High Score", currentScore);
            editor.commit();
            highScoreStr = "High Score: " + currentScore;
        }
        mTextHighScore.setText(highScoreStr);
        String countdown = "Level " + mGame.getLevel() + " Countdown: " + mGame.getCountdown();
        mTextCountdown.setText(countdown);
    }
}
