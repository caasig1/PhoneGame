package com.example.foodgame.MonsterGame;

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
 * The viewer for monster game
 */
@SuppressLint("ViewConstructor")
public class MonsterGameView extends GameView {

     /**
      * The bitmap of the background
      */
     Bitmap background;

     /**
      * The shape of the background
      */
     RectF dst;

     /**
      * Instance of a MonsterFacade
      */
     private MonsterFacade manager;

     /**
      * Instance of a thread for Monster Game
      */
     private MainThread thread;

     /**
      * Constructor for the game viewer of monster game
      * @param context The context from which BitMap will get its resources.
      */
     MonsterGameView(Context context) {
          super(context);
          getHolder().addCallback(this);

          background = BitmapFactory.decodeResource(context.getResources(),
                  R.drawable.monster_game_kitchen);
          int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
          int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
          dst = new RectF(0, 0, screenWidth, screenHeight);

          thread = new MainThread(getHolder(), this);
          TouchHandler touchHandler = thread.touchHandler;
          setFocusable(true);

          manager = new MonsterFacade(screenHeight, screenWidth, context, touchHandler);

     }

     /**
      * Drawing the game
      * @param canvas The canvas on which everything is drawn
      */
     @Override
     public void draw(Canvas canvas) {
          if (canvas != null) {
               super.draw(canvas);
               canvas.drawBitmap(background,null, dst, null);
               manager.update(canvas);
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
