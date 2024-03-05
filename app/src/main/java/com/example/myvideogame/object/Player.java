package com.example.myvideogame.object;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.myvideogame.GameLoop;
import com.example.myvideogame.Joystick;
import com.example.myvideogame.R;

/**
 * La clase Player representa al jugador en el juego y controla su posición y comportamiento.
 * Responde a las entradas del usuario y se dibuja en la pantalla.
 */
public class Player extends Spaceship {
    public static final double SPEED_PIXELS_PER_SECOND = 500.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS; //la division hace la conversion a pixeles per update
    private final Joystick joystick;
    private double radius;


    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius) {

        super(context, positionX, positionY);
        this.joystick = joystick;
        this.radius = radius;
    }

    //enlaza el joystick con el jugador en funcion del actuador
    public void update() {
        //se actualiza la velocidad en base al actuador del juego
        if (joystick != null) {
            velocityX = joystick.getActuatorX() * MAX_SPEED;
            velocityY = joystick.getActuatorY() * MAX_SPEED;

            //actualizar posicion
            positionX += velocityX;
            positionY += velocityY;
        }
    }

    @Override
    protected int getDrawableId() {
        return R.drawable.spaceship1; // Specify the drawable ID for the player spaceship
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}