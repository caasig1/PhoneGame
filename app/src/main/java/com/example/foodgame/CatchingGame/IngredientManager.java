package com.example.foodgame.CatchingGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

/**
 * Class for an ingredient manager to handle ingredient objects
 */
class IngredientManager extends Observable{

     /**
      * An array-list of ingredient objects in this instance of the game
      */
     private ArrayList<Ingredient> ingredients = new ArrayList<>();

     /**
      * Store number of lanes in this instance of the game in which the ingredients can fall
      */
     private final int numberOfLanes;

     /**
      * Lanes in which ingredients may fall (A series of x positions)
      */
     private int[] lanes;

     /**
      * Speed of the ingredients.
      */
     private final int speed;

     /**
      * Ingredient width in this instance of the game
      */
     private final int ingredientWidth;

     /**
      * Ingredient height in this instance of the game
      */
     private final int ingredientHeight;

     /**
      * Store names of ingredients
      */
     private String[] names;

     /**
      * Store object of ingredient factory class
      */
     private IngredientFactory ingredientFactory;

     /**
      * Store object of ingredient card class
      */
     private IngredientCard ingredientCard;

     /**
      * The current frame of the game
      */
     private int frame;

     /**
      * The frame during which the last ingredient was spawned
      */
     private int lastSpawnFrame;

     /**
      * Constructs IngredientManager object
      * @param gameWidth width of screen in this instance of the game
      * @param numberLanes The number of lanes in the game for the ingredients to fall in
      */
     IngredientManager(int gameWidth, int numberLanes) {
          speed = 25;
          names = new String[]{"tomato", "cheese", "bacon", "lettuce", "pickle"};
          ingredientFactory = new IngredientFactory();

          //Lanes in which food falls so player does not always get trapped by unwanted food
          numberOfLanes = numberLanes;
          lanes = new int[numberOfLanes];
          for (int i = 0; i < lanes.length; i++) {
               lanes[i] = (gameWidth / numberOfLanes) * i;
          }

          ingredientWidth = gameWidth / numberOfLanes;
          ingredientHeight = ingredientWidth / 3;

          //Set the first desired ingredient randomly
          Random rand = new Random();
          ingredientCard = new IngredientCard(gameWidth, names[rand.nextInt(names.length)]);

          //Used for the timing of spawned ingredients
          frame = 0;
          lastSpawnFrame = 0;
     }

     /**
      * Update method to update attributes of game objects
      * @param canvas The canvas on which everything is drawn
      * @param context The context from which the Bitmaps get drawn
      * @param playerLocation The location of the player
      * @param playerDimensions The dimensions of the player
      */
     void update(Canvas canvas, Context context, int[] playerLocation, int[] playerDimensions) {
          createIngredient();
          moveIngredients();
          removableIngredients(playerLocation[1], playerDimensions[1]);
          checkCollision(playerLocation,playerDimensions);
          draw(canvas, context);
          frame++;
     }

     /**
      * Create a new ingredient that is spawned in a random lane at the top of the screen
      */
     private void createIngredient() {
          if (frame - lastSpawnFrame > Math.max(15, 20 - frame/15)) {
               lastSpawnFrame = frame;

               Ingredient ing;
               int rand = (int) (Math.random() * 5);
               Random randomGenerator = new Random();
               int laneNum = randomGenerator.nextInt(numberOfLanes);
               ing = ingredientFactory.makeIngredient(lanes[laneNum], speed, names[rand]);
               ingredients.add(ing);
          }
     }

     /**
      * Move each Ingredient
      */
     private void moveIngredients(){
          for (Ingredient ingredient: ingredients){
               ingredient.update();
          }
     }

     /**
      * Remove ingredients that have fallen past the bottom of the screen
      * @param yPlayer The y coordinate of the player
      * @param heightPlayer The height o
      */
     private void removableIngredients(int yPlayer, int heightPlayer){
          int counter = 0;
          while (counter < ingredients.size()) {
               Ingredient ingredient = ingredients.get(counter);
               int yCoord = ingredient.getYPosition();

               // If it falls past the player
               if (yCoord > yPlayer + heightPlayer) {
                    // If it is a desired ingredient
                    if (ingredient.getName().equals(ingredientCard.getWanted())) {
                         setChanged();
                         notifyObservers("lives");
                    }
                    ingredients.remove(ingredient);
               } else {
                    // Next ingredient
                    counter ++;
               }
          }
     }

     /**
      * Check if any ingredients collide with the player
      * @param playerLocation The location of the player
      * @param playerDimensions The dimensions of the player
      */
     private void checkCollision(int[] playerLocation, int[] playerDimensions){
          int playerX = playerLocation[0];
          int playerY = playerLocation[1];
          int playerWidth = playerDimensions[0];
          int playerHeight = playerDimensions[1];
          int counter = 0;
          while (counter < ingredients.size()) {
               Ingredient ingredient = ingredients.get(counter);
               // If x coordinate is the same
               if (ingredient.getXPosition() <= playerX && ingredient.getXPosition() >= playerX - playerWidth) {
                    // If y coordinate is also the same
                    if (ingredient.getYPosition() >= playerY - playerHeight &&
                            ingredient.getYPosition() <= playerY + playerHeight) {
                         // If it is the desired ingredient
                         if (ingredientCard.getWanted().equals(ingredient.getName())) {
                              setChanged();
                              notifyObservers("score");
                              Random rand = new Random();
                              ingredientCard.setRandomWanted(names[rand.nextInt(names.length)]);
                         }
                         else {
                              setChanged();
                              notifyObservers("game over");
                         }
                         ingredients.remove(ingredient);
                         counter --;
                    }
                    counter ++;
               } else {
                    counter ++;
               }
          }
     }

     /**
      * Draw method to draw game objects in this class
      * @param canvas The canvas on which everything is drawn
      * @param context The context from which the Bitmaps are being drawn
      */
     private void draw(Canvas canvas, Context context) {

          ingredientCard.draw(canvas, context);

          for (Ingredient ingredient: ingredients) {
               Bitmap display = ingredientFactory.getBitmap(ingredient.getName(), context);
               RectF dst = new RectF(ingredient.getXPosition(), ingredient.getYPosition(),
                       ingredient.getXPosition() + ingredientWidth,
                       ingredient.getYPosition() + ingredientHeight);
               canvas.drawBitmap(display, null, dst, null);
          }
     }
}
