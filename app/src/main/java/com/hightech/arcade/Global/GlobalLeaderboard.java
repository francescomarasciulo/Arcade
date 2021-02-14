package com.hightech.arcade.Global;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.hightech.arcade.R;

public class GlobalLeaderboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_global_leaderboard);

        getScore();

    }

    private void getScore() {
        for(int i = 0; i< HomeActivity.userList.size(); i++) {
            switch (i) {
                case 0:
                    TextView firstUser = findViewById(R.id.firstUser);
                    TextView firstScore = findViewById(R.id.firstScore);
                    firstUser.setText(HomeActivity.userList.get(i));
                    firstScore.setText(HomeActivity.scoreList.get(i));
                    break;
                case 1:
                    TextView secondUser = findViewById(R.id.secondUser);
                    TextView secondScore = findViewById(R.id.secondScore);
                    secondUser.setText(HomeActivity.userList.get(i));
                    secondScore.setText(HomeActivity.scoreList.get(i));
                    break;
                case 2:
                    TextView thirdUser = findViewById(R.id.thirdUser);
                    TextView thirdScore = findViewById(R.id.thirdScore);
                    thirdUser.setText(HomeActivity.userList.get(i));
                    thirdScore.setText(HomeActivity.scoreList.get(i));
                    break;
                case 3:
                    TextView fourthUser = findViewById(R.id.fourthUser);
                    TextView fourthScore = findViewById(R.id.fourthScore);
                    fourthUser.setText(HomeActivity.userList.get(i));
                    fourthScore.setText(HomeActivity.scoreList.get(i));
                    break;
                case 4:
                    TextView fifthUser = findViewById(R.id.fifthUser);
                    TextView fifthScore = findViewById(R.id.fifthScore);
                    fifthUser.setText(HomeActivity.userList.get(i));
                    fifthScore.setText(HomeActivity.scoreList.get(i));
                    break;
                case 5:
                    TextView sixthUser = findViewById(R.id.sixthUser);
                    TextView sixthScore = findViewById(R.id.sixthScore);
                    sixthUser.setText(HomeActivity.userList.get(i));
                    sixthScore.setText(HomeActivity.scoreList.get(i));
                    break;
                case 6:
                    TextView seventhUser = findViewById(R.id.seventhUser);
                    TextView seventhScore = findViewById(R.id.seventhScore);
                    seventhUser.setText(HomeActivity.userList.get(i));
                    seventhScore.setText(HomeActivity.scoreList.get(i));
                    break;
                case 7:
                    TextView eighthUser = findViewById(R.id.eighthUser);
                    TextView eighthScore = findViewById(R.id.eighthScore);
                    eighthUser.setText(HomeActivity.userList.get(i));
                    eighthScore.setText(HomeActivity.scoreList.get(i));
                    break;
                case 8:
                    TextView ninthUser = findViewById(R.id.ninthUser);
                    TextView ninthScore = findViewById(R.id.ninthScore);
                    ninthUser.setText(HomeActivity.userList.get(i));
                    ninthScore.setText(HomeActivity.scoreList.get(i));
                    break;
                case 9:
                    TextView tenthUser = findViewById(R.id.tenthUser);
                    TextView tenthScore = findViewById(R.id.tenthScore);
                    tenthUser.setText(HomeActivity.userList.get(i));
                    tenthScore.setText(HomeActivity.scoreList.get(i));
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void onBackPressed() {
        HomeActivity.userList.clear();
        HomeActivity.scoreList.clear();
        finish();
        startActivity(new Intent(GlobalLeaderboard.this, HomeActivity.class));
    }

}