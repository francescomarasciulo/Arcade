package com.hightech.arcade.JungleRun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import com.hightech.arcade.R;

public class Background {
    private int backgroundWidth;
    private int backgroundHeight;
    private int backgroundXPos = 0;
    private int backgroundYPos = 0;

    Bitmap bitmapBackground;
    private Rect frameToDraw = new Rect (0, 0, backgroundWidth, backgroundHeight);
    private Rect whereToDraw = new Rect (backgroundXPos, backgroundYPos, backgroundXPos + backgroundWidth, backgroundYPos + backgroundHeight);

    Background(int screenX, int screenY, Resources res) {
        backgroundWidth = screenX;
        backgroundHeight = screenY;
        setFrameToDraw();
        bitmapBackground = BitmapFactory.decodeResource(res, R.drawable.junglerun2d_background);
        bitmapBackground = Bitmap.createScaledBitmap(bitmapBackground, backgroundWidth, backgroundHeight, false);
    }

    public int getBackgroundWidth() {
        return backgroundWidth;
    }

    public void setBackgroundXPos(int backgroundXPos) {
        this.backgroundXPos = backgroundXPos;
        whereToDraw.set(this.backgroundXPos, backgroundYPos, this.backgroundXPos + backgroundWidth, backgroundYPos + backgroundHeight);
    }

    public int getBackgroundXPos() {
        return backgroundXPos;
    }

    public void setScrollingBackground(int scrollingSpeed) {
        backgroundXPos -= scrollingSpeed;
        whereToDraw.set(backgroundXPos, backgroundYPos, backgroundXPos + backgroundWidth, backgroundYPos + backgroundHeight);
    }

    public Rect getFrameToDraw() {
        return frameToDraw;
    }

    public void setFrameToDraw() {
        frameToDraw.set(0, 0, backgroundWidth, backgroundHeight);
    }

    public Rect getWhereToDraw() {
        return whereToDraw;
    }

}
