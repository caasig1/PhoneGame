package com.example.foodgame.MonsterGame;

/**
 * Class for the type of monster than moves horizontally to follow the player's x coordinate
 */
class HorizontalFollowMonster extends Monster {

    /**
     * The x velocity of the monster
     */
    private float xVelocity;

    /**
     * The HorizontalFollowMonster constructor
     * @param initialX The starting position of the monster horizontally
     * @param speed The speed of the monster
     * @param gameHeight The height of the screen
     * @param gameWidth The width of the screen
     * @param size The size of the monster
     */
    HorizontalFollowMonster(int initialX, int speed, int gameHeight, int gameWidth, int size) {
        super(Math.min(initialX, gameWidth - size), speed, gameHeight, gameWidth, size);
    }

    /**
     * Updates this monster's position according to their following movement style
     * @param playerX The x coordinate of the player
     */
    void update(int playerX) {
        // If player is on the right on the monster
        if (playerX - size/2 >= x)  {
            if (xVelocity <= 0) {
                // Put on the brakes
                xVelocity = Math.round(xVelocity/1.3) + 1;
            }
            else {
                // Accelerate towards the player
                xVelocity += Math.sqrt(speed*2);
            }
        }
        // If player is on the left of the monster
        else {
            if (xVelocity <= 0){
                // Accelerate towards the player
                xVelocity -= Math.sqrt(speed*2);
            }
            else {
                // Put on the brakes
                xVelocity = Math.round(xVelocity/1.3) - 1;
            }
        }

        // If hitting a wall, shift some of the monster's velocity into a bounce
        if (x + xVelocity < 0 || x + xVelocity > gameWidth - size)
            xVelocity *= -0.5;

        x += xVelocity;

        setYPosition(y + speed);
    }
}