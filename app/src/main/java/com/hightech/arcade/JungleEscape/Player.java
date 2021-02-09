package com.hightech.arcade.JungleEscape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.util.Log;
import com.hightech.arcade.R;

public class Player implements SensorEventListener {
    private Bitmap bitmap;
    private Bitmap bitmap_dead;
    private Bitmap bitmap_jump1;
    private Bitmap bitmap_jump2;
    private Bitmap bitmap_jump3;
    private Bitmap bitmap_jump4;
    private Bitmap bitmap_jump5;
    private Bitmap bitmap_jump6;
    private Bitmap bitmap_jump7;
    private Bitmap bitmap_jump8;
    private Bitmap bitmap_jump1g;
    private Bitmap bitmap_jump2g;
    private Bitmap bitmap_jump3g;
    private Bitmap bitmap_jump4g;
    private Bitmap bitmap_jump5g;
    private Bitmap bitmap_jump6g;
    private Bitmap bitmap_jump7g;
    private Bitmap bitmap_jump8g;
    private int x;
    private int y;
    private int speed = 0;
    private int maxY;
    private int maxX;
    private int minY;
    private int minX;
    private int sensorX;
    private int screenX;
    private int screenY;
    private int state;
    private boolean isGameOver=false;
    private AnimationManager animationManager;
    private Rect detectCollision;

    public Player(Context context, int screenX, int screenY) {
        x = screenX/2-92;
        y = screenY-screenY/11-23;
        this.screenX = screenX;
        this.screenY = screenY;


        SensorManager sensorManager = (SensorManager)
                context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensorManager.registerListener(this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_GAME);
        }

        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_player);
        bitmap = Bitmap.createScaledBitmap(bitmap, screenX/7+30, screenY/11+30, false);
        bitmap_dead = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_playerdead);
        bitmap_dead = Bitmap.createScaledBitmap(bitmap_dead, screenX/7+30, screenY/11+30, false);
        bitmap_jump1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_playerjump1);
        bitmap_jump1 = Bitmap.createScaledBitmap(bitmap_jump1, screenX/7+30, screenY/11+30, false);
        bitmap_jump2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_playerjump2);
        bitmap_jump2 = Bitmap.createScaledBitmap(bitmap_jump2, screenX/7+30, screenY/11+30, false);
        bitmap_jump3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_playerjump3);
        bitmap_jump3 = Bitmap.createScaledBitmap(bitmap_jump3, screenX/7+30, screenY/11+30, false);
        bitmap_jump4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_playerjump4);
        bitmap_jump4 = Bitmap.createScaledBitmap(bitmap_jump4, screenX/7+30, screenY/11+30, false);
        bitmap_jump5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_playerjump5);
        bitmap_jump5 = Bitmap.createScaledBitmap(bitmap_jump5, screenX/7+30, screenY/11+30, false);
        bitmap_jump6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_playerjump6);
        bitmap_jump6 = Bitmap.createScaledBitmap(bitmap_jump6, screenX/7+30, screenY/11+30, false);
        bitmap_jump7 = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_playerjump7);
        bitmap_jump7 = Bitmap.createScaledBitmap(bitmap_jump7, screenX/7+30, screenY/11+30, false);
        bitmap_jump8 = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_playerjump8);
        bitmap_jump8 = Bitmap.createScaledBitmap(bitmap_jump8, screenX/7+30, screenY/11+30, false);
        bitmap_jump1g = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_playerjump1girata);
        bitmap_jump1g = Bitmap.createScaledBitmap(bitmap_jump1g, screenX/7+30, screenY/11+30, false);
        bitmap_jump2g = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_playerjump2girata);
        bitmap_jump2g = Bitmap.createScaledBitmap(bitmap_jump2g, screenX/7+30, screenY/11+30, false);
        bitmap_jump3g = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_playerjump3girata);
        bitmap_jump3g = Bitmap.createScaledBitmap(bitmap_jump3g, screenX/7+30, screenY/11+30, false);
        bitmap_jump4g = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_playerjump4girata);
        bitmap_jump4g = Bitmap.createScaledBitmap(bitmap_jump4g, screenX/7+30, screenY/11+30, false);
        bitmap_jump5g = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_playerjump5girata);
        bitmap_jump5g = Bitmap.createScaledBitmap(bitmap_jump5g, screenX/7+30, screenY/11+30, false);
        bitmap_jump6g = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_playerjump6girata);
        bitmap_jump6g = Bitmap.createScaledBitmap(bitmap_jump6g, screenX/7+30, screenY/11+30, false);
        bitmap_jump7g = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_playerjump7girata);
        bitmap_jump7g = Bitmap.createScaledBitmap(bitmap_jump7g, screenX/7+30, screenY/11+30, false);
        bitmap_jump8g = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungleescape_playerjump8girata);
        bitmap_jump8g = Bitmap.createScaledBitmap(bitmap_jump8g, screenX/7+30, screenY/11+30, false);

        maxY = screenY - bitmap.getHeight();
        maxX = screenX - bitmap.getWidth();
        minX = 0;
        minY = 0;

        detectCollision =  new Rect(x, y, screenX/18, screenY/24);

        Animation idle = new Animation(new Bitmap[]{bitmap}, 2);
        Animation Right = new Animation(new Bitmap[]{bitmap_jump1, bitmap_jump2, bitmap_jump3, bitmap_jump4, bitmap_jump5, bitmap_jump6, bitmap_jump7, bitmap_jump8}, 0.8f);
        Animation Left = new Animation(new Bitmap[]{bitmap_jump1g, bitmap_jump2g, bitmap_jump3g, bitmap_jump4g, bitmap_jump5g, bitmap_jump6g, bitmap_jump7g, bitmap_jump8g}, 0.8f);
        Animation dead = new Animation(new Bitmap[]{bitmap_dead}, 2);
        animationManager= new AnimationManager(new Animation[]{idle, Right, Left, dead});
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        sensorX= (int)event.values[0]*5;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void update() {
        float oldLeft= detectCollision.left;
        state = 0;
        if (!isGameOver)
        x -= sensorX;

        if (x>maxX)
            x=maxX;
        if (x<minX)
            x=minX;

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();

        //Modifichiamo lo stato dell'animazione in base alla posizione del rect collegato al bitmap

        if(detectCollision.left-oldLeft>2){
            state= 1;
            Log.d("entro", "destra " + detectCollision.left + " " + oldLeft);
        } else if(detectCollision.left-oldLeft<-2) {
            Log.d("entro", "sinistra " + detectCollision.left + " " + oldLeft);
            state= 2;
        }
        if(isGameOver) state =3;
        animationManager.playAnim(state);
        animationManager.update();
    }

    void draw(Canvas canvas){
        animationManager.draw(canvas,detectCollision);
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public void getgamestate(boolean isGameOver) {
    this.isGameOver = isGameOver;
    }
}
