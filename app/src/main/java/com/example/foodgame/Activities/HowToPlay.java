package com.example.foodgame.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodgame.R;

/**
 * The activity to display how to play the game
 */
public class HowToPlay extends AppCompatActivity {

    /**
     * The onCreate method that initiates the activity
     * @param savedInstanceState The saved instance state
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);
        Button ready = findViewById(R.id.btnReady);
        TextView instructions = findViewById(R.id.tvInstruction);

        //set insturctions to display on the screen
        instructions.setTextSize(20);
        instructions.setText("HOW TO PLAY THE GAME" + "\n" + "\n" +  "Burger Builder" + "\n"  +
                "1. Catch the ingredient on the     menu card" +"\n" +
                "2. Catch the wrong item and the     game ends" + "\n" +
                "3. Miss the ingredient on the menu     card and lose a life" + "\n" + "\n" +
                "Maze" + "\n" +
                "1. Eat good food in the maze to     replenish your hunger bar" + "\n" +
                "2. The game ends when your     hunger bar reaches zero" + "\n" +
                "3. Eat bad food and lose a life" + "\n" + "\n" +
                "Fruit Fly Dodge" + "\n" +
                "1. Hold finger on screen, if you lift it     the game ends" + "\n" +
                "2. Avoid the fruit flies or you lose a     life" + "\n");

        //set what to do when button ready is clicked
        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HowToPlay.this, Login.class);
                startActivity(intent);
            }
        });
    }
}
