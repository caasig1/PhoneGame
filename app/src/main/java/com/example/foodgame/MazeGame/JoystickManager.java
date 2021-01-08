package com.example.foodgame.MazeGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.foodgame.R;
import com.example.foodgame.GameAdmin.TouchHandler;

/**
 * class for the JoystickManager
 */
class JoystickManager {
    /**
     * radius of the joystick
     */
    private int radius;

    /**
     * x coordinate of the joystick
     */
    private int positionX;

    /**
     * y coordinate of the joystick
     */
    private int positionY;

    /**
     * direction of the joystick as UP DOWN LEFT or RIGHT
     */
    private String direction;

    /**
     * The center x coordinate of the joystick pad (or the background joystick)
     */
    private int centerX;

    /**
     * The center y coordinate of the joystick pad (or the background joystick)
     */
    private int centerY;

    /**
     * Constructor of the joystick
     */
    JoystickManager() {
        this.radius = 216;
        this.centerX = 540;
        this.centerY = 1620;
        this.positionX = 0;
        this.positionY = 0;
    }

    /**
     * Updates the joystick on touch when the finger/cursor is moved.
     * @param th the touchhandler instance
     * @param canvas the canvas on which everythnig is drawn
     * @param context the context from which Bitmaps will get their resources
     */
    void update(TouchHandler th, Canvas canvas, Context context) {
        //location of the finger from its center position
        positionX = (th.getPosition()[0] - (centerX));
        positionY = (th.getPosition()[1] - (centerY));

        //distance of finger from its center position
        double distance = Math.sqrt(Math.pow(positionX, 2) + Math.pow(positionY, 2));
        direction = calDirection(calAngle(positionX, positionY));

        //ensure the distance of finger within boundary of joystick restrictions
        if (distance <= (radius)) {
            setPosition(th.getPosition()[0], th.getPosition()[1]);
        } else if (distance > (radius)) {
            float x = (float) (Math.cos(Math.toRadians(calAngle(positionX, positionY)))
                    * radius);
            float y = (float) (Math.sin(Math.toRadians(calAngle(positionX, positionY)))
                    * radius);
            x += centerX;
            y += centerY;
            setPosition((int) x, (int) y);
        }
        draw(canvas, context);
    }

    /**
     * Getter for the direction
     * @return A string that represents the direction of the joystick
     */
    String getDirection() {
        return direction;
    }

    /**
     * Calculates the angle of the joystick in comparison to its starting position.
     * Picture a cartesian plane with (0,0) at the center of the starting position of the joystick
     * @param x is the x coordinate on this plane
     * @param y is the y coordinate on this plane
     * @return the angle of the joystick as explained above from 0 to 360 (0 is RIGHT)
     */
    private double calAngle(float x, float y) {
        if(x >= 0 && y >= 0)
            return Math.toDegrees(Math.atan(y / x));
        else if(x < 0 && y >= 0)
            return Math.toDegrees(Math.atan(y / x)) + 180;
        else if(x < 0 && y < 0)
            return Math.toDegrees(Math.atan(y / x)) + 180;
        else if(x >= 0 && y < 0)
            return Math.toDegrees(Math.atan(y / x)) + 360;
        return 0;
    }

    /**
     * Based on the angle, this finds the direction of the joystick
     * @param angle angle of the joystick
     * @return is the String UP DOWN LEFT RIGHT
     */
    private String calDirection(double angle) {
        if(angle >= 225 && angle < 315 ) {
            return "UP";
        } else if(angle >= 315 || angle < 45 ) {
            return "RIGHT";
        } else if(angle >= 45 && angle < 135 ) {
            return "DOWN";
        } else if(angle >= 135 && angle < 225 ) {
            return "LEFT";
        } else {
            return direction;
        }
    }

    /**
     * Set the position of the joystick. Used for drawing above
     * @param x is the x coordinate of the joystick
     * @param y is the y coordinate of the joystick
     */
    private void setPosition(int x, int y){
        this.positionX = x;
        this.positionY = y;
    }

    /**
     * Draw the joystick onto the canvas
     * @param canvas The canvas on which the joystick is being drawn
     * @param context The context from which BitMap will get its resources.
     */
    private void draw(Canvas canvas, Context context){
        Bitmap display = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.joystick_small_image);

        //dimensions/location of joystick
        int xTopLeft = positionX - 54;
        int yTopLeft = positionY - 54;
        int xBottomRight = positionX + 54;
        int yBottomRight = positionY + 54;

        RectF dst = new RectF(xTopLeft, yTopLeft, xBottomRight, yBottomRight);
        canvas.drawBitmap(display, null, dst, null);
    }
}