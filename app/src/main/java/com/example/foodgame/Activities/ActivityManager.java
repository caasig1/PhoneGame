package com.example.foodgame.Activities;

import android.annotation.SuppressLint;

import com.example.foodgame.Statistics.StatisticsWriter;

import java.util.ArrayList;

/**
 * Manager for Activities
 */
public class ActivityManager {

    /**
     * Static class to manage activities
     */
    public static class ManageActivities {

        /**
         * List of activity classes
         */
        public static ArrayList<Class> activityClasses;

        /**
         * The statistics writer that keeps track of statistics for all players
         */
        @SuppressLint("StaticFieldLeak")
        public static StatisticsWriter statisticsWriter;

        /**
         * Getter for activity classes in the order for which they are played
         * @param gameClasses The classes for the games
         * @param playerNumber The player who is playing the game (if it is two player)
         * @return The list of classes that are called/played
         */
        static ArrayList<Class> getActivityClasses(ArrayList<Class> gameClasses,
                                                   int playerNumber) {
            ArrayList<Class> activityClasses = new ArrayList<>();
            if (playerNumber != 1) {
                for (int playerCount = 0; playerCount < playerNumber; playerCount++) {
                    activityClasses.add(Login.class);
                }
            }
            for (int gameCount = 0; gameCount < gameClasses.size(); gameCount++) {
                for (int playerCount = 0; playerCount < playerNumber; playerCount++) {
                    activityClasses.add(gameClasses.get(gameCount));
                }
            }
            activityClasses.add(OptionalSaveStatistics.class);
            activityClasses.add(Leaderboard.class);

            return activityClasses;
        }

        /**
         * Get the activity classes that were saved when the user quit the game in middle of running
         * @param gameClasses the classes for the games
         * @param onGame integer that represents the game in which the current usser quit last time.
         * @return the list of classes that were saved
         */
        static ArrayList<Class> getActivityClassesSaved(ArrayList<Class> gameClasses,
                                                        int onGame) {
            for (int gameCount = onGame; gameCount < gameClasses.size(); gameCount++) {
                activityClasses.add(gameClasses.get(gameCount));
            }
            activityClasses.add(OptionalSaveStatistics.class);
            activityClasses.add(Leaderboard.class);

            return activityClasses;
        }
    }
}


