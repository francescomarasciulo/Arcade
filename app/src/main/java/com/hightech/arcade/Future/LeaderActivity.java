package com.hightech.arcade.Future;

import com.hightech.arcade.R;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LeaderActivity extends AppCompatActivity {

    TextView record1,record2,record3,record4;
    SharedPreferences sharedPreferences;

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

        setContentView(R.layout.activity_future_leader);

        record1 = findViewById(R.id.score1);
        record2 = findViewById(R.id.score2);
        record3 = findViewById(R.id.score3);
        record4 = findViewById(R.id.score4);

        sharedPreferences  = getSharedPreferences("Future", Context.MODE_PRIVATE);

        record1.setText(getString(R.string.score) +" 1 : "+ sharedPreferences.getInt("score1", 0));
        record2.setText(getString(R.string.score) +" 2 : "+ sharedPreferences.getInt("score2", 0));
        record3.setText(getString(R.string.score) +" 3 : "+ sharedPreferences.getInt("score3", 0));
        record4.setText(getString(R.string.score) +" 4 : "+ sharedPreferences.getInt("score4", 0));

    }
}
