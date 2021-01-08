package com.example.foodgame.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.foodgame.CatchingGame.CatchingActivity;
import com.example.foodgame.MazeGame.MazeActivity;
import com.example.foodgame.MonsterGame.MonsterActivity;
import com.example.foodgame.R;
import com.example.foodgame.Statistics.StatisticsWriter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Activity to be opened when the app is started
 */
public class MainActivity extends AppCompatActivity {

    private int playerNumber;

    /**
     * The onCreate method that initiates the activity
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button singlePlayer = findViewById(R.id.btnSinglePlayer);
        Button twoPlayer = findViewById(R.id.btnTwoPLayer);

        //set what to do when button single player is clicked
        singlePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerNumber = 1;
                nextActivity();
            }
        });

        //set what to do when button two player is clicked
        twoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerNumber = 2;
                nextActivity();
            }
        });
    }


    private void nextActivity() {
        // Create an array list of the game activity classses
        ArrayList<Class> gameClasses = new ArrayList<>();
        gameClasses.add(CatchingActivity.class);
        gameClasses.add(MazeActivity.class);
        gameClasses.add(MonsterActivity.class);

        // Randomize game order
        Collections.shuffle(gameClasses);

        // Create a statistics writer
        StatisticsWriter statisticsWriter = new StatisticsWriter(3,
                new String[] {"Lives", "Score", "Time"}, gameClasses, playerNumber);

        ArrayList<Class> activityClasses;
        if (playerNumber > 1) {
            activityClasses =
                    ActivityManager.ManageActivities.getActivityClasses(gameClasses,
                            playerNumber);
        }
        else {
            activityClasses = new ArrayList<>();
            activityClasses.add(Login.class);
        }
        ActivityManager.ManageActivities.activityClasses = activityClasses;
        ActivityManager.ManageActivities.statisticsWriter = statisticsWriter;

        // Begin the first intent
        Class nextClass = ActivityManager.ManageActivities.activityClasses.get(0);
        ActivityManager.ManageActivities.activityClasses.remove(0);
        Intent intent = new Intent(getApplicationContext(), nextClass);
        startActivity(intent);
    }

}