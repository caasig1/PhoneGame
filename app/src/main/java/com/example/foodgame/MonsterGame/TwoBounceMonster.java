package com.example.foodgame.MonsterGame;

/**
 * Class for the type of monster that spawns two and bounces down the screen
 */
class TwoBounceMonster extends Monster {

    /**
     * Whether the monster is going right or not
     */
    private boolean goingRight;

    /**
     *  Whether or not this monster is on the right side of the screen
     */
    private boolean onRight;

    /**
     * TwoBounceMonster Constructor
     * @param initialX The starting position of the monster horizontally
     * @param speed The speed of the monster
     * @param gameHeight The height of the screen
     * @param gameWidth The width of the screen
     * @param size The size of the monster
     */
    TwoBounceMonster(int initialX, int speed, int gameHeight, int gameWidth, int size) {
        super(initialX, speed, gameHeight, gameWidth, size);
        if (initialX == 0) {
            goingRight = true;
            onRight = false;
        }
        else {
            goingRight = false;
            onRight = true;
        }
    }

    /**
     * Updates this monster's position according to their bouncing movement style
     */
    void update() {
        if (onRight) {
            // Turn if reached a wall
            if (x + size >= gameWidth && goingRight) {
                goingRight = false;
            }
            else if (x <= gameWidth/2 && !goingRight) {
                goingRight = true;
            }
        }
        else {
            // Turn if reached a wall
            if (x + size >= gameWidth/2 && goingRight) {
                goingRight = false;
            }
            else if (x <= 0 && !goingRight) {
                goingRight = true;
            }
        }

        if (onRight) {
            // Move left or right
            if (goingRight) {
                x = Math.min(gameWidth + 1, x + 3*speed);
            } else {
                x = Math.max(gameWidth/2 - 1 , x - 3*speed);
            }
        }
        else {
            // Move left or right
            if (goingRight) {
                x = Math.min(gameWidth/2 + 1, x + 3*speed);
            } else {
                x = Math.max(-1, x - 3*speed);
            }
        }
        setYPosition(y + speed);
    }

    /**
     * Gets whether the monster is going right to true or false
     * @return the boolean whether monster is going right
     */
    boolean getGoingRight() {
        return goingRight;
    }
}