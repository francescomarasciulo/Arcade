package com.hightech.arcade.JungleEscape;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.hightech.arcade.Global.HomeActivity;
import com.hightech.arcade.R;

public class JungleEscapeActivity extends Activity implements View.OnClickListener {

    private ImageButton buttonPlay;
    private ImageButton buttonScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jungleescape_home);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);

        buttonScore = (ImageButton) findViewById(R.id.buttonScore);

        buttonScore.setOnClickListener(this);

        buttonPlay.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if(v==buttonPlay){
            startActivity(new Intent(JungleEscapeActivity.this, GameActivity.class));
        }
        if(v==buttonScore){
            startActivity(new Intent(JungleEscapeActivity.this,HighScore.class));
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(JungleEscapeActivity.this, HomeActivity.class));
    }
}