package com.example.foodgame.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodgame.R;
import com.example.foodgame.Statistics.StatisticsWriter;

import java.util.ArrayList;

/**
 * Activity that creates the login screen
 */
public class Login extends AppCompatActivity {

    /**
     * The username of the user
     */
    private EditText username;

    /**
     * The password of the user
     */
    private EditText password;

    /**
     * The message to be displayed if username or password is incorrect
     */
    private TextView message;

    /**
     * The onCreate method that initiates the activity
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        Button login = findViewById(R.id.btnLogin);
        Button signup = findViewById(R.id.btnRegister);
        message = findViewById(R.id.etMessage);
        Button howTo = findViewById(R.id.btnHowToPLay);

        login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                SharedPreferences preferences = getSharedPreferences("DATA", MODE_PRIVATE);
                String details = preferences.getString(user, "Username or Password is incorrect");
                if (details.equals(pass)) {
                    openNextActivity(user);
                } else {
                    message.setText("Username or password is incorrect");
                }
            }
        });

        //set what to do if signup button is clicked
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registration = new Intent(Login.this, Registration.class);
                startActivity(registration);
            }
        });

        //set what to do if how to play button is clicked
        howTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHowToPlay();
            }
        });
    }

    /**
     * Decide how to open next activity
     * @param user is the current user
     */
    private void openNextActivity(String user){
        StatisticsWriter writer = ActivityManager.ManageActivities.statisticsWriter;
        writer.addUsername(user);
        writer.setContext(getApplicationContext());
        if (writer.getNumberOfUsers() == 1) {
            int onGame = writer.getLastGame(user);
            ArrayList<Class> activityClasses;
            if (onGame >= writer.getTotalGames() || onGame == -1) {
                writer.loading = false;
                activityClasses =
                        ActivityManager.ManageActivities.getActivityClasses(writer.gameClasses,
                                1);
            }
            else {
                writer.loading = true;
                writer.loadStatistics(user);
                ArrayList<Class> gameClasses = writer.getGames(user);
                activityClasses = ActivityManager.ManageActivities.getActivityClassesSaved(
                        gameClasses, onGame);
            }
            ActivityManager.ManageActivities.activityClasses = activityClasses;
        }

        Class nextClass = ActivityManager.ManageActivities.activityClasses.get(0);
        ActivityManager.ManageActivities.activityClasses.remove(0);
        Intent intent = new Intent(this, nextClass);
        startActivity(intent);
    }

    /**
     * Method to open the How to play screen
     */
    private void openHowToPlay(){
        Intent intent = new Intent(Login.this, HowToPlay.class);
        startActivity(intent);
    }
}