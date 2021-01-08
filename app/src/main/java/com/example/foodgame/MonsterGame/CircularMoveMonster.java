package com.example.foodgame.MonsterGame;

import java.util.Random;

/**
 * Class for the type of monster that moves in circular patterns
 */
class CircularMoveMonster extends Monster {

    /**
     * The radius of the circular movement
     */
    private int radius;

    /**
     * The x-position of the monster without circular movement
     */
    private final int noCircleX;

    /**
     * The y-position of the monster without circular movement
     */
    private int noCircleY;

    /**
     * CircularMoveMonster constructor
     * @param speed The speed of the monster
     * @param gameHeight The height of the screen
     * @param gameWidth The width of the screen
     * @param size The size of the monster
     */
    CircularMoveMonster(int speed, int gameHeight, int gameWidth, int size) {
        super((new Random()).nextInt((int) (gameWidth/2.3)) + (int) (gameWidth/5.5),
                speed, gameHeight, gameWidth, size);

        radius = gameWidth/5;

        // Set the x and y position of the center of the circle's movement
        noCircleX = x;
        noCircleY = -radius;
    }

    /**
     * Updates the monster's position according to their circular movement style
     * @param frame number of frames since the game has started (instead of a timer)
     */
    void update(int frame) {
        float t = ((float) frame)/20*((float) Math.sqrt(speed));
        noCircleY += speed;
        x = noCircleX + (int) (radius * Math.sin(t));
        y = noCircleY + (int) (radius * Math.cos(t));

    }
}