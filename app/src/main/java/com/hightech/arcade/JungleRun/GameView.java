package com.hightech.arcade.JungleRun;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceView;
import androidx.core.content.res.ResourcesCompat;
import com.hightech.arcade.Global.HomeActivity;
import com.hightech.arcade.Global.SendHighScoreDB;
import com.hightech.arcade.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private boolean gameOver = false;

    public static int screenX, screenY;
    private int scrollingSpeed = 15;
    private int score = 0;

    private SharedPreferences preferences;
    private Paint paint;
    private Sounds sounds;
    private Background background1, background2, background3;
    private CharacterInfo characterInfo;
    private RunningMan runningMan;
    private JumpingMan jumpingMan;
    private Ground startingGround, ground1, ground2, ground3, ground4, lastGroundSpawned;
    private Coin lastCoinSpawned;
    private ArrayList<Coin> coins;
    private Random random = new Random();

    private boolean startingGroundIsSpawned = true, ground1IsSpawned, ground2IsSpawned, ground3IsSpawned, ground4IsSpawned = false;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        preferences = context.getSharedPreferences("partita", Context.MODE_PRIVATE);

        insertHighestScoreDB();

        paint = new Paint();

        sounds = new Sounds(context);

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        background3 = new Background(screenX, screenY, getResources());
        background2.setBackgroundXPos(background1.getBackgroundXPos() + background1.getBackgroundWidth());
        background3.setBackgroundXPos(background2.getBackgroundXPos() + background2.getBackgroundWidth());

        characterInfo = new CharacterInfo();
        runningMan = new RunningMan(getResources());
        jumpingMan = new JumpingMan(getResources());
        startingGround = new Ground(getResources());
        ground1 = new Ground(getResources());
        ground2 = new Ground(getResources());
        ground3 = new Ground(getResources());
        ground4 = new Ground(getResources());
        lastGroundSpawned = new Ground(getResources());
        lastCoinSpawned = new Coin(getResources());
        coins = new ArrayList<>();
    }

    @Override
    public void run() {
        while(isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        if(gameOver == false) {
            sounds.playBackgroundSound();

            background1.setScrollingBackground(scrollingSpeed);
            background2.setScrollingBackground(scrollingSpeed);
            background3.setScrollingBackground(scrollingSpeed);
            if(background1.getBackgroundXPos() + background1.getBackgroundWidth() < 0) {
                background1.setBackgroundXPos(background3.getBackgroundXPos() + background3.getBackgroundWidth());
            }
            if(background2.getBackgroundXPos() + background2.getBackgroundWidth() < 0) {
                background2.setBackgroundXPos(background1.getBackgroundXPos() + background1.getBackgroundWidth());
            }
            if(background3.getBackgroundXPos() + background3.getBackgroundWidth() < 0) {
                background3.setBackgroundXPos(background2.getBackgroundXPos() + background2.getBackgroundWidth());
            }

            if(startingGroundIsSpawned == true) {
                manageStartingGroundCollision();
                startingGround.setScrollingGround(scrollingSpeed);
                if(startingGround.getGroundXPosition() + startingGround.getGroundWidth() < 0) {
                    startingGroundIsSpawned = false;
                }
            }
            if(ground1IsSpawned == true) {
                manageGround1Collision();
                ground1.setScrollingGround(scrollingSpeed);
                if(ground1.getGroundXPosition() + ground1.getGroundWidth() < 0) {
                    ground1IsSpawned = false;
                }
            }
            if(ground2IsSpawned == true) {
                manageGround2Collision();
                ground2.setScrollingGround(scrollingSpeed);
                if(ground2.getGroundXPosition() + ground2.getGroundWidth() < 0) {
                    ground2IsSpawned = false;
                }
            }
            if(ground3IsSpawned == true) {
                manageGround3Collision();
                ground3.setScrollingGround(scrollingSpeed);
                if(ground3.getGroundXPosition() + ground3.getGroundWidth() < 0) {
                    ground3IsSpawned = false;
                }
            }
            if(ground4IsSpawned == true) {
                manageGround4Collision();
                ground4.setScrollingGround(scrollingSpeed);
                if(ground4.getGroundXPosition() + ground4.getGroundWidth() < 0) {
                    ground4IsSpawned = false;
                }
            }

            lastGroundSpawned.setScrollingGround(scrollingSpeed);

            manageCoins();

            manageGroundSpawn();
        }

        gravity();

        gameOver(getContext());
    }

    private void manageStartingGroundCollision() {
        if(jumpingMan.getIsFalling() == true) {
            if((characterInfo.getManXPos() >= startingGround.getWhereToDraw().left - characterInfo.getManWidth() && characterInfo.getManXPos() <= startingGround.getWhereToDraw().right) && (CharacterInfo.manYPos <= startingGround.getWhereToDraw().top - characterInfo.getManHeight() + 20 && CharacterInfo.manYPos >= startingGround.getWhereToDraw().top - characterInfo.getManHeight() - 20)) {
                startingGround.setIsColliding(true);
                ground1.setIsColliding(false);
                ground2.setIsColliding(false);
                ground3.setIsColliding(false);
                ground4.setIsColliding(false);
                jumpingMan.setIsJumping(false);
                jumpingMan.setIsFalling(false);
                jumpingMan.setSetInitialManYPos(true);
                runningMan.setManYPos(startingGround.getWhereToDraw().top - characterInfo.getManHeight() + 20);
            }
        }
        if((characterInfo.getManXPos() >= startingGround.getWhereToDraw().left - characterInfo.getManWidth() && characterInfo.getManXPos() <= startingGround.getWhereToDraw().right - characterInfo.getManWidth()) && (CharacterInfo.manYPos >= startingGround.getWhereToDraw().top)) {
            gameOver = true;
            jumpingMan.setIsJumping(false);
            jumpingMan.setIsFalling(true);
            sounds.stopBackgroundSound();
            sounds.playDeathSound();
        }
    }

    private void manageGround1Collision() {
        if(jumpingMan.getIsFalling() == true) {
            if((characterInfo.getManXPos() >= ground1.getWhereToDraw().left - characterInfo.getManWidth() && characterInfo.getManXPos() <= ground1.getWhereToDraw().right) && (CharacterInfo.manYPos <= ground1.getWhereToDraw().top - characterInfo.getManHeight() + 20 && CharacterInfo.manYPos >= ground1.getWhereToDraw().top - characterInfo.getManHeight() - 20)) {
                startingGround.setIsColliding(false);
                ground1.setIsColliding(true);
                ground2.setIsColliding(false);
                ground3.setIsColliding(false);
                ground4.setIsColliding(false);
                jumpingMan.setIsJumping(false);
                jumpingMan.setIsFalling(false);
                jumpingMan.setSetInitialManYPos(true);
                runningMan.setManYPos(ground1.getWhereToDraw().top - characterInfo.getManHeight() + 20);
            }
        }
        if((characterInfo.getManXPos() >= ground1.getWhereToDraw().left - characterInfo.getManWidth() && characterInfo.getManXPos() <= ground1.getWhereToDraw().right - characterInfo.getManWidth()) && (CharacterInfo.manYPos >= ground1.getWhereToDraw().top)) {
            gameOver = true;
            jumpingMan.setIsJumping(false);
            jumpingMan.setIsFalling(true);
            sounds.stopBackgroundSound();
            sounds.playDeathSound();
        }
    }

    private void manageGround2Collision() {
        if(jumpingMan.getIsFalling() == true) {
            if((characterInfo.getManXPos() >= ground2.getWhereToDraw().left - characterInfo.getManWidth() && characterInfo.getManXPos() <= ground2.getWhereToDraw().right) && (CharacterInfo.manYPos <= ground2.getWhereToDraw().top - characterInfo.getManHeight() + 20 && CharacterInfo.manYPos >= ground2.getWhereToDraw().top - characterInfo.getManHeight() - 20)) {
                startingGround.setIsColliding(false);
                ground1.setIsColliding(false);
                ground2.setIsColliding(true);
                ground3.setIsColliding(false);
                ground4.setIsColliding(false);
                jumpingMan.setIsJumping(false);
                jumpingMan.setIsFalling(false);
                jumpingMan.setSetInitialManYPos(true);
                runningMan.setManYPos(ground2.getWhereToDraw().top - characterInfo.getManHeight() + 20);
            }
        }
        if((characterInfo.getManXPos() >= ground2.getWhereToDraw().left - characterInfo.getManWidth() && characterInfo.getManXPos() <= ground2.getWhereToDraw().right - characterInfo.getManWidth()) && (CharacterInfo.manYPos >= ground2.getWhereToDraw().top)) {
            gameOver = true;
            jumpingMan.setIsJumping(false);
            jumpingMan.setIsFalling(true);
            sounds.stopBackgroundSound();
            sounds.playDeathSound();
        }
    }

    private void manageGround3Collision() {
        if(jumpingMan.getIsFalling() == true) {
            if((characterInfo.getManXPos() >= ground3.getWhereToDraw().left - characterInfo.getManWidth() && characterInfo.getManXPos() <= ground3.getWhereToDraw().right) && (CharacterInfo.manYPos <= ground3.getWhereToDraw().top - characterInfo.getManHeight() + 20 && CharacterInfo.manYPos >= ground3.getWhereToDraw().top - characterInfo.getManHeight() - 20)) {
                startingGround.setIsColliding(false);
                ground1.setIsColliding(false);
                ground2.setIsColliding(false);
                ground3.setIsColliding(true);
                ground4.setIsColliding(false);
                jumpingMan.setIsJumping(false);
                jumpingMan.setIsFalling(false);
                jumpingMan.setSetInitialManYPos(true);
                runningMan.setManYPos(ground3.getWhereToDraw().top - characterInfo.getManHeight() + 20);
            }
        }
        if((characterInfo.getManXPos() >= ground3.getWhereToDraw().left - characterInfo.getManWidth() && characterInfo.getManXPos() <= ground3.getWhereToDraw().right - characterInfo.getManWidth()) && (CharacterInfo.manYPos >= ground3.getWhereToDraw().top)) {
            gameOver = true;
            jumpingMan.setIsJumping(false);
            jumpingMan.setIsFalling(true);
            sounds.stopBackgroundSound();
            sounds.playDeathSound();
        }
    }

    private void manageGround4Collision() {
        if(jumpingMan.getIsFalling() == true) {
            if((characterInfo.getManXPos() >= ground4.getWhereToDraw().left - characterInfo.getManWidth() && characterInfo.getManXPos() <= ground4.getWhereToDraw().right) && (CharacterInfo.manYPos <= ground4.getWhereToDraw().top - characterInfo.getManHeight() + 20 && CharacterInfo.manYPos >= ground4.getWhereToDraw().top - characterInfo.getManHeight() - 20)) {
                startingGround.setIsColliding(false);
                ground1.setIsColliding(false);
                ground2.setIsColliding(false);
                ground3.setIsColliding(false);
                ground4.setIsColliding(true);
                jumpingMan.setIsJumping(false);
                jumpingMan.setIsFalling(false);
                jumpingMan.setSetInitialManYPos(true);
                runningMan.setManYPos(ground4.getWhereToDraw().top - characterInfo.getManHeight() + 20);
            }
        }
        if((characterInfo.getManXPos() >= ground4.getWhereToDraw().left - characterInfo.getManWidth() && characterInfo.getManXPos() <= ground4.getWhereToDraw().right - characterInfo.getManWidth()) && (CharacterInfo.manYPos >= ground4.getWhereToDraw().top)) {
            gameOver = true;
            jumpingMan.setIsJumping(false);
            jumpingMan.setIsFalling(true);
            sounds.stopBackgroundSound();
            sounds.playDeathSound();
        }
    }

    private void manageCoins() {
        for(Iterator<Coin> coinIterator = coins.iterator(); coinIterator.hasNext(); ) {
            Coin coin = coinIterator.next();
            coin.setScrollingCoin(scrollingSpeed);
            if( (Rect.intersects(coin.getWhereToDraw(), jumpingMan.getWhereToDraw())) || (Rect.intersects(coin.getWhereToDraw(), runningMan.getWhereToDraw())) ) {
                coinIterator.remove();
                sounds.playCoinPickSound();
                score += 10;
            }
            if(coin.getCoinXPos() + coin.getCoinWidth() < 0) {
                coinIterator.remove();
            }
        }
    }

    private void gravity() {
        if(characterInfo.getManXPos() >= startingGround.getWhereToDraw().right && jumpingMan.getIsJumping() == false) {
            startingGround.setIsColliding(false);
        }
        if(characterInfo.getManXPos() >= ground1.getWhereToDraw().right && jumpingMan.getIsJumping() == false) {
            ground1.setIsColliding(false);
        }
        if(characterInfo.getManXPos() >= ground2.getWhereToDraw().right && jumpingMan.getIsJumping() == false) {
            ground2.setIsColliding(false);
        }
        if(characterInfo.getManXPos() >= ground3.getWhereToDraw().right && jumpingMan.getIsJumping() == false) {
            ground3.setIsColliding(false);
        }
        if(characterInfo.getManXPos() >= ground4.getWhereToDraw().right && jumpingMan.getIsJumping() == false) {
            ground4.setIsColliding(false);
        }
        if((startingGround.getIsColliding() == false && ground1.getIsColliding() == false && ground2.getIsColliding() == false && ground3.getIsColliding() == false && ground4.getIsColliding() == false) && (jumpingMan.getIsJumping() == false)) {
            jumpingMan.setIsFalling(true);
        }
    }

    private void manageGroundSpawn() {
        int n = random.nextInt(5);
        switch(n) {
            case 1:
                if(ground1IsSpawned == false) {
                    int newGroundYPos = random.nextInt(screenY) + lastGroundSpawned.getGroundYPosition() - characterInfo.getJumpHeight();
                    if(newGroundYPos < (characterInfo.getJumpHeight() / 2) + characterInfo.getManHeight()) {
                        newGroundYPos = (characterInfo.getJumpHeight() / 2) + characterInfo.getManHeight();
                    }
                    if(newGroundYPos > screenY - characterInfo.getManHeight()) {
                        newGroundYPos = screenY - ground1.getGroundHeight();
                    }
                    ground1.setGroundWidth((int) (screenX / 2.4));
                    int newGroundXPos = lastGroundSpawned.getGroundXPosition() + lastGroundSpawned.getGroundWidth() + lastGroundSpawned.getGroundDistance();
                    ground1.setGroundXPosition(newGroundXPos);
                    ground1.setGroundYPosition(newGroundYPos);
                    lastGroundSpawned.setGroundXPosition(newGroundXPos);
                    lastGroundSpawned.setGroundYPosition(newGroundYPos);
                    lastGroundSpawned.setGroundWidth(ground1.getGroundWidth());

                    for (int i = 0; i < 2; i++) {
                        Coin coin = new Coin(getResources());
                        if(i == 0) {
                            coin.setCoinXPos(ground1.getGroundXPosition() + (ground1.getGroundWidth() / 2));
                            coin.setCoinYPos(ground1.getGroundYPosition() - characterInfo.getManHeight() - coin.getCoinGroundDistance());
                            lastCoinSpawned.setCoinXPos(coin.getCoinXPos());
                            lastCoinSpawned.setCoinYPos(coin.getCoinYPos());
                        }
                        else {
                            coin.setCoinXPos(lastCoinSpawned.getCoinXPos() + lastCoinSpawned.getCoinWidth());
                            coin.setCoinYPos(lastCoinSpawned.getCoinYPos());
                            lastCoinSpawned.setCoinXPos(coin.getCoinXPos());
                            lastCoinSpawned.setCoinYPos(coin.getCoinYPos());
                        }
                        coins.add(coin);
                    }

                    ground1IsSpawned = true;
                }
                break;
            case 2:
                if(ground2IsSpawned == false) {
                    int newGroundYPos = random.nextInt(screenY) + lastGroundSpawned.getGroundYPosition() - characterInfo.getJumpHeight();
                    if(newGroundYPos < (characterInfo.getJumpHeight() / 2) + characterInfo.getManHeight()) {
                        newGroundYPos = (characterInfo.getJumpHeight() / 2) + characterInfo.getManHeight();
                    }
                    if(newGroundYPos > screenY - characterInfo.getManHeight()) {
                        newGroundYPos = screenY - ground2.getGroundHeight();
                    }
                    ground2.setGroundWidth((int) (screenX / 1.7));
                    int newGroundXPos = lastGroundSpawned.getGroundXPosition() + lastGroundSpawned.getGroundWidth() + lastGroundSpawned.getGroundDistance();
                    ground2.setGroundXPosition(newGroundXPos);
                    ground2.setGroundYPosition(newGroundYPos);
                    lastGroundSpawned.setGroundXPosition(newGroundXPos);
                    lastGroundSpawned.setGroundYPosition(newGroundYPos);
                    lastGroundSpawned.setGroundWidth(ground2.getGroundWidth());

                    for (int i = 0; i < 3; i++) {
                        Coin coin = new Coin(getResources());
                        if(i == 0) {
                            coin.setCoinXPos(ground2.getGroundXPosition() + (ground2.getGroundWidth() / 2));
                            coin.setCoinYPos(ground2.getGroundYPosition() - characterInfo.getManHeight() - coin.getCoinGroundDistance());
                            lastCoinSpawned.setCoinXPos(coin.getCoinXPos());
                            lastCoinSpawned.setCoinYPos(coin.getCoinYPos());
                        }
                        else {
                            coin.setCoinXPos(lastCoinSpawned.getCoinXPos() + lastCoinSpawned.getCoinWidth());
                            coin.setCoinYPos(lastCoinSpawned.getCoinYPos());
                            lastCoinSpawned.setCoinXPos(coin.getCoinXPos());
                            lastCoinSpawned.setCoinYPos(coin.getCoinYPos());
                        }
                        coins.add(coin);
                    }

                    ground2IsSpawned = true;
                }
                break;
            case 3:
                if(ground3IsSpawned == false) {
                    int newGroundYPos = random.nextInt(screenY) + lastGroundSpawned.getGroundYPosition() - characterInfo.getJumpHeight();
                    if(newGroundYPos < (characterInfo.getJumpHeight() / 2) + characterInfo.getManHeight()) {
                        newGroundYPos = (characterInfo.getJumpHeight() / 2) + characterInfo.getManHeight();
                    }
                    if(newGroundYPos > screenY - characterInfo.getManHeight()) {
                        newGroundYPos = screenY - ground3.getGroundHeight();
                    }
                    ground3.setGroundWidth((int) (screenX / 1.4));
                    int newGroundXPos = lastGroundSpawned.getGroundXPosition() + lastGroundSpawned.getGroundWidth() + lastGroundSpawned.getGroundDistance();
                    ground3.setGroundXPosition(newGroundXPos);
                    ground3.setGroundYPosition(newGroundYPos);
                    lastGroundSpawned.setGroundXPosition(newGroundXPos);
                    lastGroundSpawned.setGroundYPosition(newGroundYPos);
                    lastGroundSpawned.setGroundWidth(ground3.getGroundWidth());

                    for (int i = 0; i < 4; i++) {
                        Coin coin = new Coin(getResources());
                        if(i == 0) {
                            coin.setCoinXPos(ground3.getGroundXPosition() + (ground3.getGroundWidth() / 2));
                            coin.setCoinYPos(ground3.getGroundYPosition() - characterInfo.getManHeight() - coin.getCoinGroundDistance());
                            lastCoinSpawned.setCoinXPos(coin.getCoinXPos());
                            lastCoinSpawned.setCoinYPos(coin.getCoinYPos());
                        }
                        else {
                            coin.setCoinXPos(lastCoinSpawned.getCoinXPos() + lastCoinSpawned.getCoinWidth());
                            coin.setCoinYPos(lastCoinSpawned.getCoinYPos());
                            lastCoinSpawned.setCoinXPos(coin.getCoinXPos());
                            lastCoinSpawned.setCoinYPos(coin.getCoinYPos());
                        }
                        coins.add(coin);
                    }

                    ground3IsSpawned = true;
                }
                break;
            case 4:
                if(ground4IsSpawned == false) {
                    int newGroundYPos = random.nextInt(screenY) + lastGroundSpawned.getGroundYPosition() - characterInfo.getJumpHeight();
                    if(newGroundYPos < (characterInfo.getJumpHeight() / 2) + characterInfo.getManHeight()) {
                        newGroundYPos = (characterInfo.getJumpHeight() / 2) + characterInfo.getManHeight();
                    }
                    if(newGroundYPos > screenY - characterInfo.getManHeight()) {
                        newGroundYPos = screenY - ground4.getGroundHeight();
                    }
                    ground4.setGroundWidth((int) (screenX / 3.8));
                    int newGroundXPos = lastGroundSpawned.getGroundXPosition() + lastGroundSpawned.getGroundWidth() + lastGroundSpawned.getGroundDistance();
                    ground4.setGroundXPosition(newGroundXPos);
                    ground4.setGroundYPosition(newGroundYPos);
                    lastGroundSpawned.setGroundXPosition(newGroundXPos);
                    lastGroundSpawned.setGroundYPosition(newGroundYPos);
                    lastGroundSpawned.setGroundWidth(ground4.getGroundWidth());

                    Coin coin = new Coin(getResources());
                    coin.setCoinXPos(ground4.getGroundXPosition() + (ground4.getGroundWidth() / 2));
                    coin.setCoinYPos(ground4.getGroundYPosition() - characterInfo.getManHeight() - coin.getCoinGroundDistance());
                    coins.add(coin);

                    ground4IsSpawned = true;
                }
                break;
            default:
                if(startingGroundIsSpawned == false) {
                    int newGroundYPos = random.nextInt(screenY) + lastGroundSpawned.getGroundYPosition() - characterInfo.getJumpHeight();
                    if(newGroundYPos < (characterInfo.getJumpHeight() / 2) + characterInfo.getManHeight()) {
                        newGroundYPos = (characterInfo.getJumpHeight() / 2) + characterInfo.getManHeight();
                    }
                    if(newGroundYPos > screenY - characterInfo.getManHeight()) {
                        newGroundYPos = screenY - startingGround.getGroundHeight();
                    }
                    int newGroundXPos = lastGroundSpawned.getGroundXPosition() + lastGroundSpawned.getGroundWidth() + lastGroundSpawned.getGroundDistance();
                    startingGround.setGroundXPosition(newGroundXPos);
                    startingGround.setGroundYPosition(newGroundYPos);
                    lastGroundSpawned.setGroundXPosition(newGroundXPos);
                    lastGroundSpawned.setGroundYPosition(newGroundYPos);
                    lastGroundSpawned.setGroundWidth(startingGround.getGroundWidth());

                    for (int i = 0; i < 5; i++) {
                        Coin coin = new Coin(getResources());
                        if(i == 0) {
                            coin.setCoinXPos(startingGround.getGroundXPosition() + (startingGround.getGroundWidth() / 2));
                            coin.setCoinYPos(startingGround.getGroundYPosition() - characterInfo.getManHeight() - coin.getCoinGroundDistance());
                            lastCoinSpawned.setCoinXPos(coin.getCoinXPos());
                            lastCoinSpawned.setCoinYPos(coin.getCoinYPos());
                        }
                        else {
                            coin.setCoinXPos(lastCoinSpawned.getCoinXPos() + lastCoinSpawned.getCoinWidth());
                            coin.setCoinYPos(lastCoinSpawned.getCoinYPos());
                            lastCoinSpawned.setCoinXPos(coin.getCoinXPos());
                            lastCoinSpawned.setCoinYPos(coin.getCoinYPos());
                        }
                        coins.add(coin);
                    }

                    startingGroundIsSpawned = true;
                }
                break;
        }
    }

    private void highestScore() {
        if(preferences.getInt("highest score", 0) < score) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("highest score", score);
            editor.apply();
            insertHighestScoreDB();
        }
    }

    private void insertHighestScoreDB() {
        Integer score = preferences.getInt("highest score", 0);
        SendHighScoreDB sendHighScoreDB = new SendHighScoreDB("Jungle_Run");
        sendHighScoreDB.execute("Send highest score", HomeActivity.username.getString("username", ""), score.toString());
    }

    private void draw() {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            canvas.drawBitmap(background1.bitmapBackground, background1.getFrameToDraw(), background1.getWhereToDraw(), null);
            canvas.drawBitmap(background2.bitmapBackground, background2.getFrameToDraw(), background2.getWhereToDraw(), null);
            canvas.drawBitmap(background3.bitmapBackground, background3.getFrameToDraw(), background3.getWhereToDraw(), null);

            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.aldrich);
            paint.setTypeface(typeface);
            paint.setTextSize(60);
            paint.setColor(Color.BLACK);

            canvas.drawText("Score: " +score, (int) (screenX / 1.3), (int) (screenY / 21.6), paint);

            if(jumpingMan.getIsJumping() == false && jumpingMan.getIsFalling() == false) {
                runningMan.manageCurrentFrame();
                canvas.drawBitmap(runningMan.bitmapRunning, runningMan.getFrameToDraw(), runningMan.getWhereToDraw(), null);
            }
            else {
                jumpingMan.manageJump();
                canvas.drawBitmap(jumpingMan.bitmapJumping, jumpingMan.getFrameToDraw(), jumpingMan.getWhereToDraw(), null);
            }

            if(startingGroundIsSpawned == true) {
                canvas.drawBitmap(startingGround.bitmapStartingGround, startingGround.getFrameToDraw(), startingGround.getWhereToDraw(), null);
            }
            if(ground1IsSpawned == true) {
                canvas.drawBitmap(ground1.bitmapGround1, ground1.getFrameToDraw(), ground1.getWhereToDraw(), null);
            }
            if(ground2IsSpawned == true) {
                canvas.drawBitmap(ground2.bitmapGround2, ground2.getFrameToDraw(), ground2.getWhereToDraw(), null);
            }
            if(ground3IsSpawned == true) {
                canvas.drawBitmap(ground3.bitmapGround3, ground3.getFrameToDraw(), ground3.getWhereToDraw(), null);
            }
            if(ground4IsSpawned == true) {
                canvas.drawBitmap(ground4.bitmapGround4, ground4.getFrameToDraw(), ground4.getWhereToDraw(), null);
            }

            for(int i = 0; i < coins.size(); i++) {
                coins.get(i).manageCurrentFrame();
                canvas.drawBitmap(coins.get(i).bitmapCoin, coins.get(i).getFrameToDraw(), coins.get(i).getWhereToDraw(), null);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void gameOver(Context context) {
        if(CharacterInfo.manYPos >= screenY - characterInfo.getManHeight()) {
            isPlaying = false;
            sounds.stopBackgroundSound();
            highestScore();
            context.startActivity(new Intent(context, GameOver.class));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN :
                jumpingMan.setIsJumping(true);
                sounds.playJumpSound();
                break;
        }
        return true;
    }

    private void sleep() {
        try{
            thread.sleep(17);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        try{
            isPlaying = false;
            sounds.pauseBackgroundSound();
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

}
