package com.hightech.arcade.Future;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.hightech.arcade.R;

public class Background {

    int x = 0;
    int y = 0;
    Bitmap back1;

    Background (int screenX, int screenY, Resources res){

        back1= BitmapFactory.decodeResource(res, R.drawable.future_back1);
        back1= Bitmap.createScaledBitmap(back1, screenX, screenY, false);

    }
}
