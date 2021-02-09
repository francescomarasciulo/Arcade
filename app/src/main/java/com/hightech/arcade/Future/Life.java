package com.hightech.arcade.Future;

import com.hightech.arcade.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import static com.hightech.arcade.Future.GameView.screenRatioX;
import static com.hightech.arcade.Future.GameView.screenRatioY;
import static com.hightech.arcade.Future.GameView.hit;

public class Life {

    int x, y, width, height;
    Bitmap life1, life2, life3;

    Life (Resources res) {

        life1 = BitmapFactory.decodeResource(res, R.drawable.future_life1);
        life2 = BitmapFactory.decodeResource(res, R.drawable.future_life2);
        life3 = BitmapFactory.decodeResource(res, R.drawable.future_life3);

        width = life3.getWidth();
        height = life3.getHeight();

        width /= 3.9;
        height /= 3.9;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        life1 = Bitmap.createScaledBitmap(life1, width, height, false);
        life2 = Bitmap.createScaledBitmap(life2, width, height, false);
        life3 = Bitmap.createScaledBitmap(life3, width, height, false);
    }

    Bitmap getLife () {
        if (hit == 0) return life3;
        if (hit == 1) return life2;
        return life1;
    }
}
