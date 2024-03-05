package com.example.myvideogame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.myvideogame.object.Circle;
import com.example.myvideogame.object.Enemy;
import com.example.myvideogame.object.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * La clase Game gestiona todos los objetos del juego y es responsable de la actualización de los estados
 * y la renderización de los objetos en la pantalla.
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private final Player player;
    private final Joystick joystick;
    //private final Enemy enemy;
    private GameLoop gameLoop;
    private List<Enemy> enemyList = new ArrayList<Enemy>();

    public Game(Context context) {
        super(context);

        //Obtener surface holder y añadir callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this); //hace referencia a las instancias de la clase en la que estamos y permite responder a los inputs del usuario

        this.gameLoop = new GameLoop(this, surfaceHolder);

        //inicializar objetos del juego
        joystick = new Joystick(275, 350, 70, 40);
        player = new Player(context, joystick,2*500, 500, 30);

        setFocusable(true);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //Gestionar eventos touch
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    joystick.setIsPressed(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (joystick.getIsPressed()) {
                    //si el joystick es set y movido el actuador se establece en
                    // la posicion de la pantalla donde el jugador mueve su dedo
                    joystick.setActuator((double) event.getX(), (double) event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP: //cuando el jugador levanta el dedo
                joystick.setIsPressed(false);
                joystick.resetActuator();
                return true;
         }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop.startLoop();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        drawUPS(canvas);
        drawFPS(canvas);

        //se dibujan los objetos del juego
        joystick.draw(canvas);
        player.draw(canvas);
        for (Enemy enemy : enemyList) {
            enemy.draw(canvas);
        }

    }

    //muestra en la pantalla las actualizaciones/segundo de la pantalla (Update per second)
    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.teal_700);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100, 100, paint);
    }

    //Frames per second
    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.purple_700);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100, 200, paint);
    }

    public void update() {
        //actualiza el estado  de los objetos del juego
        joystick.update();
        player.update();

        //Crear enemigos en un momento determinado
        if (Enemy.readyToSpawn()) {
            enemyList.add(new Enemy(getContext(), player));
        }

        //Actualizar estado de cada enemigo
        for (Enemy enemy : enemyList) {
            enemy.update();
        }

        //Verificar colision entre los objetos del enemyList y el jugador
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while (iteratorEnemy.hasNext()) {
            if (Circle.isColliding(iteratorEnemy.next(),player)) {
                //quitar si colisionan
                iteratorEnemy.remove();
            }
        }
    }

}
