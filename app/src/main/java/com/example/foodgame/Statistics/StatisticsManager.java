package com.example.foodgame.Statistics;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;


import com.example.foodgame.Activities.ActivityManager;
import com.example.foodgame.R;

import java.util.Observable;
import java.util.Observer;

/**
 * Manages statistics for each individual game
 */
public class StatisticsManager extends Observable implements Observer {
    /**
     * The number of lives of the current player
     */
    private int lives;

    /**
     * The score of the current player
     */
    private int score;

    /**
     * The time at which game starts
     */
    public long startTime;

    /**
     * The game context
     */
    private Context context;

    /**
     * The list of game statistics
     */
    private String[] statisticsNames;

    /**
     * Whether the statistics have been sent to the StatisticsWriter
     */
    private boolean hasSentStatistics = false;

    /**
     * A boolean dictating whether to draw the loading background
     */
    private boolean drawLoadingBackground;

    /**
     * Constructor for the Statistics manager
     * @param context Game context
     */
    public StatisticsManager(Context context) {
        // Set attributes
        this.lives = 3;
        this.score = 0;
        this.drawLoadingBackground = false;
        this.context = context;
        this.statisticsNames = ActivityManager.ManageActivities.
                statisticsWriter.getStatisticsNames();
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Update the statistics when with an observer update
     * @param obs The observer
     * @param statisticObject The statistic being changed, passed in as an Object
     */
    @Override
    public void update(Observable obs, Object statisticObject) {
        String statistic = (String) statisticObject;

        // Update statistics depending on statistics message
        if (statistic.equals("lives")) {
            lives -= 1;
        }
        if (statistic.equals("score")) {
            score += 5;
        }
        if (statistic.equals("game over")) {
            gameOver();
        }

        if (lives <= 0) {
            gameOver();
        }
    }

    /**
     * Ends the game, sends the statistics back to statistics writer and finish the activity.
     */
    private void gameOver() {
        // Only send the statistics if not already sent
        if (!hasSentStatistics) {
            int[] statistics = getStatistics();

            // Setup the next activity
            Class nextClass = ActivityManager.ManageActivities.activityClasses.get(0);
            ActivityManager.ManageActivities.activityClasses.remove(0);
            ActivityManager.ManageActivities.statisticsWriter.setContext(context);
            Intent intent = new Intent(context, nextClass);

            // Notify the statistics writer
            setChanged();
            notifyObservers(statistics);
            hasSentStatistics = true;

            // Draw loading screen while switching activities
            drawLoadingBackground = true;

            // Begin the next activity
            context.startActivity(intent);
        }
    }

    /**
     * Draws the statistics on screen and potentially the loading background
     * @param canvas The canvas on which the Statistics is being drawn
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    public void update(Canvas canvas, int screenHeight, int screenWidth) {
        // Draw statistics to screen
        if (!drawLoadingBackground) {
            StatisticsDrawer.DrawStatistics.draw(canvas,
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.black),
                    getStatistics(), getStatisticsNames(),
                    ActivityManager.ManageActivities.statisticsWriter.getCurrentUsername(),
                    screenHeight, screenWidth);
        }
        // Draw loading background to screen
        else {
            RectF dst = new RectF(0, 0, screenWidth, screenHeight);
            canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.loading_screen), null, dst, null);
        }
    }


    /**
     * Compiles and returns a list of all the the game statistics
     * @return list of integers for live, score, time
     */
    private int[] getStatistics() {
        int time = (int) ((System.currentTimeMillis() - startTime) / 1000);
        return new int[] {lives, score, time};
    }

    /**
     * Returns the list of names of the game statistics
     * @return list of Strings of statistics names
     */
    private String[] getStatisticsNames() {
        return statisticsNames;
    }
}


