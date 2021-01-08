package com.example.foodgame.MonsterGame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/**
 * The Activity for game 3
 */
public class MonsterActivity extends AppCompatActivity {

    /**
     * The onCreate method that initiates the game with a statistics writer and a context.
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MonsterGameView gameView = new MonsterGameView(this);
        setContentView(gameView);
    }
}
