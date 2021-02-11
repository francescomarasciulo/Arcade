package com.hightech.arcade.AndroidDoodle;

import com.hightech.arcade.R;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class LeaderDoodle extends AppCompatActivity{

    TextView punteggio1,punteggio2,punteggio3,punteggio4,punteggio5;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodle_leader);

        punteggio1 = findViewById(R.id.punteggio1);
        punteggio2 = findViewById(R.id.punteggio2);
        punteggio3 = findViewById(R.id.punteggio3);
        punteggio4 = findViewById(R.id.punteggio4);
        punteggio5 = findViewById(R.id.punteggio5);

        sharedPreferences  = getSharedPreferences("AndroidDoodle", Context.MODE_PRIVATE);

        punteggio1.setText(getString(R.string.score) +" 1 : "+ sharedPreferences.getInt("punteggio1", 0));
        punteggio2.setText(getString(R.string.score) +" 2 : "+ sharedPreferences.getInt("punteggio2", 0));
        punteggio3.setText(getString(R.string.score) +" 3 : "+ sharedPreferences.getInt("punteggio3", 0));
        punteggio4.setText(getString(R.string.score) +" 4 : "+ sharedPreferences.getInt("punteggio4", 0));
        punteggio5.setText(getString(R.string.score) +" 5 : "+ sharedPreferences.getInt("punteggio5", 0));

    }

}
