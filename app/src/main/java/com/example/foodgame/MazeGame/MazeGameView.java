package com.example.foodgame.MazeGame;

import com.example.foodgame.GameAdmin.GameView;
import com.example.foodgame.GameAdmin.MainThread;
import com.example.foodgame.GameAdmin.TouchHandler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceHolder;

/**
 * The viewer for maze game
 */
@SuppressLint("ViewConstructor")
public class MazeGameView extends GameView {

    /**
     * Instance of the MazeFacade
     */
    MazeFacade mazeFacade;

    /**
     * Instance of the thread being used
     */
    private MainThread thread;

    /**
     * Constructor for the viewer
     * @param context The context from which BitMap will get its resources.
     */
    MazeGameView(Context context) {
        super(context);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        TouchHandler touchHandler = thread.touchHandler;
        setFocusable(true);
        Paint paintText = new Paint();
        paintText.setTextSize(36);
        paintText.setTypeface(Typeface.DEFAULT_BOLD);

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        mazeFacade = new MazeFacade(
                screenHeight, screenWidth, context, touchHandler);


    }

    /**
     * Drawing the game
     * @param canvas The canvas on which everything will be drawn
     */
    @Override
    public void draw(Canvas canvas) {
        if (canvas != null) {
            super.draw(canvas);
            mazeFacade.update(canvas);
        }
    }

    /**
     * Create a surface
     * @param holder The holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    /**
     * Change the surface
     * @param holder Holder
     * @param format format
     * @param width width
     * @param height height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /**
     * Destroy the surface
     * @param holder Holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }
}
