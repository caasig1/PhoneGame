package com.example.foodgame.MazeGame;
import android.content.Context;
import android.graphics.Canvas;
import java.util.Observable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that represents food generator.
 */
class FoodManager extends Observable {

    /**
     * List of food in the game.
     */
    private ArrayList<Food> foods;

    /**
     * Length of block.
     */
    private int tileSize;

    /**
     * Array that represents the maze cells and walls.
     */
    private String[][] maze;

    /**
     * The factory in which food is created
     */
    private FoodFactory foodFactory;

    /**
     * Constructor for the class.
     * @param tileSize The size of a tile on the screen
     * @param maze The maze setup
     * @param playerLocation The player's starting location
     */
    FoodManager(int tileSize, String[][] maze, int[] playerLocation) {
        foods = new ArrayList<>();
        this.tileSize = tileSize;
        this.maze = maze;
        int foodNumber = 3;
        this.foodFactory = new FoodFactory();

        //Create the food
        for (int i = 0; i < foodNumber; i++) {
            foods.add(foodFactory.makeFood("good", playerLocation, maze, foods));
            foods.add(foodFactory.makeFood("bad", playerLocation, maze, foods));
        }
    }

    /** Update to check if a player and a food share the same location.
     * @param playerLocation location of the player
     * @param canvas The canvas on which everything is drawn
     * @param context The context from which the BitMap will get its resources
     * @return Indicate type of food and/or change in player's health
     */
    int update(int[] playerLocation, Canvas canvas, Context context){
        int healthPointChange = 0;

        int counter = 0;
        while (counter < foods.size()) {
            Food food = foods.get(counter);
            // If player eats the food
            if (Arrays.equals(playerLocation, food.getLocation())) {
                healthPointChange += food.getEaten();
                foods.remove(counter);
                setChanged();
                if (healthPointChange > 0) {
                    notifyObservers("score");
                    foods.add(foodFactory.makeFood("good", playerLocation, maze, foods));
                } else {
                    notifyObservers("lives");
                    foods.add(foodFactory.makeFood("bad", playerLocation, maze, foods));
                }
            }
            else {
                counter++;
            }
        }
        draw(canvas, context);
        return healthPointChange;
    }

    /**
     * Draws the Food objects on to the screen.
     * @param canvas The canvas on which the food is being drawn
     * @param context The context from which BitMap will get its resources.
     */
    private void draw(Canvas canvas, Context context) {
        for (Food food: foods){
            food.draw(canvas, context, tileSize);
        }
    }
}