package com.example.myvideogame;
import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

public class Player {
    private static final double SPEED_PIXELS_PER_SECOND = 500.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS; //la division hace la conversion a pixeles per update
    private double positionX;
    private double positionY;
    private double radius;
    private Bitmap spaceship;

    private static final float IMAGE_SCALE_FACTOR = 0.07f;
    private double velocityX;
    private double velocityY;

    public Player(Context context, double positionX, double positionY, double radius) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.radius = radius;

        //cargar la imagen
        spaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship1);

        //verifica si se ha cargado
        if (spaceship != null) {
            //ajusta la escala de la imagen
            int newWidth = (int) (spaceship.getWidth() * IMAGE_SCALE_FACTOR);
            int newHeight = (int) (spaceship.getHeight() * IMAGE_SCALE_FACTOR);
            spaceship = Bitmap.createScaledBitmap(spaceship, newWidth, newHeight, true);
        } else {
            Log.e(TAG, "no se ha podido cargar la imagen");
        }
    }

    public void draw(Canvas canvas) {
        if (spaceship != null) {
            canvas.drawBitmap(spaceship, (float) (positionX - radius), (float) (positionY - radius), null);
        } else {
            Log.e(TAG, "imagen nula");
        }
    }

    //enlaza el joystick con el jugador en funcion del actuator
    public void update(Joystick joystick) {
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;
        positionX += velocityX;
        positionY += velocityY;
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
