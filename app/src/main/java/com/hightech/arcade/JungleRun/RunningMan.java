package com.hightech.arcade.JungleRun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import com.hightech.arcade.R;

public class RunningMan {
    private int currentFrame = 0;
    private int frameCount = 10;
    private int frameLengthInMillisecond = 100;
    private long lastFrameChangeTime = 0;
    private boolean isMoving = true;

    private CharacterInfo characterInfo = new CharacterInfo();

    Bitmap bitmapRunning;
    private Rect frameToDraw = new Rect (0, 0, characterInfo.getManWidth(), characterInfo.getManHeight());
    private Rect whereToDraw = new Rect (characterInfo.getManXPos(), CharacterInfo.manYPos, characterInfo.getManXPos() + characterInfo.getManWidth(), CharacterInfo.manYPos + characterInfo.getManHeight());


    RunningMan(Resources res) {
        bitmapRunning = BitmapFactory.decodeResource(res, R.drawable.junglerun2d_runningplayer);
        bitmapRunning = Bitmap.createScaledBitmap(bitmapRunning, characterInfo.getManWidth() * frameCount, characterInfo.getManHeight(), false);
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
        frameToDraw.left = currentFrame * characterInfo.getManWidth();
        frameToDraw.right = frameToDraw.left + characterInfo.getManWidth();
    }

    public void setManYPos(int manYPos) {
        CharacterInfo.manYPos = manYPos;
        whereToDraw.set(characterInfo.getManXPos(), CharacterInfo.manYPos, characterInfo.getManXPos() + characterInfo.getManWidth(), CharacterInfo.manYPos + characterInfo.getManHeight());
    }

    public Rect getWhereToDraw() {
        return whereToDraw;
    }

    public Rect getFrameToDraw() {
        return frameToDraw;
    }

}
