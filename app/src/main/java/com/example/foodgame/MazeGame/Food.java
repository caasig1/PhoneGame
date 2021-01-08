package com.example.foodgame.MazeGame;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Parent class for all foods.
 */
abstract class Food{

    /**
     * Location of the food object.
     */
    private int[] location;

    /**
     * Constructor for the Food class.
     * @param location is the location of the food, which is for drawing it onto the canvas.
     */
    Food(int[] location) {
        this.location = location;
    }

    /**
     * Getter for the location of the Food object.
     * @return the location of the Food object.
     */
    int[] getLocation() {
        return this.location;
    }

    /**
     * When Food is eaten, I want to indicate its type with an integer.
     * @return integer corresponding to the type of food for FoodManager to know.
     */
    abstract int getEaten();

    /**
     * Draws the object on to the screen.
     * @param canvas The canvas on which the Food is being drawn
     * @param context The context from which BitMap will get its resources.
     * @param tileSize The size of each tile.
     */
    abstract void draw(Canvas canvas, Context context, int tileSize);
}