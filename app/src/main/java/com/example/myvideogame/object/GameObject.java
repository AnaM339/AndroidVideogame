package com.example.myvideogame.object;

import android.graphics.Canvas;

/**
 * La clase base abstracta para todos los objetos del juego.
 * Contiene propiedades comunes como posición y velocidad, y métodos abstractos para dibujar y actualizar.
 */
public abstract class GameObject {
    protected double positionX;
    protected double positionY;
    protected double velocityX = 0.0;
    protected double velocityY = 0.0;
    protected double directionX = 1.0;
    protected double directionY = 0.0;

    public GameObject() { }

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public double getPositionX() { return positionX; }
    public double getPositionY() { return positionY; }

    public abstract void draw(Canvas canvas);
    public abstract void update();

    public static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        //se utiliza la teorema de Pitagoras
        return Math.sqrt(
                Math.pow(obj2.getPositionX() - obj1.getPositionX(), 2) +
                        Math.pow(obj2.getPositionY() - obj1.getPositionY(), 2)
        );
    }

    protected double getDirectionX() {
        return directionX;
    }

    protected double getDirectionY() {
        return directionY;
    }
}
