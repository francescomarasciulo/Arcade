package com.hightech.arcade.JungleEscape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

public class Ramo {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 1;

    Context context;

    //creating a rect object
    private Rect detectCollision;

    public Ramo(Context context) {
        this.context = context;

    }

    public void update() {
        y += speed +10;
    }


    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
    }

    //getters
    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    void setBitmap (Bitmap ramo) {
        this.bitmap=ramo;
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
