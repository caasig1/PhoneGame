package com.example.foodgame.Statistics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;


/**
 * Holds static class DrawStatistics
 */
class StatisticsDrawer {

    /**
     * Handles the drawing of the game statistics on the canvas
     */
    public static class DrawStatistics {

        /**
         * Draws the given statistics on the screen at with a given background
         * @param canvas Game canvas
         * @param background The background bitmap drawn behind the statistics
         * @param statistics Statistics values
         * @param statisticNames Names of the statistics values
         * @param username Name of the current user
         * @param gameHeight Height of game
         * @param gameWidth Width of game
         */
        public static void draw(Canvas canvas, Bitmap background, int[] statistics,
                                String[] statisticNames, String username, int gameHeight,
                                int gameWidth) {
            // Create background resizing and draw
            RectF dst = new RectF(0, 0, gameWidth, 54*2);
            canvas.drawBitmap(background,null, dst, null);

            // Create new paint
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize((gameWidth/5)/statistics.length);

            // Write the statistics to screen
            for (int i = 0; i < statistics.length; i++) {
                canvas.drawText(statisticNames[i] + ": " + statistics[i],
                        i*gameWidth/3 + gameWidth/50, gameHeight/26, paint);
            }

            // Write the username to screen
            canvas.drawText(username, gameWidth/50, (gameHeight/26)*2, paint);
        }
    }
}
