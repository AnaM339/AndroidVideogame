package com.example.myvideogame.object;

import android.graphics.Canvas;

/**
 * La clase base abstracta para todos los objetos del juego.
 * Contiene propiedades comunes como posición y velocidad, y métodos abstractos para dibujar y actualizar.
 */
public abstract class GameObject {
    protected double positionX;
    protected double positionY;
    protected double velocityX;
    protected double velocityY;

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    protected static double getDistanceBetweenObjets(GameObject obj1, GameObject obj2) {
        //se utiliza el teorema de Pitagoras
        return Math.sqrt(
                Math.pow(obj2.getPositionX() - obj1.getPositionX(), 2) +
                Math.pow(obj2.getPositionY() - obj1.getPositionY(), 2)
        );
    }

    public abstract void draw(Canvas canvas);

    public abstract void update();

    protected double getPositionX() {
        return positionX;
    }

    protected double getPositionY() {
        return positionY;
    }
}
