package com.example.foodgame.MonsterGame;

/**
 * Class that represents the player
 */
class PlayerManager {

    /**
     * X coordinate of player
     */
    private int x;

    /**
     * Y coordinate of player
     */
    private int y;

    /**
     * PlayerManager constructor
     * @param gridHeight The height of the screen
     * @param gridWidth The width of the screen
     */
    PlayerManager(int gridHeight, int gridWidth) {
        x = gridWidth/2;
        y = gridHeight/2;
    }

    /**
     * Getter for player's x position
     * @return returns the x coordinate of player
     */
    int getX() {
        return x;
    }

    /**
     * Getter for player's y position
     * @return returns the y coordinate of player
     */
    int getY() {
        return y;
    }

    /**
     * Update player position and drawn based on finger placement
     * @param xyPos The location where player will be set to according to finger location
     */
    void update(int[] xyPos) {
        this.x = xyPos[0];
        this.y = xyPos[1];
    }
}
