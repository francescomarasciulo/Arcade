package com.hightech.arcade.AndroidDoodle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.media.MediaPlayer;
import com.hightech.arcade.Global.HomeActivity;
import com.hightech.arcade.Global.SendHighScoreDB;
import com.hightech.arcade.R;
import android.graphics.Rect;
import androidx.core.content.res.ResourcesCompat;
import java.util.ArrayList;

public class DoodleGameView extends SurfaceView implements Runnable {

    int high[] = new int[5];
    SharedPreferences sharedPreferences;

    private int bugCount = 0;
    private Typeface typeface;
    private int bugScreenX, bugScreenY;
    static MediaPlayer sound;
    private Thread thread;
    private Paint paint;
    private Robot robot;
    private ArrayList<Bug> bugs;
    private ArrayList<Pickup> pick;
    private boolean isPlaying, isGameOver = false;
    private SfondoDoodle space1;
    public static int SCORE = 0;

    public DoodleGameView (Context context, int screenX, int screenY) {

        super(context);
        bugScreenX = screenX;
        bugScreenY = screenY;
        typeface = ResourcesCompat.getFont(context, R.font.games);
        sound = MediaPlayer.create(context, R.raw.future_sound);
        robot = new Robot(context,screenX,screenY);
        space1 = new SfondoDoodle(screenX, screenY, getResources());
        paint = new Paint();
        bugs = new ArrayList<>();
        pick = new ArrayList<>();


        sharedPreferences = context.getSharedPreferences("AndroidDoodle",Context.MODE_PRIVATE);
        high[0] = sharedPreferences.getInt("punteggio1",0);
        high[1] = sharedPreferences.getInt("punteggio2",0);
        high[2] = sharedPreferences.getInt("punteggio3",0);
        high[3] = sharedPreferences.getInt("punteggio4",0);
        high[4] = sharedPreferences.getInt("punteggio5",0);
    }

    public void run() {
        while (isPlaying) {
            sound.start();
            sound.setLooping(true);
            update();
            draw();
            sleep();
        }
    }

    private void update(){
        robot.update();

        if (SCORE < 150) {
            if (bugCount % 1000 == 0) bugs.add(new Bug(getContext(),bugScreenX ,bugScreenY));
        } else {
            if (bugCount % 800 == 0) bugs.add(new Bug(getContext(), bugScreenX, bugScreenY));
            if (bugCount % 900 == 0)  bugs.add(new Bug(getContext(), bugScreenX, bugScreenY));
        }

        if (bugCount % 2500 == 0) pick.add(new Pickup(getContext(),bugScreenX ,bugScreenY));
        if (bugCount % 400 == 0) SCORE++;

        for(Pickup pickup : pick){
            pickup.update();
            if (Rect.intersects(pickup.getCollision(), robot.getCollision())) {
                pickup.bY-=100* bugScreenX;
                SCORE+=20;
            }
        }


        for(Bug bug : bugs) {
            bug.update();
            if (Rect.intersects(bug.getCollision(), robot.getCollision())) {
                isGameOver = true;
                for(int k=0;k <= 4;k++){
                    if(high[k] <= SCORE){
                        high[k] = SCORE;
                        break;
                    }
                }
                SharedPreferences.Editor e = sharedPreferences.edit();
                for(int k=0;k <= 4;k++){
                    int j = k+1;
                    e.putInt("punteggio"+j,high[k]);
                }
                e.apply();
            }
        }
    }

    private void draw(){
        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            int halfWidth= canvas.getWidth()/2;
            int halfHeight= canvas.getHeight()/2;
            canvas.drawBitmap(space1.space, space1.x, space1.y, paint);
            canvas.drawBitmap(robot.getBitmap(), robot.getX(), robot.getY(), paint);

            for (Bug bug : bugs) {
                canvas.drawBitmap(bug.getBitmap(), bug.getX(), bug.getY(), paint);
            }
            for (Pickup pickup : pick) {
                canvas.drawBitmap(pickup.getBitmap(), pickup.getX(), pickup.getY(), paint);
            }

            Paint score = new Paint();
            score.setTextSize(100);
            score.setColor(Color.WHITE);
            canvas.drawText("Score : " + SCORE, 100, 130, score);

            if(isGameOver){
                insertHighestScoreDB();
                sound.stop();
                paint.setTextSize(190);
                paint.setColor(Color.rgb(142,255,188));
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTypeface(typeface);
                canvas.drawBitmap(space1.space,0,0,paint);
                canvas.drawText("GAME OVER",halfWidth ,halfHeight ,paint);
                paint.setTextSize(100);
                canvas.drawText("TAP TO CONTINUE...", canvas.getWidth()-500, canvas.getHeight()-50,paint);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void insertHighestScoreDB() {
        Integer score = sharedPreferences.getInt("punteggio1", 0);
        SendHighScoreDB sendHighScoreDB = new SendHighScoreDB("Android_Doodle");
        sendHighScoreDB.execute("Send highest score", HomeActivity.username.getString("username", ""), score.toString());
    }

    private void sleep(){
        try {
            if (bugCount == 10000) {
                bugCount = 0;
            }
            Thread.sleep(20);
            bugCount += 20;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause(){
        isPlaying=false;
        sound.stop();
        SCORE=0;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        if(isGameOver){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                SCORE=0;
                sound.stop();
                ((DoodleGameActivity) getContext()).finish();
                getContext().startActivity(new Intent(getContext(), DoodleActivity.class));
            }
        }

        return true;
    }

}
