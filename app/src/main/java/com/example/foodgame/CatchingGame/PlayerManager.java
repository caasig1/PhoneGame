package com.example.foodgame.CatchingGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.foodgame.R;
import com.example.foodgame.GameAdmin.TouchHandler;

/** Class for a player object
 */
class PlayerManager{
     
     /**
      * x coordinate of player object
      */
     private int x;

     /**
      * y coordinate of player object
      */
     private final int y;
     
     /**
      * Width of the player object
      */
     private final int width;

     /**
      * Height of the player object
      */
     private final int height;
     
     /** Constructs a player object
      * @param gridHeight height of screen
      * @param gridWidth width of screen
      * @param numberOfLanes number lanes in which objects can fall
      */
     PlayerManager(int gridHeight, int gridWidth, int numberOfLanes) {
          width = gridWidth/numberOfLanes;
          height = width/3;
          x = gridWidth/2;
          y = 3 * gridHeight/4;
     }
     
     /**
      * Method to update player object attributes
      * @param canvas The canvas on which everything is drawn
      * @param context The context from which Bitmaps get resources
      * @param touchHandler The touch handling for the game
      */
     void update(Canvas canvas, Context context, TouchHandler touchHandler) {
          this.x = touchHandler.getPosition()[0];
          
          draw(canvas, context);
     }
     
     /**
      * Method to draw player object on screen
      * @param canvas The canvas on which everything is drawn
      * @param context The context from which Bitmaps get resources
      */
     private void draw (Canvas canvas, Context context){
          Bitmap display = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
          RectF dst = new RectF(x - width/2, y, x + width/2, y + height);
          
          canvas.drawBitmap(display, null, dst, null);
     }

     /**
      * Getter for location of the player
      * @return Returns the coordinates of the player as x,y coordinates
      */
     int[] getLocation() {
          return new int[] {x, y};
     }

     /**
      * Getter for the dimensions of the player
      * @return Returns the dimensions of the player as width,height values
      */
     int[] getDimensions(){
          return new int[] {width, height};
     }
}
