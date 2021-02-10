package com.hightech.arcade.JungleEscape;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.hightech.arcade.R;

public class HighScore extends AppCompatActivity {

    TextView bestScore;
    TextView score2, score3, score4;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jungleescape_leader);

        bestScore = (TextView) findViewById(R.id.textView);
        score2 = (TextView) findViewById(R.id.textView2);
        score3 = (TextView) findViewById(R.id.textView3);
        score4 = (TextView) findViewById(R.id.textView4);

        sharedPreferences  = getSharedPreferences("Jungleescape", Context.MODE_PRIVATE);

        bestScore.setTextColor(Color.parseColor("#000000"));
        bestScore.setText(getString(R.string.score) +" 1: "+sharedPreferences.getInt("scores1",0));
        score2.setTextColor(Color.parseColor("#000000"));
        score2.setText(getString(R.string.score) +" 2: "+sharedPreferences.getInt("scores2",0));
        score3.setTextColor(Color.parseColor("#000000"));
        score3.setText(getString(R.string.score) +" 3: "+sharedPreferences.getInt("scores3",0));
        score4.setTextColor(Color.parseColor("#000000"));
        score4.setText(getString(R.string.score) +" 4: "+sharedPreferences.getInt("scores4",0));

    }
}
