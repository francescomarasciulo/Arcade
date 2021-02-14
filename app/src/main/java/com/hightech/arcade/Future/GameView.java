package com.hightech.arcade.Future;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import com.hightech.arcade.Global.HomeActivity;
import com.hightech.arcade.Global.SendHighScoreDB;
import com.hightech.arcade.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    int score = 0;
    int[] highScore = new int[4];
    SharedPreferences sharedPreferences;

    static MediaPlayer theme;
    private Thread thread;
    private int screenX, screenY, i;
    public static int hit = 0;
    public static float screenRatioX, screenRatioY;
    private Random random;
    private Life life;
    private Player player;
    private Enemy[] enemies;
    private List<Bullet> bullets;
    private Typeface typeface;
    private Paint paint;
    private boolean isPlaying, isGameOver = false;
    private Background background1, background2;

    public GameView (Context context, int screenX, int screenY) {
        super (context);
        this.screenX = screenX;
        this.screenY = screenY;

        sharedPreferences = context.getSharedPreferences("Future",Context.MODE_PRIVATE);
        highScore[0] = sharedPreferences.getInt("score1",0);
        highScore[1] = sharedPreferences.getInt("score2",0);
        highScore[2] = sharedPreferences.getInt("score3",0);
        highScore[3] = sharedPreferences.getInt("score4",0);

        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        theme = MediaPlayer.create(context, R.raw.doodle_theme);
        typeface = ResourcesCompat.getFont(context, R.font.string);
        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        player = new Player(this, screenY, screenX, getResources());
        bullets = new ArrayList<>();
        background2.x = screenX;
        life = new Life(getResources());
        paint = new Paint();
        enemies = new Enemy[4];
        random = new Random();

        for(i=0 ; i < 4; i++ ){
            Enemy enemy = new Enemy(getResources(), screenX, screenY);
            enemies[i] = enemy;
        }
    }

    @Override
    public void run(){
        while (isPlaying){
            theme.start();
            theme.setLooping(true);
            update();
            draw();
            sleep();
        }
    }

    private void update(){
        background1.x -= 20 * screenRatioX;
        background2.x -= 20 * screenRatioX;
        if (background1.x + background1.back1.getWidth() < 0) background1.x = screenX;
        if (background2.x + background2.back1.getWidth() < 0) background2.x = screenX;

        player.update();
        if(player.IsGoingUp) player.y -= 21.5;
        else player.y += 21.5;
        if(player.y < 0) player.y = 0;
        if(player.y > screenY - player.height) player.y = screenY - player.height;

        List<Bullet> trash = new ArrayList<>();
        for(Bullet bullet : bullets){
            if(bullet.x > screenX) trash.add(bullet);
            bullet.x += 50 * screenRatioX;

            for(Enemy enemy : enemies){
                if(Rect.intersects(enemy.getCollision() , bullet.getCollision())) {
                    enemy.x = -500;
                    bullet.x = screenX + 500;
                    score += 5;
                }
            }
        }

        for(Bullet bullet : trash) bullets.remove(bullet);

        for(Enemy enemy : enemies){
            enemy.x -= enemy.speed;
            if(enemy.x + enemy.width < 0) {
                int bound = 30;
                enemy.speed = random.nextInt(bound);
                if(enemy.speed < 15) enemy.speed = 20;
                enemy.x = screenX + 15;
                enemy.y = random.nextInt(screenY - enemy.height);
            }

            if(Rect.intersects(enemy.getCollision(), player.getCollision())){
                hit ++;
                enemy.x = -500;
                if(hit == 3) {
                    isGameOver = true;
                    hit = 0;
                    theme.stop();
                    theme.setLooping(false);

                    for(int k=0;k <= 3;k++){
                        if(highScore[k] <= score){
                            highScore[k] = score;
                            break;
                        }
                    }
                    SharedPreferences.Editor e = sharedPreferences.edit();
                    for(int k=0;k <= 3;k++){
                        int j = k+1;
                        e.putInt("score"+j,highScore[k]);
                    }
                    e.apply();

                    return;
                }
            }
        }
    }

    private void draw(){
        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            int halfWidth= canvas.getWidth()/2;
            int halfHeight= canvas.getHeight()/2;
            canvas.drawBitmap(background1.back1, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.back1, background2.x, background2.y, paint);

            paint.setTextSize(120);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("Score : "+score,screenX/2, life.height, paint);

            canvas.drawBitmap(life.getLife(), life.x, life.y, paint);
            canvas.drawBitmap(player.getPlayer(), player.x, player.y, paint);

            for (Enemy enemy : enemies)
                canvas.drawBitmap(enemy.getEnemy(), enemy.x, enemy.y, paint);

            for (Bullet bullet : bullets)
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);

            if(isGameOver){
                isPlaying = false;
                insertHighestScoreDB();
                paint.setTextSize(400);
                paint.setColor(Color.rgb(255,255,255));
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTypeface(typeface);
                canvas.drawBitmap(background1.back1,0,0,paint);
                canvas.drawText("GAME OVER", halfWidth, halfHeight,paint);
                paint.setTextSize(140);
                canvas.drawText("TAP TO CONTINUE...", canvas.getWidth()-500, canvas.getHeight()-50,paint);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void insertHighestScoreDB() {
        Integer score = sharedPreferences.getInt("score1", 0);
        SendHighScoreDB sendHighScoreDB = new SendHighScoreDB("Future");
        sendHighScoreDB.execute("Send highest score", HomeActivity.username.getString("username", ""), score.toString());
    }

    private void sleep(){
        try {
            Thread.sleep(17);
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
        try {
            isPlaying=false;
            hit = 0;
            theme.stop();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX() < screenX / 2){
                    player.IsGoingUp = true;
                }
                if(event.getX() > screenX / 2) {
                    player.toShoot++;
                }
                break;
            case MotionEvent.ACTION_UP:
                player.IsGoingUp = false;
                break;
        }

        if(isGameOver){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                ((GameActivity) getContext()).finish();
                getContext().startActivity(new Intent(getContext(), FutureActivity.class));
            }
        }
        return true;
    }

    public void newBullet(){
        Bullet bullet = new Bullet(getResources());
        bullet.x = player.x + player.width;
        bullet.y = player.y + (player.height / 2);
        bullets.add(bullet);
    }
}
