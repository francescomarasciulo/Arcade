package com.hightech.arcade.AndroidDoodle;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.hightech.arcade.R;

public class SfondoDoodle {

    int x = 0;
    int y = 0;

    Bitmap space;

    SfondoDoodle (int screenX, int screenY, Resources res) {

        space = BitmapFactory.decodeResource(res, R.drawable.doodle_space);
        space = Bitmap.createScaledBitmap(space, screenX, screenY, false);

    }

}
