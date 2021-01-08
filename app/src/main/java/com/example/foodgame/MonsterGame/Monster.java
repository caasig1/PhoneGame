package com.example.foodgame.MonsterGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.foodgame.R;

/**
 * Monster class that is the parent class for all types of monsters
 */
class Monster {

     /**
      * x coordinate of monster
      */
     protected int x;

     /**
      * y coordinate of monster
      */
     protected int y;

     /**
      * speed of monster
      */
     protected int speed;

     /**
      * The height of the screen
      */
     private int gameHeight;

     /**
      * The width of the screen
      */
     int gameWidth;

     /**
      * Size of the monster
      */
     protected int size;

     /**
      * Monster constructor
      * @param x The x coordinate for where the monster spawns
      * @param speed The speed of the monster
      * @param gameHeight The height of the screen
      * @param size The size of the monster
      */
     Monster(int x, int speed, int gameHeight, int gameWidth, int size) {
          this.speed = speed;
          this.x = x;
          this.gameHeight = gameHeight;
          this.gameWidth = gameWidth;
          this.size = size;
     }

     /**
      * Getter for monster's y position
      * @return position of monster
      */
     int getYPosition() {
          return y;
     }

     /**
      * Getter for monster's x position
      * @return x position of monster
      */
     int getXPosition() {
          return x;
     }

     /**
      * Setter for monster's y position
      * @param y is the new y position of the monster
      */
     void setYPosition(int y) {
          this.y = y;
     }

     /**
      * Checks whether the monster has passed the bottom of the screen
      * @return true if y has passed, meaning the monster is off the screen
      */
     boolean isPassed() {
          return (y >= gameHeight);
     }

     /**
      * Draws the monster
      * @param canvas The canvas on which the monster is drawn
      * @param context The context from which BitMap will get its resources.
      */
     void draw(Canvas canvas, Context context) {
          Bitmap display = BitmapFactory.decodeResource(context.getResources(), R.drawable.monster);
          RectF dst = new RectF(getXPosition(), getYPosition(), getXPosition() + size,
                  getYPosition() + size);
          canvas.drawBitmap(display, null, dst, null);
     }
}