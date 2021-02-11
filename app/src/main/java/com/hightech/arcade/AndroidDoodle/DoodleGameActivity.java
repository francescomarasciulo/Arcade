package com.hightech.arcade.AndroidDoodle;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class DoodleGameActivity extends AppCompatActivity {

    private DoodleGameView doodleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        doodleView = new DoodleGameView(this, point.x, point.y);
        setContentView(doodleView);
    }

    @Override
    protected void onPause(){
        super.onPause();
        doodleView.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        doodleView.resume();
    }


}
