package com.example.foodgame.MazeGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.foodgame.R;

/**
 * Class that represents the food that adds health points to the player when eaten.
 */
class GoodFood extends Food {

    /**
     * Constructor for the class.
     * @param location is the location of the food, which is for drawing it onto the canvas.
     */
    GoodFood(int[] location) {
        super(location);
    }

    /**
     * When goodFood is eaten, I want to indicate it has a positive effect by returning 20.
     * @return 20 so that FoodManager knows it is a good food and heals player by 20
     */
    @Override
    int getEaten(){
        return 20;
    }

    /**
     * Draws the object on to the screen.
     * @param canvas The canvas on which the GoodFood is being drawn
     * @param context The context from which BitMap will get its resources.
     * @param tileSize The size of each tile.
     */
    @Override
    void draw(Canvas canvas, Context context, int tileSize) {
        Bitmap display = BitmapFactory.decodeResource(context.getResources(), R.drawable.good_food);
        int mazeShift = 54 * 2;

        //Dimensions/location of the food
        int xTopLeft = this.getLocation()[0] * tileSize;
        int yTopLeft = this.getLocation()[1] * tileSize + mazeShift;
        int xBottomRight = (this.getLocation()[0] + 1) * tileSize;
        int yBottomRight = (this.getLocation()[1] + 1) * tileSize + mazeShift;

        RectF dst = new RectF(xTopLeft, yTopLeft,xBottomRight, yBottomRight);
        canvas.drawBitmap(display,null,dst,null);
    }

}