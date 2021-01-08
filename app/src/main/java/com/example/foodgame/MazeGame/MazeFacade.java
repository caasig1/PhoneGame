package com.example.foodgame.MazeGame;
import android.content.Context;
import android.graphics.Canvas;

import com.example.foodgame.Activities.ActivityManager;
import com.example.foodgame.GameAdmin.GameFacade;
import com.example.foodgame.Statistics.StatisticsManager;
import com.example.foodgame.GameAdmin.TouchHandler;

/**
 * The class that manages the game, the facade of maze game.
 */
class MazeFacade implements GameFacade {
    /**
     * Instance of FoodManager
     */
    private FoodManager foodManager;

    /**
     * An instance of the statistics manager
     */
    private StatisticsManager statisticsManager;

    /**
     * An instance of JoystickFacade.
     */
    private JoystickManager joystickManager;

    /**
     * An instance of MazeManager.
     */
    private MazeManager mazeManager;

    /**
     * An instance of PlayerManager.
     */
    private PlayerManager playerManager;

    /**
     * Height of screen.
     */
    private int screenHeight;

    /**
     * Width of screen.
     */
    private int screenWidth;

    /**
     * Context for Bitmaps later
     */
    private Context context;

    /**
     * An instance of the touch handling
     */
    private TouchHandler touchHandler;

    /**
     * Constructor of the MazeFacade class.
     * @param height height of the screen
     * @param width width of the screen
     * @param cnt context for Bitmaps
     * @param touchHandler touchscreen for joystick
     */
    MazeFacade(int height, int width, Context cnt, TouchHandler touchHandler) {
        this.screenHeight = height;
        this.screenWidth = width;
        this.touchHandler = touchHandler;
        this.context = cnt;

        //Make the maze
        this.mazeManager = new MazeManager(cnt, screenWidth, screenHeight);
        int tileSize = mazeManager.getTileSize();

        //Create the managers
        this.playerManager = new PlayerManager(tileSize, mazeManager.playerStart());
        this.joystickManager = new JoystickManager();
        this.foodManager = new FoodManager(tileSize, mazeManager.getMazeAppearance(), playerManager.getLocation());
        this.statisticsManager = new StatisticsManager(context);

        //Add observers to various classes
        foodManager.addObserver(statisticsManager);
        playerManager.addObserver(statisticsManager);
        statisticsManager.addObserver(ActivityManager.ManageActivities.statisticsWriter);

    }

    /**
     * Update the game every time something happens.
     * JoystickManager updates player's direction.
     * Player does/does not move depending on wall positions.
     * Food location changes if player eats it.
     * Player's health changes according to the food.
     * Player loses health over time.
     * @param canvas The canvas on which everything is drawn
     */
    @Override
    public void update(Canvas canvas) {
        //Update managers
        mazeManager.draw(canvas, screenWidth, screenHeight);
        joystickManager.update(touchHandler, canvas, context);
        playerManager.setDirection(joystickManager.getDirection());
        playerManager.update(mazeManager.getMazeAppearance(), canvas, context);
        int healthChange = foodManager.update(playerManager.getLocation(), canvas, context);
        statisticsManager.update(canvas,screenHeight,screenWidth);

        //Change player's health accordingly
        playerManager.addHealthPoints(healthChange);
        playerManager.addHealthPoints(-0.5);
    }
}
