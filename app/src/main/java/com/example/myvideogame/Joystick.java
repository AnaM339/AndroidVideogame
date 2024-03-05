package com.example.myvideogame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * La clase Joystick representa un controlador virtual en la pantalla
 * que permite a los usuarios controlar la dirección de movimiento de un objeto en el juego.
 */
public class Joystick {

    private int outerCircleCenterPositionX;
    private int outerCircleCenterPositionY;
    private int innerCircleCenterPositionX;
    private int innerCircleCenterPositionY;
    private int outerCircleRadius;
    private int innerCircleRadius;
    private Paint outerCirclePaint;
    private Paint innerCirclePaint;
    private double joystickCenterToTouchDistance;
    private boolean isPressed;
    private double actuatorX;
    private double actuatorY;

    public Joystick(int centerPositionX, int centerPositionY, int outerCircleRadius, int innerCircleRadius) {

        //posicion de los circulos exterior y interior
        outerCircleCenterPositionX = centerPositionX;
        outerCircleCenterPositionY = centerPositionY;
        innerCircleCenterPositionX = centerPositionX;
        innerCircleCenterPositionY = centerPositionY;

        //radios de los circulos
        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        //paint de los circulos
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.GREEN);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
    public void draw(Canvas canvas) {

        //pintar el circulo exterior
        canvas.drawCircle(
                outerCircleCenterPositionX,
                outerCircleCenterPositionY,
                outerCircleRadius,
                outerCirclePaint
        );

        //pintar el circulo interior
        canvas.drawCircle(
                innerCircleCenterPositionX,
                innerCircleCenterPositionY,
                innerCircleRadius,
                innerCirclePaint
        );
    }

    public void update() {
        updateInnerCirclePosition();
    }

    //este metodo dibuja un vector desde el centro del circulo exterior en la direccion
    //del actuator, y este decide el porcentaje se mueve fuera del circulo en coordenadas x,y
    private void updateInnerCirclePosition() {
        innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX*outerCircleRadius);
        innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY*outerCircleRadius);
    }

    public boolean isPressed(double touchPositionX, double touchPositionY) {

        //se usa la teorema de Pitagoras para resolver esta distancia
        joystickCenterToTouchDistance = Math.sqrt(
                Math.pow(outerCircleCenterPositionX -touchPositionX, 2) +
                Math.pow(outerCircleCenterPositionY - touchPositionY, 2)
        );

        //la distancia del centro del joystick hasta el punto donde el usuario toca la pantalla
        //es decir el usuario toca dentro del circulo exterior
        return joystickCenterToTouchDistance < outerCircleRadius;

    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean getIsPressed() {
        return isPressed;
    }

    //el metodo establece los valores de x y de y
    //el rango de de 0 (no tira nada) a 1 (se tira del joystick al maximo en una dirrecion)
    public void setActuator(double touchPositionX, double touchPositionY) {
        //distancias relativas
        double deltaX = touchPositionX - outerCircleCenterPositionX;
        double deltaY = touchPositionY - outerCircleCenterPositionY;

        //distancias absolutas
        //se calcula con el teorema de Pitagoras
        double deltaDistance = Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));

        if(deltaDistance < outerCircleRadius) {
            // se calcula la distancia en la que se ha tirado del joystick
            // y se divide entre la distancia total desde el centor del joystick y su margen
            actuatorX = deltaX/outerCircleRadius;
            actuatorY = deltaY/outerCircleRadius;
        } else {
            //calcula para cuando la distancia es mayor al margen del circulo exterior
            actuatorX = deltaX/deltaDistance;
            actuatorY = deltaY/deltaDistance;
        }
    }

    public void resetActuator() {
        actuatorX = 0.0;
        actuatorY = 0.0;
    }

    public double getActuatorX() {
        return actuatorX;
    }

    public double getActuatorY() {
        return actuatorY;
    }
}
