package com.example.foodgame.CatchingGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.foodgame.R;

/**
 * Class to set up a factory for ingredient objects
 */
class IngredientFactory{
	
	/**
	 * Constructs an ingredient
	 * @param x coordinate of ingredient
	 * @param speed speed of ingredient
	 * @param ingType type of ingredient
	 */
	 Ingredient makeIngredient(int x, int speed, String ingType) {
		  return new Ingredient(x, speed, ingType);
	 }
	 
	  /**
	   * Method to create and return display bitmap for parameter ingredient
	   * @param ingredientName The ingredient's name
	   * @param context The context from which Bitmaps get their resources
	   */
	 Bitmap getBitmap(String ingredientName, Context context){
	 	 Bitmap display;
		 switch (ingredientName) {
			 case "tomato":
				 display = BitmapFactory.decodeResource(context.getResources(), R.drawable.tomato);
				 break;
			 case "cheese":
				 display = BitmapFactory.decodeResource(context.getResources(), R.drawable.cheese);
				 break;
			 case "bacon":
				 display = BitmapFactory.decodeResource(context.getResources(), R.drawable.bacon);
				 break;
			 case "lettuce":
				 display = BitmapFactory.decodeResource(context.getResources(), R.drawable.lettuce);
				 break;
			 case "pickle":
				 display = BitmapFactory.decodeResource(context.getResources(), R.drawable.pickle);
				 break;
			 default:
				 display = null;
				 break;
		 }
		 return display;
	 }
}
