package com.hightech.arcade.JungleRun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.hightech.arcade.Global.HomeActivity;
import com.hightech.arcade.R;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_junglerun2d_gameover);

        findViewById(R.id.playAgain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharacterInfo.manYPos = (int) (GameView.screenY / 2.4);
                startActivity(new Intent(GameOver.this, GameActivity.class));
            }
        });

        findViewById(R.id.quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(GameOver.this, HomeActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(GameOver.this, HomeActivity.class));
    }



}
