package com.example.foodgame.MazeGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.foodgame.R;

import java.util.Observable;

/**
 * Class that represents the player.
 */
class PlayerManager extends Observable {

    /**
     * Health points of the player.
     */
    private double healthPoints;

    /**
     * x-coordinate of the player.
     */
    private int x;

    /**
     * y-coordinate of the player.
     */
    private int y;

    /**
     * String representing the direction of the player: UP, DOWN, LEFT, RIGHT
     */
    private String direction;

    /**
     * Tile size of each tile in the maze.
     */
    private int tileSize;

    /**
     * Constructor for the player
     * @param tileSize size of a tile in the maze
     * @param startingPosition the initial position of the player
     */
    PlayerManager(int tileSize, int[] startingPosition) {

        this.x = startingPosition[0]*tileSize;
        this.y = startingPosition[1]*tileSize;

        this.direction = "DOWN";
        this.healthPoints = 100;
        this.tileSize = tileSize;
    }

    /**
     * Getter for player's location.
     * @return int[] of x-coordinate, y-coordinate
     */
    int[] getLocation() {
        return new int[] {this.x/tileSize, this.y/tileSize};
    }

    /**
     * Getter for player's direction.
     * @return String direction = UP, DOWN, LEFT, RIGHT
     */
    private String getDirection() {
        return this.direction;
    }

    /**
     * Setter for player's direction.
     * @param newDirection: new direction of the player
     */
    void setDirection(String newDirection) {
        this.direction = newDirection;
    }

    /**
     * Setter for player's HP.
     * @param change: increment to player's HP.
     */
    void addHealthPoints(double change) {
        if (this.healthPoints + change < 0) {
            this.healthPoints = 0;
        } else if (this.healthPoints + change > 100) {
            this.healthPoints = 100;
        } else {
            this.healthPoints += change;
        }
    }

    /**
     * Moves player in its direction if possible.
     * Once the health reaches zero, the game ends.
     * @param maze The appearance of the maze as a list of lists
     * @param canvas The canvas on which everything will be drawn
     * @param context The context from which Bitmaps will get their resources
     */
    void update(String[][] maze, Canvas canvas, Context context) {
        if (this.direction.equals("UP") && checkWall(maze)) {
            this.y -= tileSize;
        } else if (this.direction.equals("DOWN") && checkWall(maze)) {
            this.y += tileSize;
        } else if (this.direction.equals("LEFT") && checkWall(maze)) {
            this.x -= tileSize;
        } else if (this.direction.equals("RIGHT") && checkWall(maze)) {
            this.x += tileSize;
        }
        if (this.healthPoints <= 0) {
            setChanged();
            notifyObservers("game over");
        }
        draw(canvas, context);
    }

    /**
     * Return if there is a wall in the direction that the player wants to move.
     * @param mazeAppearance The maze's appearance as a list of lists
     * @return true if yes, false if no.
     */
    private boolean checkWall(String[][] mazeAppearance) {
        int[] playerLocation = this.getLocation();

        switch (this.getDirection()) {
            case "UP": {
                int[] tempLocation = new int[]{playerLocation[0], playerLocation[1] - 1};
                return (isOpenCell(tempLocation, mazeAppearance));

            }
            case "RIGHT": {
                int[] tempLocation = new int[]{playerLocation[0] + 1, playerLocation[1]};
                return (isOpenCell(tempLocation, mazeAppearance));


            }
            case "DOWN": {
                int[] tempLocation = new int[]{playerLocation[0], playerLocation[1] + 1};
                return (isOpenCell(tempLocation, mazeAppearance));


            }
            default: {
                int[] tempLocation = new int[]{playerLocation[0] - 1, playerLocation[1]};
                return (isOpenCell(tempLocation, mazeAppearance));
            }
        }
    }

    /**
     * Returns if the given cell is open.
     * @param location location of the player
     * @param mazeAppearance appearance of the maze
     * @return true if the cell is open
     */
    private boolean isOpenCell(int[] location, String[][] mazeAppearance) {
        return mazeAppearance[location[0]][location[1]].equals("open");
    }

    /**
     * Draws the player on to the screen.
     * @param canvas The canvas on which the Player is being drawn
     * @param context The context from which BitMap will get its resources.
     */
    private void draw(Canvas canvas, Context context) {
        Bitmap display = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.maze_player_2);
        int mazeShift = 54*2;

        //dimensions/location of the player
        int xTopLeft = this.getLocation()[0]*tileSize;
        int yTopLeft = this.getLocation()[1] * tileSize + mazeShift;
        int xBottomRight = (this.getLocation()[0] + 1) * tileSize;
        int yBottomRight = (this.getLocation()[1] + 1) * tileSize + mazeShift;
        RectF dst = new RectF(xTopLeft, yTopLeft, xBottomRight, yBottomRight);
        canvas.drawBitmap(display,null,dst,null);

        //health bar for the player
        double percent = healthPoints * 0.01;
        int portion = 54 * 19;
        int roundingHealth = (int) (portion * percent);
        Bitmap display2 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.health_bar);
        RectF dst2 = new RectF(54, 54*24-27, roundingHealth, 54*25-27);
        canvas.drawBitmap(display2,null,dst2,null);
    }

}