package com.example.foodgame.MazeGame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * The Activity for Maze Game
 */
public class MazeActivity extends AppCompatActivity {

    /**
     * The onCreate method that initiates the game with a statistics writer and a context.
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MazeGameView gameView = new MazeGameView(this);
        setContentView(gameView);
    }
}
