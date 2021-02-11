package com.hightech.arcade.AndroidDoodle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.hightech.arcade.R;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;

public class Robot implements SensorEventListener{

    private Bitmap android;
    private int x;
    private int y;
    private int maxY, minY;
    private int screenX;
    private int screenY;
    private int maxX, minX;
    private int sensorX;
    private Rect detectCollision;

    public Robot(Context context, int screenX, int screenY) {

        x = screenX/2-92;
        y = screenY-screenY/11-23;
        this.screenX = screenX;
        this.screenY = screenY;

        //function for sensor
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        }
        //fine

        android = BitmapFactory.decodeResource(context.getResources(), R.drawable.android);
        android = Bitmap.createScaledBitmap(android, android.getWidth()/7+30, android.getHeight()/7+30, false);

        maxY = screenY - android.getHeight();
        maxX = screenX - android.getWidth();
        minX = 0;
        minY = 0;

        //initializing rect object
        detectCollision =  new Rect(x, y, android.getWidth(), android.getHeight());
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        sensorX= (int)event.values[0]*5;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void update() {

        x -= sensorX*2;

        if (x>maxX)
            x=minX;
        if (x<minX)
            x=maxX;

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + android.getWidth();
        detectCollision.bottom = y + android.getHeight();

    }

    public Rect getCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return android;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}


