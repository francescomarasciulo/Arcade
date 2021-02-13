package com.hightech.arcade.JungleRun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import com.hightech.arcade.R;

public class Coin {
    private int currentFrame = 0;
    private int frameCount = 6;
    private int frameLengthInMillisecond = 100;
    private long lastFrameChangeTime = 0;
    private boolean isMoving = true;
    private int coinWidth = (int) (GameView.screenX / 24);
    private int coinHeight = (int) (GameView.screenY / 13.5);
    private int coinXPos = (-coinWidth);
    private int coinYPos = 0;
    private int coinGroundDistance = (int) (GameView.screenY / 10.8);

    Bitmap bitmapCoin;
    private Rect frameToDraw = new Rect (0, 0, coinWidth, coinHeight);
    private Rect whereToDraw = new Rect (coinXPos, coinYPos, coinXPos + coinWidth, coinYPos + coinHeight);

    Coin(Resources res) {
        bitmapCoin = BitmapFactory.decodeResource(res, R.drawable.junglerun2d_coin);
        bitmapCoin = Bitmap.createScaledBitmap(bitmapCoin, coinWidth * frameCount, coinHeight, false);
    }

    public void manageCurrentFrame() {
        long time = System.currentTimeMillis();
        if(isMoving) {
            if(time > lastFrameChangeTime + frameLengthInMillisecond) {
                lastFrameChangeTime = time;
                currentFrame++;
                if(currentFrame >= frameCount) {
                    currentFrame = 0;
                }
            }
        }
        frameToDraw.left = currentFrame * coinWidth;
        frameToDraw.right = frameToDraw.left + coinWidth;
    }

    public int getCoinXPos() {
        return coinXPos;
    }

    public void setCoinXPos(int coinXPos) {
        this.coinXPos = coinXPos;
    }

    public int getCoinYPos() {
        return coinYPos;
    }

    public void setCoinYPos(int coinYPos) {
        this.coinYPos = coinYPos;
    }

    public int getCoinWidth() {
        return coinWidth;
    }

    public int getCoinGroundDistance() {
        return coinGroundDistance;
    }

    public void setScrollingCoin(int scrollingSpeed) {
        coinXPos -= scrollingSpeed;
        whereToDraw.set(coinXPos, coinYPos, coinXPos + coinWidth, coinYPos + coinHeight);
    }

    public Rect getFrameToDraw() {
        return frameToDraw;
    }

    public Rect getWhereToDraw() {
        return whereToDraw;
    }

}
