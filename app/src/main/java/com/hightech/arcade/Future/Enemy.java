package com.hightech.arcade.Future;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import com.hightech.arcade.R;

public class Enemy {

    public int speed = 20;
    public int x, y, width, height, enemyCount = 0;
    public Bitmap alien0, alien1, alien2, alien3, alien4, alien5, alien6, alien7, alien8;


    Enemy (Resources res, int screenX, int screenY){

        alien0 = BitmapFactory.decodeResource(res, R.drawable.future_alien0);
        alien1 = BitmapFactory.decodeResource(res, R.drawable.future_alien1);
        alien2 = BitmapFactory.decodeResource(res, R.drawable.future_alien2);
        alien3 = BitmapFactory.decodeResource(res, R.drawable.future_alien3);
        alien4 = BitmapFactory.decodeResource(res, R.drawable.future_alien4);
        alien5 = BitmapFactory.decodeResource(res, R.drawable.future_alien5);
        alien6 = BitmapFactory.decodeResource(res, R.drawable.future_alien6);
        alien7 = BitmapFactory.decodeResource(res, R.drawable.future_alien7);
        alien8 = BitmapFactory.decodeResource(res, R.drawable.future_alien8);

        height = (int) (screenY / 3.1);
        width = (int) (screenX / 7.7);

        alien0 = Bitmap.createScaledBitmap(alien0, width, height, false);
        alien1 = Bitmap.createScaledBitmap(alien1, width, height, false);
        alien2 = Bitmap.createScaledBitmap(alien2, width, height, false);
        alien3 = Bitmap.createScaledBitmap(alien3, width, height, false);
        alien4 = Bitmap.createScaledBitmap(alien4, width, height, false);
        alien5 = Bitmap.createScaledBitmap(alien5, width, height, false);
        alien6 = Bitmap.createScaledBitmap(alien6, width, height, false);
        alien7 = Bitmap.createScaledBitmap(alien7, width, height, false);
        alien8 = Bitmap.createScaledBitmap(alien8, width, height, false);

        y = -height;
    }

    Bitmap getEnemy (){

        if (enemyCount == 0){ enemyCount++; return alien0;}
        if (enemyCount == 1){ enemyCount++; return alien1;}
        if (enemyCount == 2){ enemyCount++; return alien2;}
        if (enemyCount == 3){ enemyCount++; return alien3;}
        if (enemyCount == 4){ enemyCount++; return alien4;}
        if (enemyCount == 5){ enemyCount++; return alien5;}
        if (enemyCount == 6){ enemyCount++; return alien6;}
        if (enemyCount == 7){ enemyCount++; return alien7;}
        enemyCount = 0;
        return alien8;

    }

    Rect getCollision () {return new Rect(x ,y , x + width, y + height); }

}
