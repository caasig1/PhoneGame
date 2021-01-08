package com.example.foodgame.MazeGame;

import com.example.foodgame.R;

/**
 * This is a maze factory where a mazes will be chosen randomly as the maze for the game
 */
class MazeFactory {

    /**
     * This chooses a random maze from the few that are placed into R.drawable
     * You will need to add the dimensions of the maze when adding a new maze
     * Also make sure the image is of high resolution or the pixels will not be solid colours and
     * more like shades which will affect how walls are detected.
     * @return Return the integer corresponding to the maze image in R.drawable
     */
    int[] makeMaze(int n) {
        int maze;
        int dimensions;
        if (n == 0){
            maze = R.drawable.maze1;
            dimensions = 20;
        } else if (n == 1){
            maze = R.drawable.maze2;
            dimensions = 20;
        } else if (n == 2){
            maze = R.drawable.maze3;
            dimensions = 20;
        } else if (n == 3){
            maze = R.drawable.maze4;
            dimensions = 24;
        } else if (n == 4){
            maze = R.drawable.maze5;
            dimensions = 20;
        } else if (n == 5){
            maze = R.drawable.maze6;
            dimensions = 20;
        } else if (n == 6){
            maze = R.drawable.maze7;
            dimensions = 32;
        } else if (n == 7){
            maze = R.drawable.maze8;
            dimensions = 41;
        } else if (n == 8){
            maze = R.drawable.maze9;
            dimensions = 26;
        } else {
            maze = R.drawable.maze10;
            dimensions = 18;
        }
        return new int[] {maze, dimensions};
    }
}
