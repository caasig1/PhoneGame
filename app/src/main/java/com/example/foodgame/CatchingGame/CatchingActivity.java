package com.example.foodgame.CatchingGame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/**
 * The Activity for Catching Game
 */
public class CatchingActivity extends AppCompatActivity {

    /**
     * The onCreate method that initiates the game with a statistics writer and a context.
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CatchingGameView gameView = new CatchingGameView(this);
        setContentView(gameView);
    }
}
