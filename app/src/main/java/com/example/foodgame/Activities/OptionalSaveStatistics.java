package com.example.foodgame.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.foodgame.R;

/**
 * Class asking if statistics want to be saved
 */
public class OptionalSaveStatistics extends AppCompatActivity {

    /**
     * The onCreate method that initiates the game with a statistics writer and a context.
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optional_save_statistics);
        Button saveStatistics = findViewById(R.id.btnsave);
        Button discardStatistics = findViewById(R.id.btndiscard);

        saveStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.ManageActivities.statisticsWriter.writeScoreboardStatistics();
                nextActivity();
            }
        });

        discardStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity();
            }
        });
    }

    /**
     * Moves the game onto the next activity
     */
    private void nextActivity() {
        // Begin the next intent
        Class nextClass = ActivityManager.ManageActivities.activityClasses.get(0);
        ActivityManager.ManageActivities.activityClasses.remove(0);
        Intent intent = new Intent(getApplicationContext(), nextClass);
        startActivity(intent);
    }
}