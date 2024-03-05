package com.example.myvideogame.object;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import com.example.myvideogame.GameLoop;
import com.example.myvideogame.R;

/**
 * La clase Enemy representa a un enemigo en el juego, hereda de Spaceship.
 * Controla su posición y comportamiento específico de los enemigos.
 */
public class Enemy extends Spaceship {

    private static final double SPEED_PIXELS_PER_SECOND = Player.SPEED_PIXELS_PER_SECOND*0.6;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS; //la division hace la conversion a pixeles per update
    private final Player player;
    private double radius;
    private Bitmap enemy;
    private static final float IMAGE_SCALE_FACTOR = 0.2f;

    public Enemy(Context context, Player player, double positionX, double positionY) {
        super(context, positionX, positionY);
        this.player = player;
        loadImage(context); // Ensure loadImage is called during construction
    }

    @Override
    protected int getDrawableId() {
        return R.drawable.enemy;
    }

    private void loadImage(Context context) {
        enemy = BitmapFactory.decodeResource(context.getResources(), getDrawableId());

        if (enemy != null) {
            int newWidth = (int) (enemy.getWidth() * IMAGE_SCALE_FACTOR);
            int newHeight = (int) (enemy.getHeight() * IMAGE_SCALE_FACTOR);
            enemy = Bitmap.createScaledBitmap(enemy, newWidth, newHeight, true);
        } else {
            Log.e(TAG, "Enemy: No se ha podido cargar la imagen. Drawable ID: " + getDrawableId());
        }
    }

    public void draw(Canvas canvas) {
        if (enemy != null) {
            canvas.drawBitmap(enemy, (float) (positionX - radius), (float) (positionY - radius), null);
        } else {
            Log.e(TAG, "Enemy: imagen nula");
        }
    }

    //El metodo actualiza la velocidad del enemigo en la direccion del jugador
    @Override
    public void update() {
        //calcular vector desde el enemigo hacia el jugador (coordenadas x e y)
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;

        //calcular la distancia absoluta entre el enemigo(this) y el jugador
        double distanceToPlayer = GameObject.getDistanceBetweenObjets(this, player);

        //calcular la dirrecion desde el enemigo al jugador
        double directionX = distanceToPlayerX/distanceToPlayer;
        double directionY = distanceToPlayerY/distanceToPlayer;

        //establecer velocidad en la direccion del enemigo
        if (distanceToPlayer > 0) { //evitar la division entre 0
            velocityX = directionX*MAX_SPEED;
            velocityY = directionY*MAX_SPEED;
        } else {
            velocityX = 0;
            velocityY = 0;
        }

        //actualizar la posicion del enemigo
        positionX += velocityX;
        positionY += velocityY;
    }
}
