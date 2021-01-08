package com.example.foodgame.GameAdmin;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

public class MainThread extends Thread{

    /**
     * The instance for the viewer of the game
     */
    private GameView gameView;

    /**
     * The instance of the surfaceHolder
     */
    private final SurfaceHolder surfaceHolder;

    /**
     * The instance of the touchHandler used in player manager
     */
    public TouchHandler touchHandler;

    /**
     * The boolean indicating if the game is running
     */
    private boolean isRunning;


    /**
     * Constructor for the thread
     * @param surfaceHolder the surfaceHolder being used in the game viewer
     * @param gameView the maze game viewer
     */
    @SuppressLint("ClickableViewAccessibility")
    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {

        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;

        this.touchHandler = new TouchHandler();
        this.gameView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                touchHandler.setPosition(arg1);


                if(arg1.getAction() == MotionEvent.ACTION_UP){
                    touchHandler.setHolding(false);
                }

                if(arg1.getAction() == MotionEvent.ACTION_DOWN){
                    touchHandler.setHolding(true);
                }

                return true;
            }
        });
    }

    /**
     * Run the thread
     */
    @Override
    public void run() {
        while (isRunning) {
            Canvas canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                sleep(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Change the state of the thread (running or not)
     * @param isRunning boolean representing the state of thread
     */
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}
