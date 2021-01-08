package com.example.foodgame.MonsterGame;

import java.util.ArrayList;

/**
 * Factory creating many monsters
 */
class MonsterFactory {

    /**
     * Create and return a monster according to the integer type given
     * @param monsterType The type of the monster represented by an integer
     * @param startX The starting position along the x axis (horizontally along the top)
     * @param speed The speed of the monster
     * @param gridHeight The Height of the screen
     * @param gridWidth The  width of the screen
     * @param size The size of the monster
     * @return Return the newly created monster
     */
    ArrayList<Monster> getMonster(int monsterType, int startX, int speed, int gridHeight,
                                  int gridWidth, int size){
        ArrayList<Monster> returnMonsters = new ArrayList<>();
        if(monsterType == 0) {
            returnMonsters.add(new WallBounceMonster(startX, speed, gridHeight, gridWidth, size));
        }
        else if (monsterType == 1) {
            returnMonsters.add(new CircularMoveMonster(speed, gridHeight, gridWidth, size));
        }
        else if (monsterType == 2) {
            returnMonsters.add(new HorizontalFollowMonster(startX, speed, gridHeight, gridWidth,
                    size));
        }
        else if (monsterType == 3) {
            returnMonsters.add(new TwoBounceMonster(0, speed, gridHeight, gridWidth, size));
            returnMonsters.add(new TwoBounceMonster(gridWidth-size, speed, gridHeight,
                    gridWidth, size));
        }
        else {
            return null;
        }
        return returnMonsters;
    }
}