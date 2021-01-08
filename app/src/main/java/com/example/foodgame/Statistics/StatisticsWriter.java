package com.example.foodgame.Statistics;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodgame.CatchingGame.CatchingActivity;
import com.example.foodgame.MazeGame.MazeActivity;
import com.example.foodgame.MonsterGame.MonsterActivity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Writes and reads statistics from file, and observe statistics from the statistics managers
 */
public class StatisticsWriter implements Observer {
    /**
     *
     */
    private ArrayList<int[]> usersStatistics;

    /**
     * Games played
     */
    private int gamesPlayed = 0;

    /**
     * Total number of games
     */
    private final int totalGames;

    /**
     * The names of the statistics
     */
    private final String[] statisticsNames;

    /**
     * The context of the current activity
     */
    private Context context;

    /**
     * A list of usernames of the users currently playing
     */
    private ArrayList<String> usernames;

    /**
     * The username of the user currently playing a game
     */
    private int currentUser;

    /**
     * The number of users currently playing
     */
    private int numberOfUsers;

    /**
     * The order of the games the users(s) will play
     */
    public ArrayList<Class> gameClasses;

    /**
     * Whether the games have been randomized, or read from a saved file
     */
    public boolean loading;

    /**
     * StatisticsWriter constructor
     */
    public StatisticsWriter(int totalGames, String[] statisticsNames, ArrayList<Class> gameClasses,
                            int numberOfUsers) {
        this.totalGames = totalGames;
        this.statisticsNames = statisticsNames;
        this.gameClasses = gameClasses;
        this.usernames = new ArrayList<>();
        this.currentUser = 0;
        this.usersStatistics = new ArrayList<>();
        for (int count = 0; count < numberOfUsers; count++) {

            this.usersStatistics.add(new int[totalGames]);
        }
        this.numberOfUsers = numberOfUsers;
    }


    /**
     * Changes statistics on notify, according to the list of game statistics object
     */
    @Override
    public void update(Observable obs, Object gameStatisticsObject) {
        int[] gameStatistics = ((int[]) gameStatisticsObject);

        // Increment the round statistics by the received game statistics
        for (int i = 0; i < usersStatistics.get(currentUser).length; i++) {
            usersStatistics.get(currentUser)[i] += gameStatistics[i];
        }

        // Write these statistics to a temporary file
        writeTemporaryStatistics(getCurrentUsername());

        // Increment the current user and games played
        currentUser++;
        if (currentUser >= usernames.size()) {
            currentUser -= usernames.size();
        }
        gamesPlayed++;
    }

    /**
     * Write statistic to a temporary, unfinished round of games file
     */
    private void writeTemporaryStatistics(String username) {
        // Get current temporary statistics values from shared preferences
        SharedPreferences currentGame = context.getSharedPreferences("CURRENTGAME",
                Context.MODE_PRIVATE);

        // Initialize round statistics
        int nextLives = usersStatistics.get(currentUser)[0];
        int nextScore = usersStatistics.get(currentUser)[1];
        int nextTime = usersStatistics.get(currentUser)[2];

        // Write new temporary statistics to shared preferences
        SharedPreferences.Editor editor = currentGame.edit();
        String lives = Integer.toString(nextLives);
        String score = Integer.toString(nextScore);
        String time = Integer.toString(nextTime);
        String value = lives + "," + score + "," + time;
        editor.putString(username, value);
        editor.apply();

        // Get the game state for the given user
        SharedPreferences quit = context.getSharedPreferences("USERSAVEDSTATE",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor quitEditor = quit.edit();
        int lastGamePlayed;
        ArrayList<String> allClasses = new ArrayList<>();

        // If new games, create a copy of gameClasses with strings in place of classes
        if (!loading) {
            lastGamePlayed = gamesPlayed;
            for (Class classes : gameClasses) {
                if (classes == CatchingActivity.class) {
                    allClasses.add("CatchingActivity");
                } else if (classes == MazeActivity.class) {
                    allClasses.add("MazeActivity");
                } else if (classes == MonsterActivity.class) {
                    allClasses.add("MonsterActivity");
                }
            }
        }
        // If using loaded games, create a copy of savedClasses with strings in place of classes
        else {
            lastGamePlayed = getLastGame(getCurrentUsername());
            ArrayList<Class> savedClasses = getGames(getCurrentUsername());
            for (Class classes : savedClasses) {
                if (classes == CatchingActivity.class) {
                    allClasses.add("CatchingActivity");
                } else if (classes == MazeActivity.class) {
                    allClasses.add("MazeActivity");
                } else if (classes == MonsterActivity.class) {
                    allClasses.add("MonsterActivity");
                }
            }
        }

        // Write the new classes as strings to the game state of the given user
        String firstClass = allClasses.get(0);
        String secondClass = allClasses.get(1);
        String thirdClass = allClasses.get(2);
        String lastGame = Integer.toString(lastGamePlayed + 1);
        String combined = lastGame + "," + firstClass + "," + secondClass + "," + thirdClass;
        quitEditor.putString(getCurrentUsername(), combined);
        quitEditor.apply();

    }

    /**
     * Loads the temporarily statistics of the username given and sets the roundStatistics to these
     * values
     * @param username The username of which to get the temporary statistics
     */
    public void loadStatistics(String username) {
        // Get the current temporary statistics values from shared preferences
        SharedPreferences currentGame = context.getSharedPreferences("CURRENTGAME",
                Context.MODE_PRIVATE);
        String currValue = currentGame.getString(username, "0,-1,0");
        String[] current = currValue.split(",");

        // Set the roundStatistics to these values
        usersStatistics.get(usernames.indexOf(username))[0] = Integer.parseInt(current[0]);
        usersStatistics.get(usernames.indexOf(username))[1] = Integer.parseInt(current[1]);
        usersStatistics.get(usernames.indexOf(username))[2] = Integer.parseInt(current[2]);
    }

    /**
     * Writes the statistics of all currently playing users to the scoreboard
     */
    public void writeScoreboardStatistics() {
        for (int count = 0; count < usernames.size(); count++) {
            writeScoreboardStatistics(usernames.get(count));
        }
    }

    /** Write statistics to scoreboard file for a given user if they achieve their best score
     * @param username The username for which the scoreboard statistics may be written
     */
    private void writeScoreboardStatistics(String username) {
        // Assess shared preferences
        SharedPreferences overallGame = context.getSharedPreferences("OVERALLBEST",
                Context.MODE_PRIVATE);
        SharedPreferences currentGame = context.getSharedPreferences("CURRENTGAME",
                Context.MODE_PRIVATE);

        // Get the statistics of the current game
        String currValue = currentGame.getString(username, "0,-1,0");
        String[] current = currValue.split(",");
        int currLives = Integer.parseInt(current[0]);
        int currScore = Integer.parseInt(current[1]);
        int currTime = Integer.parseInt(current[2]);

        // Get their leaderboard statistics
        String overallValue = overallGame.getString(username, "0,-1,");
        String[] overall = overallValue.split(",");
        int overallScore = Integer.parseInt(overall[1]);

        // Only write the recent scoreboard statistics if their new score is larger than their
        // earlier score
        if (currScore > overallScore) {
            SharedPreferences.Editor editor = overallGame.edit();
            String lives = Integer.toString(currLives);
            String score = Integer.toString(currScore);
            String time = Integer.toString(currTime);
            String value = lives + "," + score + "," + time;
            editor.putString(username, value);
            editor.apply();
        }
    }

    /**
     * Getter for the last game a user ended on
     * @param username The username of the user we want the last game of
     * @return The last game a user ended on
     */
    public int getLastGame(String username) {
        return (int) getUserSavedState(username).get(0);
    }

    /**
     * Getter for the last game order a user ended on
     * @param username The username of the user we want the game order of
     * @return The last game order a user ended on
     */
    public ArrayList<Class> getGames(String username) {
        return (ArrayList<Class>) getUserSavedState(username).get(1);
    }

    /**
     * Get the last game and game order that a user ended on
     * @param username The username of the user we want the last game and game order that the user
     *                 ended on
     * @return The last game and game order that a user ended on
     */
    private ArrayList<Object> getUserSavedState(String username){
        // Get the last game state of the given user from shared preferences
        SharedPreferences preferences = context.getSharedPreferences("USERSAVEDSTATE",
                Context.MODE_PRIVATE);
        String value = preferences.getString(username, "No user");

        // If the user has never played before
        if (!value.equals("No user")){
            String[] values = value.split(",", -1);
            int gameOn = Integer.parseInt(values[0]);
            // Define the order of games being played by converting from string to activity class
            ArrayList<Class> activityOrder = new ArrayList<>();
            for (int i = 1; i < values.length; i++) {
                switch (values[i]) {
                    case "CatchingActivity":
                        activityOrder.add(CatchingActivity.class);
                        break;
                    case "MazeActivity":
                        activityOrder.add(MazeActivity.class);
                        break;
                    case "MonsterActivity":
                        activityOrder.add(MonsterActivity.class);
                        break;
                }
            }

            // Compile the list of last game and game order that a user ended on and return
            ArrayList<Object> returnList = new ArrayList<>();
            returnList.add(gameOn);
            returnList.add(activityOrder);
            return returnList;
        }
        // If the user has played before
        else {
            // Compile the list of last game and game order that a user ended on and return
            ArrayList<Object> convertToObjects = new ArrayList<>();
            convertToObjects.add(-1);
            convertToObjects.add(gameClasses);
            return convertToObjects;
        }
    }

    /**
     * Add the username to the list of users currently playing
     * @param username The username of the user being added to the list
     */
    public void addUsername(String username) { usernames.add(username); }

    /**
     * Getter for the username of the user currently playing a game
     * @return The username of the user currently playing a game
     */
    String getCurrentUsername() { return usernames.get(currentUser); }

    /**
     * Getter for the usernames of the users playing
     * @return The usernames of the users playing
     */
    public ArrayList<String> getUsernames(){return this.usernames;}

    /**
     * Getter for the current activity context
     * @return The current activity context
     */
    public Context getContext() { return this.context; }

    /**
     * Setter for the current activity context
     * @param context The current activity context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Getter for the number of users
     * @return The number of users
     */
    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    /**
     * Getter for the number of total games
     * @return The number of total games
     */
    public int getTotalGames() {
        return totalGames;
    }

    /**
     * Getter for the names of the game statistics
     * @return The names of the game statistics
     */
    String[] getStatisticsNames() {
        return statisticsNames;
    }
}