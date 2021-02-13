package com.hightech.arcade.JungleRun;

public class CharacterInfo {
    private int manHeight = (int) (GameView.screenY / 5.4);
    private int manWidth = (int) (GameView.screenX / 10.6);
    private int jumpSpeed = (int) (GameView.screenY / 54);
    private int jumpHeight = (int) (GameView.screenY / 3.2);
    private int manXPos = (int) (GameView.screenX / 19.2);
    public static int manYPos = (int) (GameView.screenY / 2.4);

    public int getManHeight() {
        return manHeight;
    }

    public int getManWidth() {
        return manWidth;
    }

    public int getManXPos() {
        return manXPos;
    }

    public int getJumpHeight() {
        return jumpHeight;
    }

    public int getJumpSpeed() {
        return jumpSpeed;
    }
}
