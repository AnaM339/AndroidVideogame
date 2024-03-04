package com.example.myvideogame;
import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

public class Player {
    private double positionX;
    private double positionY;
    private double radius;
    private Bitmap spaceship;

    private static final float IMAGE_SCALE_FACTOR = 0.07f;

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

    public void update() {
        // Update logic for the player
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
