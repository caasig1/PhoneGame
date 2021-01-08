package com.example.foodgame.CatchingGame;

/**
 * Class for an ingredient object
 */
class Ingredient {

    /**
     * x coordinate of ingredient
     */
    private final int x;
    /**
     * y coordinate of ingredient
     */
    private int y;

    /**
     * Speed of ingredient
     */
    private final int speed;

    /**
     * Store name of ingredient
     */
    private String name;

    /** Constructs an ingredient
     * @param x coordinate of ingredient
     * @param speed speed of ingredient
     * @param name name of ingredient
     */
    Ingredient(int x, int speed, String name) {
        this.x = x;
        this.y = 0;
        this.speed = speed;
        this.name = name;
    }

    /**
     * Getter for ingredient's y position
     * @return the Y position of the ingredient
     */
    int getYPosition() {
        return y;
    }

    /**
     * Getter for ingredient's x position
     * @return the X position of the ingredient
     */
    int getXPosition() {
        return x;
    }

    /**
     * Setter for ingredient's name
     * @return The name type of this ingredient
     */
    String getName() {return name;}

    /**
     * Update the location of the ingredient
     */
    void update(){
        this.y += speed;
    }

}
