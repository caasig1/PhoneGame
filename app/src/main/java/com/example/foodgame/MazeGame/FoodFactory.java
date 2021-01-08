package com.example.foodgame.MazeGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This is a food factory where new food is created.
 */
class FoodFactory {

    /**
     * Create and return a food according to the string type given
     * @param foodType The type of the food represented by a string
     * @param playerLocation The position of the player in the maze
     * @param maze The maze setup
     * @param foods The food in the maze
     * @return Return the newly created food
     */
    Food makeFood(String foodType, int[] playerLocation, String[][] maze, ArrayList<Food> foods){
        if(foodType.equals("good")) {
            return new GoodFood(createNewLocation(playerLocation, maze, foods));
        }
        else if (foodType.equals("bad")) {
            return new BadFood(createNewLocation(playerLocation, maze, foods));
        }
        else {
            return null;
        }
    }

    /**
     * Generates a random location for new food.
     * @param playerLocation The current position of the player in the maze
     * @param maze The maze setup
     * @param foods The food in the maze
     * @return new location that is unoccupied
     */
    private int[] createNewLocation(int[] playerLocation, String[][] maze, ArrayList<Food> foods) {
        Random r = new Random();
        int xCoord =  r.nextInt((maze.length-1) + 1);
        int yCoord = r.nextInt((maze.length-1) + 1);
        int[] location = new int[] {xCoord, yCoord};

        //If it is not an open cell, keep randomly choosing locations
        while (!isOpenCell(location, maze, playerLocation, foods)){
            xCoord =  r.nextInt((maze.length-1) + 1);
            yCoord = r.nextInt((maze.length-1) + 1);
            location = new int[] {xCoord, yCoord};
        }
        return location;
    }

    /**
     * Returns if the given cell is open.
     * @param location location to check for food
     * @param mazeAppearance The maze setup
     * @param playerLocation The current position of the player in the maze
     * @param foods The food in the maze
     * @return true if the square is not currently occupied by another object and is open
     */
    private boolean isOpenCell(int[] location, String[][] mazeAppearance, int[] playerLocation, ArrayList<Food> foods) {
        //If it is in the same spot as player
        if (Arrays.equals(location, playerLocation)) {
            return false;
        }
        //If it is in the same spot as any other food
        for (Food food: foods){
            if (Arrays.equals(location,food.getLocation())){
                return false;
            }
        }
        return mazeAppearance[location[0]][location[1]].equals("open");
    }

}
