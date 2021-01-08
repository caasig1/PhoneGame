package com.example.foodgame.CatchingGame;

import android.content.Context;
import android.graphics.Canvas;

import com.example.foodgame.Activities.ActivityManager;
import com.example.foodgame.GameAdmin.GameFacade;
import com.example.foodgame.Statistics.StatisticsManager;
import com.example.foodgame.GameAdmin.TouchHandler;

/**
 * Facade class to manage functions of the Catching Game
 */
class CatchingFacade implements GameFacade {

    /**
     * Game height in this instance of the game
     */
    private int gridHeight;

    /**
     * Game width in this instance of the game
     */
    private int gridWidth;

    /**
     * Game context for Bitmap resources
     */
    private Context context;

    /**
     * Touch handler to get user finger location on screen
     */
    private TouchHandler touchHandler;

    /**
     * Store instance of ingredient manager class
     */
    private IngredientManager ingredientManager;

    /**
     * Store instance of player manager class
     */
    private PlayerManager playerManager;

    /**
     * Store instance of game statistics manager
     */
    private StatisticsManager statisticsManager;

    /**
     * Constructs CatchingFacade
     * @param height The height of screen in this instance of the game
     * @param width The width of screen in this instance of the game
     * @param context The context of screen in this instance of the game
     * @param touchHandler The touch manager object
     */
    CatchingFacade(int height, int width, Context context, TouchHandler touchHandler) {
        gridHeight = height;
        gridWidth = width;
        this.context = context;
        this.touchHandler = touchHandler;
        int numberOfLanes = 6;

        //Create the Managers
        statisticsManager = new StatisticsManager(context);
        ingredientManager = new IngredientManager(gridWidth, numberOfLanes);
        playerManager = new PlayerManager(gridHeight, gridWidth, numberOfLanes);

        //Adding observers to interacting classes
        ingredientManager.addObserver(statisticsManager);
        statisticsManager.addObserver(ActivityManager.ManageActivities.statisticsWriter);

    }

    /**
     * Update method to update changes in the individual manager objects
     * @param canvas The canvas on which everything is drawn
     */
    @Override
    public void update(Canvas canvas) {
        ingredientManager.update(canvas, context, playerManager.getLocation(), playerManager.getDimensions());
        playerManager.update(canvas, context, touchHandler);
        statisticsManager.update(canvas, gridHeight, gridWidth);
    }
}