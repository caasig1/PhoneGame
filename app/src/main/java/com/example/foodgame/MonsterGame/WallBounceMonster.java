package com.example.foodgame.MonsterGame;

import java.util.Random;

/**
 * Class for the type of monster that bounces horizontally and moves downwards
 */
class WallBounceMonster extends Monster {

    /**
     * Whether the monster is going right or not
    */
    private boolean goingRight;

    /**
     * WallBounceMonster constructor
     * @param initialX The starting position of the monster horizontally
     * @param speed The speed of the monster
     * @param gameHeight The height of the screen
     * @param gameWidth The width of the screen
     * @param size The size of the monster
     */
    WallBounceMonster(int initialX, int speed, int gameHeight, int gameWidth, int size) {
        super(initialX, speed, gameHeight, gameWidth, size);
        Random random = new Random();
        goingRight = random.nextBoolean();
    }

    /**
     * Updates this monster's position according to their bouncing movement style
     */
    void update() {
        // Turn if reached a wall
        if (x + size >= gameWidth && goingRight) {
            goingRight = false;
        }
        else if (x <= 0 && !goingRight) {
            goingRight = true;
        }

        // Move left or right
        if (goingRight) {
            x = Math.min(gameWidth + 1, x + 3*speed);
        }
        else {
            x = Math.max(-1, x - 3*speed);
        }
        setYPosition(y + speed);
    }

    /**
     * Sets whether the monster is going right to true or false
     * @param goingRight boolean whether monster is going right
     */
    void setGoingRight(boolean goingRight) {
        this.goingRight = goingRight;
    }
}