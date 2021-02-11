package com.hightech.arcade.AndroidDoodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import com.hightech.arcade.R;
import java.util.Random;

public class Pickup {
    private Bitmap pick;
    public int bX;
    public int bY;
    public int bMaxX;
    private int bMinX;
    public int bMaxY;
    public int bMinY;
    private Rect bCollision;

    public Pickup(Context context, int screenX, int screenY) {

        pick = BitmapFactory.decodeResource(context.getResources(), R.drawable.doodle_javalogo);
        pick = Bitmap.createScaledBitmap(pick, pick.getWidth() / 7, pick.getHeight() / 7, false);

        bMaxX = screenX - pick.getWidth();
        bMaxY = screenY - pick.getHeight();
        bMinX = 0;
        bMinY = 0;

        Random random = new Random();

        bX = random.nextInt(bMaxX);
        bY = 0 - (pick.getHeight());
        bCollision = new Rect(bX, bY, pick.getWidth(), pick.getHeight());
    }

    public void update(){
        bY += 7;
        bCollision.left = bX;
        bCollision.top = bY;
        bCollision.right = bX + pick.getWidth() - 50;
        bCollision.bottom = bY + pick.getHeight() - 50;
    }

    public Rect getCollision() {
        return bCollision;
    }

    public Bitmap getBitmap() {
        return pick;
    }

    public int getX() {
        return bX;
    }

    public int getY() {
        return bY;
    }
}
