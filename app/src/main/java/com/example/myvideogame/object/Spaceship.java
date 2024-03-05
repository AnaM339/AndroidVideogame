package com.example.myvideogame.object;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

/**
 * La clase base abstracta para todas las naves espaciales en el juego.
 * Hereda de GameObject y proporciona la funcionalidad común para cargar y dibujar naves espaciales.
 */
public abstract class Spaceship extends GameObject {

    protected Bitmap image;
    protected double radius;
    private static final float IMAGE_SCALE_FACTOR = 0.07f;

    public Spaceship(Context context, double positionX, double positionY) {
        super(positionX, positionY);
        loadImage(context);
    }

    protected abstract int getDrawableId(); //metodo abstracto para obtener el ID

    private void loadImage(Context context) {
        image = BitmapFactory.decodeResource(context.getResources(), getDrawableId());

        if (image != null) {
            int newWidth = (int) (image.getWidth() * IMAGE_SCALE_FACTOR);
            int newHeight = (int) (image.getHeight() * IMAGE_SCALE_FACTOR);
            image = Bitmap.createScaledBitmap(image, newWidth, newHeight, true);
        } else {
            Log.e(TAG, "No se ha podido cargar la imagen");
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (image != null) {
            canvas.drawBitmap(image, (float) (positionX - radius), (float) (positionY - radius), null);
        } else {
            Log.e(TAG, "Imagen nula");
        }
    }

    @Override
    public void update() {
        // Common update logic for all spaceships
    }
}

