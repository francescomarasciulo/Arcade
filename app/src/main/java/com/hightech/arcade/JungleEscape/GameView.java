package com.hightech.arcade.JungleEscape;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.hightech.arcade.Global.HomeActivity;
import com.hightech.arcade.Global.SendHighScoreDB;
import com.hightech.arcade.R;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class GameView extends SurfaceView implements Runnable {

    private Bitmap ramo_sinistra;
    private Bitmap ramo_destra;
    private Bitmap backgroundingame;
    private Bitmap fogliascore;
    private Bitmap bananabitmap;
    volatile boolean playing;
    private Thread gameThread = null;
    private Player player;
    private Ramo enemies;
    private Ramo enemies2;
    private Ramo enemies3;
    private Ramo enemies4;
    private Bonus banana;
    private Bonus banana2;
    private boolean spawned = false;
    private boolean collision = false;
    private boolean collision2 = false;
    private static SoundPlayer sound;
    private static MediaPlayer mediaPlayer;

    int screenY;
    int screenX;

    Context context;

    int score;
    float midscore;
    int scorebonus;
    int scorebonus2;

    int highScore[] = new int[4];

    SharedPreferences sharedPreferences;

    private boolean isGameOver ;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        mediaPlayer= MediaPlayer.create(context, R.raw.jungleescape_theme);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        sound = new SoundPlayer(context);
        player = new Player(context, screenX, screenY);
        surfaceHolder = getHolder();
        paint = new Paint();

        this.context = context;

        backgroundingame = BitmapFactory.decodeResource(context.getResources(),R.drawable.jungleescape_jungle);
        banana = new Bonus(context);
        banana2 = new Bonus(context);
        enemies = new Ramo(context);
        enemies2 = new Ramo(context);
        enemies3 = new Ramo(context);
        enemies4 = new Ramo(context);
        ramo_sinistra = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_ramosinistro);
        ramo_destra = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_ramodestra);
        backgroundingame = Bitmap.createScaledBitmap(backgroundingame, screenX, screenY, false);
        fogliascore = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_fogliascore);
        fogliascore = Bitmap.createScaledBitmap(fogliascore, 250, 119, false);
        bananabitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_banana);
        banana.setBitmap(Bitmap.createScaledBitmap(bananabitmap, 120, 70, false));
        banana2.setBitmap(Bitmap.createScaledBitmap(bananabitmap, 120, 70, false));


        enemies.setBitmap(Bitmap.createScaledBitmap(ramo_sinistra, (screenX/4)*3, 134, false));
        enemies.setX(screenX);
        enemies.setY(screenY + 101);
        enemies.setDetectCollision(new Rect(enemies.getX(), enemies.getY(), enemies.getBitmap().getWidth(), enemies.getBitmap().getHeight()));

        enemies2.setBitmap(Bitmap.createScaledBitmap(ramo_destra, (screenX/5)*2, 134, false));
        enemies2.setX(screenX);
        enemies2.setY(screenY + 101);
        enemies2.setDetectCollision(new Rect(enemies2.getX(), enemies2.getY(), enemies2.getBitmap().getWidth(), enemies2.getBitmap().getHeight()));

        enemies3.setBitmap(Bitmap.createScaledBitmap(ramo_sinistra, (screenX/4)*3, 134, false));
        enemies3.setX(screenX);
        enemies3.setY(screenY + 101);
        enemies3.setDetectCollision(new Rect(enemies3.getX(), enemies3.getY(), enemies3.getBitmap().getWidth(), enemies3.getBitmap().getHeight()));

        enemies4.setBitmap(Bitmap.createScaledBitmap(ramo_destra, (screenX/5)*2, 134, false));
        enemies4.setX(screenX);
        enemies4.setY(screenY + 101);
        enemies4.setDetectCollision(new Rect(enemies4.getX(), enemies4.getY(), enemies4.getBitmap().getWidth(), enemies4.getBitmap().getHeight()));

        banana.setX(screenX/2);
        banana.setY(screenY + 101);
        banana.setDetectCollision(new Rect(banana.getX(), banana.getY(), banana.getBitmap().getWidth(), banana.getBitmap().getHeight()));

        banana2.setX(screenX);
        banana2.setY(screenY + 101);
        banana2.setDetectCollision(new Rect(banana2.getX(), banana2.getY(), banana2.getBitmap().getWidth(), banana2.getBitmap().getHeight()));

        score = 0;
        scorebonus = 0;
        scorebonus2 = 0;

        this.screenX = screenX;
        this.screenY = screenY;

        isGameOver = false;

        sharedPreferences = context.getSharedPreferences("Jungleescape", MODE_PRIVATE);

        highScore[0] = sharedPreferences.getInt("scores1",0);
        highScore[1] = sharedPreferences.getInt("scores2",0);
        highScore[2] = sharedPreferences.getInt("scores3",0);
        highScore[3] = sharedPreferences.getInt("scores4",0);
    }

    @Override
    public void run() {

        while (playing) {

            update();
            draw();
            control();

        }

    }

    private void update() {

        player.update();
        player.getgamestate(isGameOver);
        if (!isGameOver) {
            midscore+= 0.15 + scorebonus + scorebonus2;
            if (midscore >= 1)
                score = (int) midscore;
            for(int k=0;k <= 3;k++) {
                if (highScore[k]<=score) {
                    highScore[k] = score;
                    break;
                }
            }
            SharedPreferences.Editor e = sharedPreferences.edit();
            for(int k=0;k <= 3;k++) {
                int j = k+1;
                e.putInt("scores"+j, highScore[k]);
            }
            e.apply();

        }

        enemies.getDetectCollision().left = enemies.getX();
        enemies.getDetectCollision().top = enemies.getY();
        enemies.getDetectCollision().right = enemies.getX() + enemies.getBitmap().getWidth();
        enemies.getDetectCollision().bottom = enemies.getY() + enemies.getBitmap().getHeight();

        enemies2.getDetectCollision().left = enemies2.getX();
        enemies2.getDetectCollision().top = enemies2.getY();
        enemies2.getDetectCollision().right = enemies2.getX() + enemies2.getBitmap().getWidth();
        enemies2.getDetectCollision().bottom = enemies2.getY() + enemies2.getBitmap().getHeight();

        enemies3.getDetectCollision().left = enemies3.getX();
        enemies3.getDetectCollision().top = enemies3.getY();
        enemies3.getDetectCollision().right = enemies3.getX() + enemies3.getBitmap().getWidth();
        enemies3.getDetectCollision().bottom = enemies3.getY() + enemies3.getBitmap().getHeight();

        enemies4.getDetectCollision().left = enemies4.getX();
        enemies4.getDetectCollision().top = enemies4.getY();
        enemies4.getDetectCollision().right = enemies4.getX() + enemies4.getBitmap().getWidth();
        enemies4.getDetectCollision().bottom = enemies4.getY() + enemies4.getBitmap().getHeight();

        banana.getDetectCollision().left = banana.getX();
        banana.getDetectCollision().top = banana.getY();
        banana.getDetectCollision().right = banana.getX() + banana.getBitmap().getWidth();
        banana.getDetectCollision().bottom = banana.getY() + banana.getBitmap().getHeight();

        banana2.getDetectCollision().left = banana2.getX();
        banana2.getDetectCollision().top = banana2.getY();
        banana2.getDetectCollision().right = banana2.getX() + banana2.getBitmap().getWidth();
        banana2.getDetectCollision().bottom = banana2.getY() + banana2.getBitmap().getHeight();

        enemies.update();
        enemies2.update();
        enemies3.update();
        enemies4.update();
        banana.update();
        banana2.update();

        if (!isGameOver) {

            if (banana.getY() >= screenY || collision) {
                Random generator = new Random();
                banana.setX(generator.nextInt(screenX/3)+banana.getBitmap().getWidth());
                banana.setY(-100);
                banana.setDetectCollision(new Rect(banana.getX(), banana.getY(), banana.getBitmap().getWidth(), banana.getBitmap().getHeight()));
            }

             if (banana2.getY() >= screenY || collision2) {
                Random generator = new Random();
                banana2.setX(generator.nextInt(screenX/3)+screenX/2);
                banana2.setY(banana.getY()-1000);
                banana2.setDetectCollision(new Rect(banana2.getX(), banana2.getY(), banana2.getBitmap().getWidth(), banana2.getBitmap().getHeight()));
            }


            if (enemies3.getY() >= screenY) spawned = false;


            if (enemies.getY()>screenY) {
                Random generator = new Random();
                switch ( generator.nextInt(7)+  1) {
                    case 1:
                        enemies.setBitmap(Bitmap.createScaledBitmap(ramo_sinistra, (screenX/4)*3, 134, false));
                        enemies.setX(0);
                        enemies.setY(-100);
                        enemies.setDetectCollision(new Rect(enemies.getX(), enemies.getY(), enemies.getBitmap().getWidth(), enemies.getBitmap().getHeight()));
                        break;
                    case 2:
                        enemies.setBitmap(Bitmap.createScaledBitmap(ramo_destra, (screenX/4)*3, 134, false));
                        enemies.setX(screenX - enemies.getBitmap().getWidth());
                        enemies.setY(-100);
                        enemies.setDetectCollision(new Rect(enemies.getX(), enemies.getY(), enemies.getBitmap().getWidth(), enemies.getBitmap().getHeight()));
                        break;
                    case 3:
                        enemies.setBitmap(Bitmap.createScaledBitmap(ramo_sinistra, (screenX/5)*2-30, 134, false));
                        enemies.setX(0);
                        enemies.setY(-100);
                        enemies.setDetectCollision(new Rect(enemies.getX(), enemies.getY(), enemies.getBitmap().getWidth(), enemies.getBitmap().getHeight()));
                        enemies2.setBitmap(Bitmap.createScaledBitmap(ramo_destra, (screenX/5)*2-30, 134, false));
                        enemies2.setX(screenX - enemies2.getBitmap().getWidth());
                        enemies2.setY(-100);
                        enemies2.setDetectCollision(new Rect(enemies2.getX(), enemies2.getY(), enemies2.getBitmap().getWidth(), enemies2.getBitmap().getHeight()));


                        break;
                    case 4:
                        enemies.setBitmap(Bitmap.createScaledBitmap(ramo_sinistra, (screenX/5)*2+220, 134, false));
                        enemies.setX(0);
                        enemies.setY(-100);
                        enemies.setDetectCollision(new Rect(enemies.getX(), enemies.getY(), enemies.getBitmap().getWidth(), enemies.getBitmap().getHeight()));

                        enemies2.setBitmap(Bitmap.createScaledBitmap(ramo_destra, (screenX/5)*2-280, 50, false));
                        enemies2.setX(screenX - enemies2.getBitmap().getWidth());
                        enemies2.setY(-40);
                        enemies2.setDetectCollision(new Rect(enemies2.getX(), enemies2.getY(), enemies2.getBitmap().getWidth(), enemies2.getBitmap().getHeight()));
                        break;
                    case 5:
                        enemies.setBitmap(Bitmap.createScaledBitmap(ramo_sinistra, (screenX/5)*2-280, 50, false));
                        enemies.setX(0);
                        enemies.setY(-40);
                        enemies.setDetectCollision(new Rect(enemies.getX(), enemies.getY(), enemies.getBitmap().getWidth(), enemies.getBitmap().getHeight()));

                        enemies2.setBitmap(Bitmap.createScaledBitmap(ramo_destra, (screenX/5)*2+220, 134, false));
                        enemies2.setX(screenX - enemies2.getBitmap().getWidth());
                        enemies2.setY(-100);
                        enemies2.setDetectCollision(new Rect(enemies2.getX(), enemies2.getY(), enemies2.getBitmap().getWidth(), enemies2.getBitmap().getHeight()));
                        break;
                    case 6:
                        enemies.setBitmap(Bitmap.createScaledBitmap(ramo_sinistra, (screenX/5)*2+100, 134, false));
                        enemies.setX(0);
                        enemies.setY(-100);
                        enemies.setDetectCollision(new Rect(enemies.getX(), enemies.getY(), enemies.getBitmap().getWidth(), enemies.getBitmap().getHeight()));

                        enemies2.setBitmap(Bitmap.createScaledBitmap(ramo_destra, (screenX/5)*2-160, 134, false));
                        enemies2.setX(screenX - enemies2.getBitmap().getWidth());
                        enemies2.setY(-100);
                        enemies2.setDetectCollision(new Rect(enemies2.getX(), enemies2.getY(), enemies2.getBitmap().getWidth(), enemies2.getBitmap().getHeight()));
                        break;
                    case 7:
                        enemies.setBitmap(Bitmap.createScaledBitmap(ramo_sinistra, (screenX/5)*2-160, 134, false));
                        enemies.setX(0);
                        enemies.setY(-100);
                        enemies.setDetectCollision(new Rect(enemies.getX(), enemies.getY(), enemies.getBitmap().getWidth(), enemies.getBitmap().getHeight()));

                        enemies2.setBitmap(Bitmap.createScaledBitmap(ramo_destra, (screenX/5)*2+100, 134, false));
                        enemies2.setX(screenX - enemies2.getBitmap().getWidth());
                        enemies2.setY(-100);
                        enemies2.setDetectCollision(new Rect(enemies2.getX(), enemies2.getY(), enemies2.getBitmap().getWidth(), enemies2.getBitmap().getHeight()));
                        break;
                }
            }


            if (enemies.getY()>screenY/2 && !spawned) {
                spawned = true;
                Random generator = new Random();
                switch (generator.nextInt(7)+1) {
                    case 1:
                        enemies3.setBitmap(Bitmap.createScaledBitmap(ramo_sinistra, (screenX/4)*3, 134, false));
                        enemies3.setX(0);
                        enemies3.setY(-100);
                        enemies3.setDetectCollision(new Rect(enemies3.getX(), enemies3.getY(), enemies3.getBitmap().getWidth(), enemies3.getBitmap().getHeight()));
                        break;
                    case 2:
                        enemies3.setBitmap(Bitmap.createScaledBitmap(ramo_destra, (screenX/4)*3, 134, false));
                        enemies3.setX(screenX - enemies3.getBitmap().getWidth());
                        enemies3.setY(-100);
                        enemies3.setDetectCollision(new Rect(enemies3.getX(), enemies3.getY(), enemies3.getBitmap().getWidth(), enemies3.getBitmap().getHeight()));
                        break;
                    case 3:
                        enemies3.setBitmap(Bitmap.createScaledBitmap(ramo_sinistra, (screenX/5)*2-30, 134, false));
                        enemies3.setX(0);
                        enemies3.setY(-100);
                        enemies3.setDetectCollision(new Rect(enemies3.getX(), enemies3.getY(), enemies3.getBitmap().getWidth(), enemies3.getBitmap().getHeight()));

                        enemies4.setBitmap(Bitmap.createScaledBitmap(ramo_destra, (screenX/5)*2-30, 134, false));
                        enemies4.setX(screenX - enemies4.getBitmap().getWidth());
                        enemies4.setY(-100);
                        enemies4.setDetectCollision(new Rect(enemies4.getX(), enemies4.getY(), enemies4.getBitmap().getWidth(), enemies4.getBitmap().getHeight()));
                        break;
                    case 4:
                        enemies3.setBitmap(Bitmap.createScaledBitmap(ramo_sinistra, (screenX/5)*2+220, 134, false));
                        enemies3.setX(0);
                        enemies3.setY(-100);
                        enemies3.setDetectCollision(new Rect(enemies3.getX(), enemies3.getY(), enemies3.getBitmap().getWidth(), enemies3.getBitmap().getHeight()));

                        enemies4.setBitmap(Bitmap.createScaledBitmap(ramo_destra, (screenX/5)*2-280, 50, false));
                        enemies4.setX(screenX - enemies4.getBitmap().getWidth());
                        enemies4.setY(-40);
                        enemies4.setDetectCollision(new Rect(enemies4.getX(), enemies4.getY(), enemies4.getBitmap().getWidth(), enemies4.getBitmap().getHeight()));
                        break;
                    case 5:
                        enemies3.setBitmap(Bitmap.createScaledBitmap(ramo_sinistra, (screenX/5)*2-280, 50, false));
                        enemies3.setX(0);
                        enemies3.setY(-40);
                        enemies3.setDetectCollision(new Rect(enemies3.getX(), enemies3.getY(), enemies3.getBitmap().getWidth(), enemies3.getBitmap().getHeight()));

                        enemies4.setBitmap(Bitmap.createScaledBitmap(ramo_destra, (screenX/5)*2+220, 134, false));
                        enemies4.setX(screenX - enemies2.getBitmap().getWidth());
                        enemies4.setY(-100);
                        enemies4.setDetectCollision(new Rect(enemies4.getX(), enemies4.getY(), enemies4.getBitmap().getWidth(), enemies4.getBitmap().getHeight()));
                        break;
                    case 6:
                        enemies3.setBitmap(Bitmap.createScaledBitmap(ramo_sinistra, (screenX/5)*2+100, 134, false));
                        enemies3.setX(0);
                        enemies3.setY(-100);
                        enemies3.setDetectCollision(new Rect(enemies3.getX(), enemies3.getY(), enemies3.getBitmap().getWidth(), enemies3.getBitmap().getHeight()));

                        enemies4.setBitmap(Bitmap.createScaledBitmap(ramo_destra, (screenX/5)*2-160, 134, false));
                        enemies4.setX(screenX - enemies4.getBitmap().getWidth());
                        enemies4.setY(-100);
                        enemies4.setDetectCollision(new Rect(enemies4.getX(), enemies4.getY(), enemies4.getBitmap().getWidth(), enemies4.getBitmap().getHeight()));
                        break;
                    case 7:
                        enemies3.setBitmap(Bitmap.createScaledBitmap(ramo_sinistra, (screenX/5)*2-160, 134, false));
                        enemies3.setX(0);
                        enemies3.setY(-100);
                        enemies3.setDetectCollision(new Rect(enemies3.getX(), enemies3.getY(), enemies3.getBitmap().getWidth(), enemies3.getBitmap().getHeight()));

                        enemies4.setBitmap(Bitmap.createScaledBitmap(ramo_destra, (screenX/5)*2+100, 134, false));
                        enemies4.setX(screenX - enemies4.getBitmap().getWidth());
                        enemies4.setY(-100);
                        enemies4.setDetectCollision(new Rect(enemies4.getX(), enemies4.getY(), enemies4.getBitmap().getWidth(), enemies4.getBitmap().getHeight()));
                        break;
                }
            }

            if(Rect.intersects(player.getDetectCollision(),enemies.getDetectCollision())) {
                isGameOver = true;
                sound.playGameOverSound();
            }

            if(Rect.intersects(player.getDetectCollision(),enemies2.getDetectCollision())) {
                isGameOver = true;
                sound.playGameOverSound();
            }

            if(Rect.intersects(player.getDetectCollision(),enemies3.getDetectCollision())) {
                isGameOver = true;
                sound.playGameOverSound();
            }

            if(Rect.intersects(player.getDetectCollision(),enemies4.getDetectCollision())) {
                isGameOver = true;
                sound.playGameOverSound();
            }

            if(Rect.intersects(player.getDetectCollision(),banana.getDetectCollision())) {
                scorebonus = 20;
                collision = true;
                sound.playPickSound();
            } else { scorebonus = 0;
                collision = false; }

            if(Rect.intersects(player.getDetectCollision(),banana2.getDetectCollision())) {
                scorebonus2 = 20;
                collision2 = true;
                sound.playPickSound();
            } else { scorebonus2 = 0;
                collision2 = false; }
        }
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(backgroundingame, 0, 0, paint);
            paint.setTextSize(20);
            player.draw(canvas);

            canvas.drawBitmap(
                    enemies.getBitmap(),
                    enemies.getX(),
                    enemies.getY(),
                    paint);

            canvas.drawBitmap(
                    enemies2.getBitmap(),
                    enemies2.getX(),
                    enemies2.getY(),
                    paint);

            if (spawned) canvas.drawBitmap(
                    enemies3.getBitmap(),
                    enemies3.getX(),
                    enemies3.getY(),
                    paint);

            if (spawned) canvas.drawBitmap(
                    enemies4.getBitmap(),
                    enemies4.getX(),
                    enemies4.getY(),
                    paint);

            canvas.drawBitmap(
                    banana.getBitmap(),
                    banana.getX(),
                    banana.getY(),
                    paint);

            canvas.drawBitmap(
                    banana2.getBitmap(),
                    banana2.getX(),
                    banana2.getY(),
                    paint);

            Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);

            paint.setTypeface(boldTypeface);
            paint.setTextSize(30);
            paint.setColor(Color.rgb(0,0,0));
            if (!isGameOver) {
                canvas.drawBitmap(fogliascore, -30, 15, paint);
                canvas.drawText("Score:" + score, 40, 70, paint);
            }

            if(isGameOver){
                insertHighestScoreDB();
                mediaPlayer.stop();
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);
                int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                canvas.drawText("Game Over",canvas.getWidth()/2,yPos,paint);
                canvas.drawText("Score:" + score, canvas.getWidth()/2, yPos + 150, paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);

        }
    }

    private void insertHighestScoreDB() {
        Integer score = sharedPreferences.getInt("scores1", 0);
        SendHighScoreDB sendHighScoreDB = new SendHighScoreDB("Jungle_Escape");
        sendHighScoreDB.execute("Send highest score", HomeActivity.username.getString("username", ""), score.toString());
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        playing = false;
        mediaPlayer.pause();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        if(isGameOver){

            if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                ((GameActivity) getContext()).finish();
                context.startActivity(new Intent(context, JungleEscapeActivity.class));
            }
        }
        return true;
    }
}