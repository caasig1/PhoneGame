package com.example.foodgame.GameAdmin;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Parent class for game viewer
 */
public abstract class GameView extends SurfaceView implements SurfaceHolder.Callback {
    /**
     * Constructs catching game view object
     * @param context Context of screen
     */
    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    /**
     * Drawing the game
     * @param canvas The canvas on which everything will be drawn
     */
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
    }
}

