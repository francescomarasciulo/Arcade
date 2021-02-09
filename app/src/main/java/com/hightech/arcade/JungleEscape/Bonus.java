package com.hightech.arcade.JungleEscape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

public class Bonus {
    private Bitmap bitmap;
    private int x;
    private int y;
    Context context;

    private Rect detectCollision;

    public Bonus(Context context) {
        this.context = context;

    }

    public void update() {
        y += 15;
    }


    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    void setBitmap (Bitmap banana) {
        this.bitmap=banana;
    }

    void setX (int x) {
        this.x=x;
    }

    void setY (int y) {
        this.y=y;
    }

    void setDetectCollision (Rect collision) {
        this.detectCollision = collision;
    }


}
