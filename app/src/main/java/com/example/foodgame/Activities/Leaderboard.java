package com.example.foodgame.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.example.foodgame.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Activity to display the Leaderboard
 */
public class Leaderboard extends AppCompatActivity {

    /**
     * The onCreate method that initiates the activity
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        Button restart = findViewById(R.id.btnRestart);

        ArrayList<String> currentUsernames = new ArrayList<>();
        ArrayList<String> currentStatisticsStrings = new ArrayList<>();
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<String> statisticsStrings = new ArrayList<>();

        //Getting the latest scores
        SharedPreferences preferences = getSharedPreferences("CURRENTGAME", Context.MODE_PRIVATE);
        Map<String, ?> allEnteries = preferences.getAll();
        for(Map.Entry<String, ?> entry : allEnteries.entrySet()){
            if (ActivityManager.ManageActivities.statisticsWriter.getUsernames().contains(
                    entry.getKey())) {
                currentUsernames.add(entry.getKey());
                currentStatisticsStrings.add(entry.getValue().toString());
            }
        }

        //Getting the high scores
        SharedPreferences preferences1 = getSharedPreferences("OVERALLBEST", Context.MODE_PRIVATE);
        Map<String, ?> allEnteries1 = preferences1.getAll();
        for(Map.Entry<String, ?> entry : allEnteries1.entrySet()){
            usernames.add(entry.getKey());
            statisticsStrings.add(entry.getValue().toString());
        }

        String[] statisticsNames = {"Lives", "Score", "Time"};

        LinearLayout linearLayout = findViewById(R.id.leaderboardLayout);
        setLayoutContent(usernames, statisticsStrings, statisticsNames, linearLayout);

        LinearLayout linearLayout2 = findViewById(R.id.userScoresLayout);
        setLayoutContent(currentUsernames, currentStatisticsStrings, statisticsNames,
                linearLayout2);

        //set what to do when button restart is clicked
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Leaderboard.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Method to set the layout for scrollable view
     * @param usernames the list of usernames to be displayed
     * @param statisticsStrings the statistics of each user
     * @param statisticsNames the statistics we are tracking
     * @param linearLayout the linear layout widget we want to display this data on
     */
    private void setLayoutContent(ArrayList<String> usernames, ArrayList<String> statisticsStrings,
                                  String[] statisticsNames, LinearLayout linearLayout) {
        for (int user = 0; user < usernames.size(); user++) {
            // Create string to write involving statistics names and values
            String statistics = statisticsStrings.get(user).
                    replace("[","").replace("]","");
            List<String> statisticsList = Arrays.asList(statistics.split(",",-1));
            StringBuilder writeString = new StringBuilder(usernames.get(user) + ":   ");
            // Add each stat to the string
            for (int stat = 0; stat < statisticsList.size(); stat++) {
                writeString.append("    ").append(statisticsNames[stat]).append(": ").append(
                        statisticsList.get(stat));
            }

            // Create the text view and insert it into the linear layout
            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            textView1.setText(writeString.toString());
            textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
            linearLayout.addView(textView1);
        }

    }
}
