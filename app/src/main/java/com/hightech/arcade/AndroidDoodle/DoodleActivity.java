package com.hightech.arcade.AndroidDoodle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import com.hightech.arcade.Global.HomeActivity;
import com.hightech.arcade.R;

public class DoodleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodle);

        findViewById(R.id.doodlePlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoodleActivity.this, DoodleGameActivity.class));
            }
        });

        findViewById(R.id.doodleScore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoodleActivity.this, LeaderDoodle.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DoodleActivity.this, HomeActivity.class));
        finish();
    }
}