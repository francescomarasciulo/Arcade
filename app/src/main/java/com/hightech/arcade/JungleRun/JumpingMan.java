package com.hightech.arcade.JungleRun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import com.hightech.arcade.R;

public class JumpingMan {
    private int finalManYPos;
    private int initialManYPos;
    private boolean setInitialManYPos = true;
    private boolean isJumping = false;
    private boolean isFalling = true;

    private CharacterInfo characterInfo = new CharacterInfo();

    Bitmap bitmapJumping;

    private Rect frameToDraw = new Rect (0, 0, characterInfo.getManWidth(), characterInfo.getManHeight());
    private Rect whereToDraw = new Rect (characterInfo.getManXPos(), CharacterInfo.manYPos, characterInfo.getManXPos() + characterInfo.getManWidth(), CharacterInfo.manYPos + characterInfo.getManHeight());

    JumpingMan(Resources res) {
        bitmapJumping = BitmapFactory.decodeResource(res, R.drawable.junglerun2d_jumpingplayer);
        bitmapJumping = Bitmap.createScaledBitmap(bitmapJumping, characterInfo.getManWidth(), characterInfo.getManHeight(), false);
    }

    public void manageJump() {
        if(setInitialManYPos) {
            initialManYPos = CharacterInfo.manYPos;
            finalManYPos = initialManYPos - characterInfo.getJumpHeight();
        }

        if(isJumping) {
            if(CharacterInfo.manYPos > finalManYPos && CharacterInfo.manYPos >= 0 && isFalling == false) {
                CharacterInfo.manYPos -= characterInfo.getJumpSpeed();
                whereToDraw.set(characterInfo.getManXPos(), CharacterInfo.manYPos, characterInfo.getManXPos() + characterInfo.getManWidth(), CharacterInfo.manYPos + characterInfo.getManHeight());
                setInitialManYPos = false;
            }
            else {
                isFalling = true;
                CharacterInfo.manYPos += characterInfo.getJumpSpeed();
                whereToDraw.set(characterInfo.getManXPos(), CharacterInfo.manYPos, characterInfo.getManXPos() + characterInfo.getManWidth(), CharacterInfo.manYPos + characterInfo.getManHeight());
            }
        }

        if(isJumping == false && isFalling == true) {
            CharacterInfo.manYPos += characterInfo.getJumpSpeed();
            whereToDraw.set(characterInfo.getManXPos(), CharacterInfo.manYPos, characterInfo.getManXPos() + characterInfo.getManWidth(), CharacterInfo.manYPos + characterInfo.getManHeight());
        }
    }

    public boolean getIsJumping() {
        return isJumping;
    }

    public void setIsJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    public boolean getIsFalling() {
        return isFalling;
    }

    public void setIsFalling(boolean isFalling) {
        this.isFalling = isFalling;
    }

    public void setSetInitialManYPos(boolean setInitialManYPos) {
        this.setInitialManYPos = setInitialManYPos;
    }

    public Rect getFrameToDraw() {
        return frameToDraw;
    }

    public Rect getWhereToDraw() {
        return whereToDraw;
    }
}
