package com.example.myvideogame.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * La clase base abstracta para todas las naves espaciales en el juego.
 * Hereda de GameObject y proporciona la funcionalidad común para cargar y dibujar naves espaciales.
 */
public abstract class Circle extends GameObject {
    protected double radius;
    protected Paint paint;
    public Circle(Context context, int color, double positionX, double positionY, double radius) {
        super(positionX, positionY);
        this.radius = radius;
        // Set colors of circle
        paint = new Paint();
        paint.setColor(color);
    }

    /**
     * Metodo verifica si se chocan los objetos
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean isColliding(Circle obj1, Circle obj2) {
        double distance = getDistanceBetweenObjects(obj1, obj2);
        double distanceToCollision = obj1.getRadius() + obj2.getRadius();
        if (distance < distanceToCollision)
            return true;
        else
            return false;
    }
    public void draw(Canvas canvas) {
        canvas.drawCircle((float) positionX, (float) positionY, (float) radius, paint);
    }

    public double getRadius() {
        return radius;
    }
}

