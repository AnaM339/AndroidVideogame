package com.example.myvideogame.object;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.myvideogame.GameLoop;
import com.example.myvideogame.R;

/**
 * La clase Enemy representa a un enemigo en el juego, hereda de Circle.
 * Controla su posición y comportamiento específico de los enemigos.
 */
public class Enemy extends Circle {

    private static final double SPEED_PIXELS_PER_SECOND = Player.SPEED_PIXELS_PER_SECOND*0.6;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS; //la division hace la conversion a pixeles per update
    private static final double SPAWNS_PER_MINUTE = 20.0;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE/60.0; //conversion de minutos a segundos
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS/SPAWNS_PER_SECOND;
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN;
    private final Player player;

    public Enemy(Context context, Player player, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.enemy), positionX, positionY, radius);
        this.player = player;
    }

    /**
     * El constructor overload de Enemy se utiliza para generar enemigos en ubicaciones aleatorias.
     * @param context
     * @param player
     */
    public Enemy(Context context, Player player) {
        super(
                context,
                ContextCompat.getColor(context, R.color.enemy),
                Math.random() * 1000,
                Math.random() * 1000,
                30
        );
        this.player = player;
    }

    /**
     * readyToSpawn verifica si debe generarse un nuevo enemigo, según el número decidido de generaciones
     * por minuto (ver SPAWNS_PER_MINUTE en la parte superior).
     * @return
     */
    public static boolean readyToSpawn() {
        if (updatesUntilNextSpawn <= 0) {
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        } else {
            updatesUntilNextSpawn--;
            return false;
        }
    }

    //El metodo actualiza la velocidad del enemigo en la direccion del jugador
    public void update() {

        //   Actualizar la velocidad del enemigo para que la velocidad esté en dirección al jugador.
        // Calcular el vector desde el enemigo hasta el jugador (en x e y)
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;
        // Calcular la distancia (absoluta) entre el enemigo (this) y el jugador
        double distanceToPlayer = GameObject.getDistanceBetweenObjects(this, player);
        // Calcular la dirección desde el enemigo hasta el jugador
        double directionX = distanceToPlayerX / distanceToPlayer;
        double directionY = distanceToPlayerY / distanceToPlayer;
        // Establecer la velocidad en la dirección hacia el jugador
        if (distanceToPlayer > 0) { // Evitar la división por cero
            velocityX = directionX * MAX_SPEED;
            velocityY = directionY * MAX_SPEED;
        } else {
            velocityX = 0;
            velocityY = 0;
        }

        //   Actualizar la posición del enemigo
        positionX += velocityX;
        positionY += velocityY;
    }
}
