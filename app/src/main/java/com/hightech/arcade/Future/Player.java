package com.hightech.arcade.Future;

import com.hightech.arcade.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import static com.hightech.arcade.Future.GameView.screenRatioX;

public class Player {

    int toShoot = 0;
    boolean IsGoingUp = false;
    int x, y, width, height;
    private int flyCount = 0, shootCount = 1;
    Bitmap jet1, jet2, jet3;
    private GameView gameView;
    private Rect pCollision;

    Player (GameView gameView, int screenY, int screenX, Resources res){

        this.gameView = gameView;

        jet1 = BitmapFactory.decodeResource(res, R.drawable.future_jet1);
        jet2 = BitmapFactory.decodeResource(res, R.drawable.future_jet2);
        jet3 = BitmapFactory.decodeResource(res, R.drawable.future_jet3);

        height = (int) (screenY / 3.1);
        width = (int) (screenX / 7.7);

        jet1 = Bitmap.createScaledBitmap(jet1, width, height, false);
        jet2 = Bitmap.createScaledBitmap(jet2, width, height, false);
        jet3 = Bitmap.createScaledBitmap(jet3, width, height, false);

        y = screenY / 2;
        x = (int) (64 * screenRatioX);

        pCollision = new Rect(x, y, width + x, height + y);
    }

    public void update(){
        pCollision.top = y - 10;
        pCollision.bottom = y + jet1.getHeight() - 20;
    }

    Bitmap getPlayer(){

        //function for future_bullet
        if (toShoot != 0){
            if (shootCount == 1) {
                shootCount++;
            }
            shootCount = 1;
            toShoot --;
            gameView.newBullet();
        }
        //fine

        if (flyCount == 0){
            flyCount++;
            return jet1;
        }
        if (flyCount == 1) {
            flyCount++;
            return jet2;
        }
        flyCount = 0;
        return jet3;
    }

    Rect getCollision (){ return pCollision; }

}
