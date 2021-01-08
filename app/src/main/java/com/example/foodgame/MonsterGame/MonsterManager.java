package com.example.foodgame.MonsterGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.foodgame.R;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

class MonsterManager extends Observable {
    /**
     * Height of the screen
     */
    private int gameHeight;

    /**
     * Width of the screen
     */
    private int gameWidth;

    /**
     * The size of the monster
     */
    private int monsterSize;

    /**
     * The last frame on which a monster was spawned
     */
    private long lastSpawnFrame;

    /**
     * The frame on which the program is on
     */
    private int frame;

    /**
     * The speed at which monsters move
     */
    private int speed = 10;

    /**
     * The factory in which monsters are created
     */
    private MonsterFactory monsterFactory;

    /**
     * A list of on-screen monsters
     */
    private ArrayList<Monster> monsters;

    /**
     * MonsterManager contructor
     * @param gameHeight The height of the screen
     * @param gameWidth The width of the screen
     * @param monsterSize The size of the monster
     */
    MonsterManager(int gameHeight, int gameWidth, int monsterSize) {
        this.gameHeight = gameHeight;
        this.gameWidth = gameWidth;
        this.monsterSize = monsterSize;

        lastSpawnFrame = 0;
        frame = 0;

        monsters = new ArrayList<>();
        monsterFactory = new MonsterFactory();
    }

    /**
     * Update and draw monsters, perform collision checking
     * @param playerX The x coordinate of the player
     * @param playerY The y coordinate of the player
     * @param canvas The canvas on which monsters are drawn
     * @param context The context from which the Bitmaps will get their resources
     */
    void update(int playerX, int playerY, Canvas canvas, Context context) {

        // Update monsters
        createMonster();
        deleteOffscreenMonsters();
        checkCollision(playerX, playerY, canvas, context);
        moveMonsters(playerX);
        drawMonsters(canvas, context);
        updateSpeed();
        frame++;
    }

    /**
     * Updates speed if enough time has passed
     */
    private void updateSpeed() {
        speed = Math.min(10 + frame/125, 15);
    }

    /**
     * Delete any off-screen monsters
     */
    private void deleteOffscreenMonsters() {
        int mon = 0;
        while (mon < monsters.size()) {
            Monster monster = monsters.get(mon);

            // Delete monsters if they are past the bottom of the screen
            if (monster.isPassed()) {
                monsters.remove(mon);

                // Update score in StatisticsManager through observer
                setChanged();
                notifyObservers("score");
                continue;
            }
            mon++;
        }
    }

    /**
     * Update the monsters and delete them if they are off-screen
     * @param playerX The x coordinate of the player
     */
    private void moveMonsters(int playerX) {
        int counter = 0;
        while (counter < monsters.size()) {
            Monster monster = monsters.get(counter);

            // Update monsters
            if (monster instanceof HorizontalFollowMonster) {
                ((HorizontalFollowMonster) monster).update(playerX);
            }
            else if (monster instanceof CircularMoveMonster) {
                ((CircularMoveMonster) monster).update(frame);
            }
            else if (monster instanceof WallBounceMonster) {
                ((WallBounceMonster) monster).update();
            }
            else if (monster instanceof TwoBounceMonster) {

                // Check to see if there is a corresponding two bouncing monster to this one
                boolean hasPartner = false;
                for (int m = 0; m < monsters.size(); m++) {
                    if (monsters.get(m) instanceof TwoBounceMonster && m != counter) {
                        if (Math.abs(monsters.get(m).getYPosition() - monster.getYPosition()) < monsterSize) {
                            hasPartner = true;
                            break;
                        }
                    }
                }

                // If there is a partner, update this monster as regular
                if (hasPartner) {
                    ((TwoBounceMonster) monster).update();
                }
                // If not, create a WallBounceMonster in place of the TwoBounceMonster
                else {
                    createWallBounceInPlaceOfTwoBouncing(monster, counter);
                }
            }
            counter++;
        }
    }

    /**
     * Detect player-monster collisions
     * @param playerX The x coordinate of the player
     * @param playerY The y coordinate of the player
     * @param canvas The canvas on which the monster is drawn
     * @param context The context from which BitMap will get its resources.
     */
    private void checkCollision(int playerX, int playerY, Canvas canvas, Context context) {
        int counter = 0;
        while (counter < monsters.size()) {
            Monster monster = monsters.get(counter);

            int monX = monster.getXPosition();
            int monY = monster.getYPosition();

            // Check for player-monster collision
            if (monX <= playerX && monX >= playerX - monsterSize && monY <= playerY &&
                    monY >= playerY - monsterSize) {

                // On collision, delete the monster
                monsters.remove(counter);

                // On collision, create a visual flash of the monster being destroyed
                Bitmap flash = BitmapFactory.decodeResource(context.getResources(), R.drawable.redflash);
                canvas.drawBitmap(flash, 0, 0, null);

                // On collision, update lives in StatisticsManager through observer
                setChanged();
                notifyObservers("lives");
            }
            else {
                counter++;
            }
        }
    }

    /**
     * Draws all monsters
     * @param canvas The canvas on which the monster is drawn
     * @param context The context from which BitMap will get its resources.
     */
    private void drawMonsters(Canvas canvas, Context context) {
        for (Monster monster: monsters) {
            monster.draw(canvas, context);
        }
    }

    /**
     * Create a new monster
     */
    private void createMonster() {
        // If there has been long enough of a delay, create another monster
        if (frame - lastSpawnFrame > Math.max(50, 125 - frame/15)) {
            lastSpawnFrame = frame;

            // Create a random monster type
            Random randomGenerator = new Random();
            int monsterChoice = randomGenerator.nextInt(4);
            int startX = randomGenerator.nextInt(gameWidth);
            ArrayList<Monster> mon = monsterFactory.getMonster(monsterChoice, startX, speed, gameHeight, gameWidth, monsterSize);
            monsters.addAll(mon);
        }
    }

    /**
     * Create a WallBounce monster in place of a TwoBouncing monster
     * @param monster The two bouncing monster
     * @param mon The index of the two bouncing monster
     */
    private void createWallBounceInPlaceOfTwoBouncing(Monster monster, int mon) {
        int startX = monster.getXPosition();
        ArrayList<Monster> newMonster = monsterFactory.getMonster(0, startX, speed,
                gameHeight, gameWidth, monsterSize);
        newMonster.get(0).setYPosition(monster.getYPosition());
        boolean goingRight = ((TwoBounceMonster) monster).getGoingRight();
        ((WallBounceMonster) newMonster.get(0)).setGoingRight(goingRight);
        monsters.remove(mon);
        monsters.addAll(newMonster);
    }
}
