package com.hightech.arcade.AndroidDoodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import com.hightech.arcade.R;
import static com.hightech.arcade.AndroidDoodle.DoodleGameView.SCORE;
import java.util.Random;

public class Bug {

    private Bitmap bug;
    public int bX;
    public int bY;
    public int bMaxX;
    private int bMinX;
    public int bMaxY;
    public int bMinY;
    private Rect bCollision;

    public Bug(Context context, int screenX, int screenY) {

        bug = BitmapFactory.decodeResource(context.getResources(), R.drawable.doodle_bug);
        bug = Bitmap.createScaledBitmap(bug, bug.getWidth() / 10+30, bug.getHeight() / 10+30, false);

        bMaxX = screenX - bug.getWidth();
        bMaxY = screenY - bug.getHeight();
        bMinX = 0;
        bMinY = 0;

        Random random = new Random();

        bX = random.nextInt(bMaxX);
        bY = 0 - (bug.getHeight());
        bCollision = new Rect(bX, bY, bug.getWidth(), bug.getHeight());

    }

    public void update(){
        bY += 14;
        if(SCORE>100) bY+=2;
        bCollision.left = bX;
        bCollision.top = bY;
        bCollision.right = bX + bug.getWidth() - 50;
        bCollision.bottom = bY + bug.getHeight() - 50;

    }

    public Rect getCollision() {
        return bCollision;
    }

    public Bitmap getBitmap() {
        return bug;
    }

    public int getX() {
        return bX;
    }

    public int getY() {
        return bY;
    }
}