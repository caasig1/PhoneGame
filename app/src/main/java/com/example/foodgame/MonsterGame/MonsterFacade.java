package com.example.foodgame.MonsterGame;

import android.content.Context;
import android.graphics.Canvas;

import com.example.foodgame.Activities.ActivityManager;
import com.example.foodgame.GameAdmin.GameFacade;
import com.example.foodgame.Statistics.StatisticsManager;
import com.example.foodgame.GameAdmin.TouchHandler;

import java.util.Observable;

/**
 * Class that manages the game, the facade of monster game.
 */

class MonsterFacade extends Observable implements GameFacade {
    /**
     * The player manager object and constituent facade part
     */
    private PlayerManager playerManager;

    /**
     * The monster manager object and constituent facade part
     */
    private MonsterManager monsterManager;

    /**
     * The statistics manager object and constituent facade part
     */
    private StatisticsManager statisticsManager;

    /**
     * The touch handler to communicate finger movement
     */
    private TouchHandler touchHandler;

    /**
     * The touch manager communicates with touch handler to make sure the user's finger is down
     */
    private TouchManager touchManager;

    /**
     * The current context
     */
    private Context context;

    /**
     * The height of the screen
     */
    private final int gameHeight;

    /**
     * The width of the screen
     */
    private final int gameWidth;

    /**
     * MonsterFacade constructor
     * @param gameHeight The height of the screen
     * @param gameWidth The width of the screen
     * @param context The context from which BitMap will get its resources.
     * @param touchHandler The touch handler for finger tracking
     */
    MonsterFacade(int gameHeight, int gameWidth, Context context, TouchHandler touchHandler) {
        // Base the size of the monsters on the screen width
        /* The draw size of a monster. */
        int monsterSize = gameWidth / 6;

        // Create constituent MonsterFacade managers
        playerManager = new PlayerManager(gameHeight, gameWidth);
        monsterManager = new MonsterManager(gameHeight, gameWidth, monsterSize);
        statisticsManager = new StatisticsManager(context);
        touchManager = new TouchManager();
        this.touchHandler = touchHandler;
        this.context = context;
        this.gameHeight = gameHeight;
        this.gameWidth = gameWidth;

        // Create observer relationships
        monsterManager.addObserver(statisticsManager);
        this.addObserver(statisticsManager);
        statisticsManager.addObserver(ActivityManager.ManageActivities.statisticsWriter);
    }

    /**
     * Update the facade's constituent parts
     * @param canvas The canvas on which everything is drawn
     */
    @Override
    public void update(Canvas canvas) {
        // Only begin the game if the player has touched the screen first
        if (touchManager.getTouchState()) {
            playerManager.update(touchHandler.getPosition());
            monsterManager.update(playerManager.getX(), playerManager.getY(), canvas, context);

            // Draw statistics
            statisticsManager.update(canvas, gameHeight, gameWidth);

            // If the player removes their finger from the screen, end the game
            if (!touchManager.playerTouching(touchHandler)) {
                setChanged();
                notifyObservers("game over");
            }
        }
        else {
            touchManager.playerTouching(touchHandler);
            statisticsManager.startTime = System.currentTimeMillis();
        }
    }
}
