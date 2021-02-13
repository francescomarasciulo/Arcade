package com.hightech.arcade.JungleRun;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import com.hightech.arcade.Global.HomeActivity;
import com.hightech.arcade.R;
import android.widget.TextView;

public class JungleRunActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_junglerun2d);

        SharedPreferences preferences = getSharedPreferences("partita", MODE_PRIVATE);

        TextView highscore = findViewById(R.id.highscore);
        highscore.setText(getString(R.string.high_score) + " " + preferences.getInt("highest score", 0));

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharacterInfo.manYPos = (int) (GameView.screenY / 2.4);
                startActivity(new Intent(JungleRunActivity.this, GameActivity.class));
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(JungleRunActivity.this, HomeActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(JungleRunActivity.this, HomeActivity.class));
    }

}
