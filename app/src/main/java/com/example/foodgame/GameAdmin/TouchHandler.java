package com.example.foodgame.GameAdmin;

import android.view.MotionEvent;

/**
 * Touch Handling for all the games
 */
public class TouchHandler {
    /**
     * The x position of the user's finger
     */
    private int x;

    /**
     * The y position of the user's finger
     */
    private int y;

    /**
     * Whether or not the user is holding down their finger
     */
    private boolean holding;

    /**
     * Setter of the position of a user's finger
     * @param arg The motion event that is triggered when there is an action
     */
    public void setPosition(MotionEvent arg) {
        this.x = (int) arg.getX();
        this.y = (int) arg.getY();
    }

    /**
     * Getter of the position of a user's finger
     */
    public int[] getPosition() {
        return new int[]{x,y};
    }

    /**
     * Setter for whether the user is holding down their finger
     */
    void setHolding(boolean holding) {
        this.holding = holding;
    }

    /**
     * Getter for whether the user is holding down their finger
     */
    public boolean getHolding() {
        return holding;
    }
}
