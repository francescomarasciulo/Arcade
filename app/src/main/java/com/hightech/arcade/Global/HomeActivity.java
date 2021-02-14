package com.hightech.arcade.Global;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import com.hightech.arcade.AndroidDoodle.DoodleActivity;
import com.hightech.arcade.Future.FutureActivity;
import com.hightech.arcade.JungleEscape.JungleEscapeActivity;
import com.hightech.arcade.JungleRun.JungleRunActivity;
import com.hightech.arcade.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    public static SharedPreferences username;
    protected static AlertDialog dialog;
    protected static ArrayList <String> userList = new ArrayList<>();
    protected static ArrayList <String> scoreList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_home);

        username = getSharedPreferences("Username", Context.MODE_PRIVATE);

        if(Connection.haveInternetConnection(HomeActivity.this )) {
            if(username.getString("username", "").equals("")) {
                myDialog();
            }
            else {
                TextView welcome = findViewById(R.id.welcome);
                welcome.setText(getString(R.string.welcome) + " " + username.getString("username", ""));
            }
        }
        else {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.internetMessage)
                    .show();
        }

        findViewById(R.id.futureStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, FutureActivity.class));
                finish();
            }
        });

        findViewById(R.id.androidDoodleStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, DoodleActivity.class));
                finish();
            }
        });

        findViewById(R.id.jungleEscapeStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, JungleEscapeActivity.class));
                finish();
            }
        });

        findViewById(R.id.jungleRunStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, JungleRunActivity.class));
                finish();
            }
        });

    }

    private void myDialog() {
        final EditText input = new EditText(this);
        dialog = new AlertDialog.Builder(this)
                .setTitle("ARCADE")
                .setMessage(R.string.welcomeMessage)
                .setCancelable(false)
                .setPositiveButton(R.string.insert, null)
                .setView(input)
                .show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String value = String.valueOf(input.getText());
                if(!input.getText().toString().trim().isEmpty()) {
                    if(input.getText().toString().trim().length() < 30) {
                        SharedPreferences.Editor editor = username.edit();
                        editor.putString("username", value.trim());
                        editor.apply();
                        insertUserDB();
                        TextView welcome = findViewById(R.id.welcome);
                        welcome.setText(getString(R.string.welcome) + " " + username.getString("username", ""));
                    }
                    else {
                        usernameDialog();
                    }
                }
            }
        });
    }

    private void usernameDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.errorUsername)
                .show();
        HomeActivity.username.edit().clear().apply();
    }

    private void insertUserDB() {
        NewUserDB newUserDB = new NewUserDB(this);
        newUserDB.execute("Registration", username.getString("username", ""),"");
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.classificaglobale);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (Connection.haveInternetConnection(HomeActivity.this )) {
            GetScoreDB getScoreDB = new GetScoreDB(this);
            switch (item.getItemId()) {
                case R.id.item1:
                    getScoreDB.execute("Get leaderboard", "Future","");
                    return true;
                case R.id.item2:
                    getScoreDB.execute("Get leaderboard", "Android_Doodle","");
                    return true;
                case R.id.item3:
                    getScoreDB.execute("Get leaderboard", "Jungle_Escape","");
                    return true;
                case R.id.item4:
                    getScoreDB.execute("Get leaderboard", "Jungle_Run","");
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}