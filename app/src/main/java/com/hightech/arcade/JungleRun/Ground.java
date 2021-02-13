package com.hightech.arcade.JungleRun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import com.hightech.arcade.R;

public class Ground {
    private int groundHeight = (int) (GameView.screenY / 5.4);
    private int groundWidth = (int) (GameView.screenX / 1.1);
    private int groundXPosition = (int) (GameView.screenX / 19.2);
    private int groundYPosition = (int) (GameView.screenY / 1.4);
    private int groundDistance = (int) (GameView.screenX / 9.6);
    private boolean isColliding = false;

    private Rect frameToDraw = new Rect (0, 0, groundWidth, groundHeight);
    private Rect whereToDraw = new Rect (groundXPosition, groundYPosition, groundXPosition + groundWidth, groundYPosition + groundHeight);

    Bitmap bitmapStartingGround, bitmapGround1, bitmapGround2, bitmapGround3, bitmapGround4;

    Ground(Resources res) {
        bitmapStartingGround = BitmapFactory.decodeResource(res, R.drawable.junglerun2d_groundstart);
        bitmapStartingGround = Bitmap.createScaledBitmap(bitmapStartingGround, groundWidth, groundHeight, false);

        bitmapGround1 = BitmapFactory.decodeResource(res, R.drawable.junglerun2d_ground1);
        bitmapGround1 = Bitmap.createScaledBitmap(bitmapGround1, groundWidth, groundHeight, false);

        bitmapGround2 = BitmapFactory.decodeResource(res, R.drawable.junglerun2d_ground2);
        bitmapGround2 = Bitmap.createScaledBitmap(bitmapGround2, groundWidth, groundHeight, false);

        bitmapGround3 = BitmapFactory.decodeResource(res, R.drawable.junglerun2d_ground3);
        bitmapGround3 = Bitmap.createScaledBitmap(bitmapGround3, groundWidth, groundHeight, false);

        bitmapGround4 = BitmapFactory.decodeResource(res, R.drawable.junglerun2d_ground4);
        bitmapGround4 = Bitmap.createScaledBitmap(bitmapGround4, groundWidth, groundHeight, false);
    }

    public void setGroundWidth(int groundWidth) {
        this.groundWidth = groundWidth;
    }

    public int getGroundWidth() {
        return groundWidth;
    }

    public int getGroundHeight() {
        return groundHeight;
    }

    public void setScrollingGround(int scrollingSpeed) {
        groundXPosition -= scrollingSpeed;
        whereToDraw.set(groundXPosition, groundYPosition, groundXPosition + groundWidth, groundYPosition + groundHeight);
    }

    public void setGroundXPosition(int groundXPosition) {
        this.groundXPosition = groundXPosition;
        whereToDraw.set(this.groundXPosition, groundYPosition, this.groundXPosition + groundWidth, groundYPosition + groundHeight);
    }

    public int getGroundXPosition() {
        return groundXPosition;
    }

    public void setGroundYPosition(int groundYPosition) {
        this.groundYPosition = groundYPosition;
        whereToDraw.set(groundXPosition, this.groundYPosition, groundXPosition + groundWidth, this.groundYPosition + groundHeight);
    }

    public int getGroundYPosition() {
        return groundYPosition;
    }

    public int getGroundDistance() {
        return groundDistance;
    }

    public void setIsColliding(boolean isColliding) {
        this.isColliding = isColliding;
    }

    public boolean getIsColliding() {
        return isColliding;
    }

    public Rect getFrameToDraw() {
        return frameToDraw;
    }

    public Rect getWhereToDraw() {
        return whereToDraw;
    }

}
