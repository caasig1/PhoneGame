package com.example.foodgame.CatchingGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.foodgame.R;

/**
 * Class for an ingredient card object
 * This object stores which ingredient is to be caught next
 */
class IngredientCard {
    /**
     * y coordinate of ingredient card
     */
    private final int y;

    /**
     * Width of ingredient card
     */
    private final int width;

    /**
     * Height of ingredient card
     */
    private final int height;

    /**
     * The desired ingredient to be caught by the player
     */
    private String wantedIngredient;

    /**
     * Ingredient factory that gives the Bitmap for the wanted ingredient
     */
    private IngredientFactory ingredientFactory;

    /**
     * Widthh of the screen
     */
    private int screenWidth;

    /**
     * Constructs ingredient card object
     * @param gridWidth width of screen
     * @param ingredientType The starting ingredient that is wanted
     * */
    IngredientCard(int gridWidth, String ingredientType) {
        //Dimensions and location
        y = gridWidth/7;
        width = gridWidth/4;
        height = width/2;
        screenWidth = gridWidth;

        //Setting wanted ingredient
        setRandomWanted(ingredientType);
        ingredientFactory = new IngredientFactory();
    }
    
    /**
     * Draw method to draw objects on screen
     * @param canvas The canvas on which everything is drawn
     * @param context The context from which Bitmaps get their resources
     */
    void draw(Canvas canvas, Context context) {
        Bitmap display = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu_card);
        RectF dst = new RectF((screenWidth - width)/2, y + 80, (screenWidth + width)/2, y + 80 +height);
        canvas.drawBitmap(display, null, dst, null);
        Bitmap ingredient = ingredientFactory.getBitmap(wantedIngredient,context);
        RectF dst1 = new RectF((screenWidth - width)/2 + 20, y+160, (screenWidth + width)/2 - 20,y + 80 +height);
        canvas.drawBitmap(ingredient, null, dst1, null);
    }
    
    /**
     * Setter method to change wanted ingredient
     * @param ingredient The ingredient type that is wanted next by the player
     */
    void setRandomWanted(String ingredient) {
        wantedIngredient = ingredient;
    }
    
    /**
     * Getter for wanted ingredient
     * @return String of what ingredient is wanted
     */
    String getWanted() {
        return wantedIngredient;
    }
}
