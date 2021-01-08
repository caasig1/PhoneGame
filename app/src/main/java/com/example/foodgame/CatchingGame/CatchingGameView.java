package com.example.foodgame.CatchingGame;

import com.example.foodgame.GameAdmin.GameView;
import com.example.foodgame.GameAdmin.MainThread;
import com.example.foodgame.R;
import com.example.foodgame.GameAdmin.TouchHandler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.SurfaceHolder;

/**
 * Class to set up view (screen contents) for catching game
 */
@SuppressLint("ViewConstructor")
public class CatchingGameView extends GameView {

    /**
     * Instance of the catching game
     */
    public CatchingFacade catchingFacade;

    /**
     * Instance of the thread for catching game
     */
    private MainThread thread;

    /**
     * The background of the game
     */
    private Bitmap background;

    /**
     * The dimensions for the background of the game
     */
    private RectF dst;
    
    /** Constructs catching game view object
     * @param context Context of screen
     */
    CatchingGameView(Context context) {
        super(context);
        getHolder().addCallback(this);

        //Get the background ready for canvas drawing
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background2);
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        dst = new RectF(0,0,screenWidth,screenHeight);

        thread = new MainThread(getHolder(), this);
        TouchHandler touchHandler = thread.touchHandler;
        catchingFacade = new CatchingFacade(screenHeight, screenWidth, context,
                touchHandler);
        setFocusable(true);
    }

    /**
     * Draw method to draw objects on screen
     * @param canvas The canvas on which everything is drawn
     */
    @Override
    public void draw(Canvas canvas) {
        if (canvas != null) {
            super.draw(canvas);
            canvas.drawBitmap(background, null, dst, null);
            catchingFacade.update(canvas);
        }
    }

    /**
     * Create surface
     * @param holder Holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    /**
     * Change surface
     * @param holder Holder
     * @param format Format
     * @param width Width
     * @param height Height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /**
     * Destroy surface
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
