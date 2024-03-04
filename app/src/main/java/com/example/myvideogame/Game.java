package com.example.myvideogame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * La clase Game gestiona todos los objetos del juego y es responsable de la actualizacion de los estados
 * y la renderizacion de los objetos en la pantalla
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private final Player player;
    private final Joystick joystick;
    private GameLoop gameLoop;


    public Game(Context context) {
        super(context);

        //Obtener surface holder y añadir callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this); //hace referencia a las instancias de la clase en la que estamos y permite responder a los inputs del usuario

        this.gameLoop = new GameLoop(this, surfaceHolder);

        //inicializar objetos del juego
        player = new Player(context, 1100, 500, 30);
        joystick = new Joystick(275, 350, 70, 40);

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
        player.draw(canvas);
        joystick.draw(canvas);

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
        player.update(joystick);
        joystick.update();
    }

}
