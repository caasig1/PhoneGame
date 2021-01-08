package com.example.foodgame.GameAdmin;

import android.graphics.Canvas;

/**
 * The interface that is being used by all facades in games
 */
public interface GameFacade {

    /**
     * Updates and draws the necessary game facade constituent parts
     * @param canvas The canvas on which everything is drawn
     */
    void update(Canvas canvas);
}