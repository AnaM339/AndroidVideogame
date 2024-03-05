package com.example.myvideogame.object;

import android.content.Context;
import androidx.core.content.ContextCompat;

import com.example.myvideogame.GameLoop;
import com.example.myvideogame.Joystick;
import com.example.myvideogame.R;
import com.example.myvideogame.Utils;

/**
 * La clase Player representa al jugador en el juego y controla su posición y comportamiento.
 * Responde a las entradas del usuario y se dibuja en la pantalla.
 */
public class Player extends Circle {
    public static final double SPEED_PIXELS_PER_SECOND = 500.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS; //la division hace la conversion a pixeles per update
    private final Joystick joystick;


    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);
        this.joystick = joystick;
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

            //actualiza direccion
            if (velocityX != 0 || velocityY != 0) {
                //Normalizar velocidad para obtener la direccion (unit vector of velocity)
                double distance = Utils.getDistanceBetweenPoints(0,0,velocityX, velocityY);
                directionX = velocityX/distance;
                directionY = velocityY/distance;

            }
        }
    }
}
