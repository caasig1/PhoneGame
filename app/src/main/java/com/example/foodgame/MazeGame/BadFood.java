package com.example.foodgame.MazeGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.foodgame.R;

/**
 * Class that represents food that removes lives when eaten by the player.
 */
class BadFood extends Food {

    /**
     * Constructor for the class.
     * @param location is the location of the food, which is for drawing it onto the canvas.
     */
    BadFood(int[] location) {
        super(location);
    }

    /**
     * When badFood is eaten, I want to indicate it has a negative effect by returning -1.
     * @return -1 so that FoodManager knows it is a bad food
     */
    @Override
    int getEaten(){
        return -1;
    }

    /**
     * Draws the object on to the screen.
     * @param canvas The canvas on which the BadFood is being drawn
     * @param context The context from which BitMap will get its resources.
     * @param tileSize The size of each tile.
     */
    @Override
    void draw(Canvas canvas, Context context, int tileSize) {
        Bitmap display = BitmapFactory.decodeResource(context.getResources(), R.drawable.bad_food);
        int mazeShift = 2 * 54;

        //Dimensions/location of Food
        int xTopLeft = this.getLocation()[0] * tileSize;
        int yTopLeft = this.getLocation()[1] * tileSize + mazeShift;
        int xBottomRight = (this.getLocation()[0] + 1) * tileSize;
        int yBottomRight = (this.getLocation()[1] + 1) * tileSize + mazeShift;

        RectF dst = new RectF(xTopLeft, yTopLeft,xBottomRight, yBottomRight);
        canvas.drawBitmap(display,null,dst,null);
    }
}