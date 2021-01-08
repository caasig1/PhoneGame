package com.example.foodgame.MazeGame;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import com.example.foodgame.R;

import java.util.Random;

/** A class to implement a maze. **/
class MazeManager {

    /**
     * A two-dimensional array representing the appearance of the maze.
     * Each cell contains a string representing its contents:
     * Wall: "wall"
     * Open cell: "open"
     * Unreachable squares: "unreachable"
     */
    private String[][] mazeAppearance;

    /**
     * The width of a maze tile to fit on the screen.
     */
    private int tileSize;

    /**
     * Bitmap for non-moving parts of the maze
     */
    private Bitmap allBackground;

    /**
     * Integer corresponding to the maze image
     */
    private int randomMaze;

    /**
     * Dimensions of the maze
     */
    private int dimension;

    /**
     * Constructor for the Maze class that codes the maze.
     * @param context The context from which Bitmaps will get their resources
     * @param screenWidth The width of the screen
     * @param screenHeight The height of the screen
     */
    MazeManager(Context context, int screenWidth, int screenHeight) {
        //make a maze factory that returns a random maze as well as dimension of that maze
        MazeFactory mazeFactory = new MazeFactory();
        int numberMazes = 10;
        Random rand = new Random();
        int n = rand.nextInt(numberMazes);
        int[] mazeInfo = mazeFactory.makeMaze(n);

        this.randomMaze = mazeInfo[0];
        this.dimension = mazeInfo[1];
        this.tileSize = screenWidth/dimension;
        this.mazeAppearance = makeMaze(context);
        this.allBackground = combineNonMoving(context, screenWidth, screenHeight);
    }

    /**
     * Returns the size of a tile in the maze
     * @return integer corresponding to the side length of a tile
     */
    int getTileSize() {
        return tileSize;
    }

    /**
     * Returns the first open spot on the map where the player should spawn.
     * @return location of player's starting position
     */
    int[] playerStart(){
        int playerX = 0;
        int playerY = 0;
        for (int i=0; i<dimension; i++) {
            for (int j=0; j<dimension; j++) {
                if (mazeAppearance[i][j].equals("open")) {
                    playerX = i;
                    playerY = j;
                    break;
                }
            }
        }
        return new int[] {playerX, playerY};
    }

    /**
     * Makes a maze of size N by N.
     * Wall spaces are set to X
     * Open spaces are set to O
     * Unreachable spaces are set to unreachable (i.e. area in a circle if player is outside the
     *      circle.
     * @param context The context from which bitmaps will get their resources
     * @return the maze that will appear on the screen, shown with X and Os
     */
    private String[][] makeMaze(Context context) {
        String[][] maze = new String[this.dimension][this.dimension];

        Bitmap picture = BitmapFactory.decodeResource(context.getResources(), this.randomMaze);
        Bitmap scaled = Bitmap.createScaledBitmap(picture,dimension*10,dimension*10,false);
        int scale = 10;
        int center = 5;
        for (int i = 0; i<=this.dimension-1; i++){
            for (int j = 0; j<=this.dimension-1; j++){
                if (scaled.getPixel(scale*(i) + center, scale*(j) + center) == -16777216) {
                    maze[i][j] = "wall";
                } else if (scaled.getPixel(scale*i,scale*j) == -1) {
                    maze[i][j] = "unreachable";
                } else {
                    maze[i][j] = "open";
                }
            }
        }
        return maze;
    }

    /**
     * Drawing the map on to the screen.
     * @param context The context from which the Bitmap will get its resources
     * @param background This is used for the configuration so both the maze drawn on a new canvas
     *                   here as well as the background for the game are the same
     * @param width The width for the Bitmap to be created on
     * @param height The height for the Bitmap to be created on
     * @return Provides a Bitmap for the manager to draw each time
     * Before, there were issues regarding lag since each picture in the NxN maze was drawn but,
     * now it only draws one picture each time, being the collective maze.
     */
    private Bitmap combineMaze(Context context, Bitmap background, int width, int height) {
        Bitmap maze = Bitmap.createBitmap(width,height,background.getConfig());

        Canvas canvas1 = new Canvas(maze);
        for (int xCoord = 0; xCoord < dimension; xCoord++) {
            for (int yCoord = 0; yCoord < dimension; yCoord++) {
                if (mazeAppearance[xCoord][yCoord].equals("wall")) {
                    Bitmap display = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.maze_wall);
                    RectF dst = new RectF(xCoord*tileSize, yCoord*tileSize, (xCoord+1)*tileSize,
                            (yCoord+1)*tileSize);
                    canvas1.drawBitmap(display, null, dst, null);
                }
            }
        }
        return maze;
    }

    /**
     * Combine the non-moving parts of the maze to reduce lag issues by reducing the number of
     * items being drawn each frame.
     * @param context The context from which Bitmaps will get their resources
     * @param width The width of the screen
     * @param height The height of the screen
     * @return Bitmap of non-moving parts
     */
    private Bitmap combineNonMoving(Context context, int width, int height) {
        Bitmap allBackground = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(allBackground);

        // Draw the background onto canvas
        Bitmap back = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.maze);
        RectF dst = new RectF(0, 0, width, height);
        canvas.drawBitmap(back,null, dst, null);

        // Draw the maze onto canvas
        Bitmap mazeMap = combineMaze(context, allBackground, width, height);
        int mazeShift = 2 * 54;
        RectF dstMap = new RectF(0,mazeShift , width, height + mazeShift);
        canvas.drawBitmap(mazeMap, null, dstMap, null);

        // Draw the back of joystick onto canvas
        int joystickRadius = 4;
        Bitmap joystickBackground = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.joystick_background_image);
        int placementHelp = 54;
        int xTopLeft = (10 - joystickRadius) * placementHelp;
        int yTopLeft = (30 - joystickRadius) * placementHelp;
        int xBottomRight = (10 + joystickRadius) * placementHelp;
        int yBottomRight = (30 + joystickRadius) * placementHelp;
        RectF dstJoystick = new RectF(xTopLeft, yTopLeft, xBottomRight, yBottomRight);
        canvas.drawBitmap(joystickBackground, null, dstJoystick, null);

        return allBackground;
    }

    /**
     * Draw the maze
     * @param canvas Canvas on which everything is drawn
     * @param width Width of the screen
     * @param height Height of the screen
     */
    void draw(Canvas canvas, int width, int height) {
        RectF dst2 = new RectF(0, 0, width, height);
        canvas.drawBitmap(allBackground,null,dst2,null);
    }

    /**
     * Gets the maze appearance for further usage in other classes.
     * @return Provides the appearance of the map in the 20x20 array of X and Os
     */
    String[][] getMazeAppearance() {
        return mazeAppearance;
    }
}