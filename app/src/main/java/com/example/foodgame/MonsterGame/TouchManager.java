package com.example.foodgame.MonsterGame;

import com.example.foodgame.GameAdmin.TouchHandler;

/**
 * The Touch Manager class
 */
class TouchManager {
    /**
     * The user has touched the screen at least once
     */
    private boolean hasTouched;

    /**
     * Constructor for TouchManager
     */
    TouchManager() {
        hasTouched = false;
    }

    /**
     * Getter for the touch state used in the facade for this game.
     * @return true if the player has touched the screen
     */
    boolean getTouchState(){
        return hasTouched;
    }

    /**
     * Tells if the player is touching and holding the screen
     * @param touchHandler The touch handling for the finger tracking
     * @return return if the finger is holding down continuously
     */
    boolean playerTouching(TouchHandler touchHandler) {
        if (hasTouched) {
            return touchHandler.getHolding();
        }
        else {
            hasTouched = touchHandler.getHolding();
        }
        return true;
    }

}
