package com.example.myvideogame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.zip.Adler32;

public class GameLoop extends Thread {

    public static final double MAX_UPS = 30.0; //30 actualizaciones por segundo
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;
    private boolean isRunning = true;
    private SurfaceHolder surfaceHolder;
    private Game game;
    private double averageUPS;
    private double averageFPS;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.game = game;
    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();

        //declarar contadores del tiempo y ciclo
        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        //GameLoop
        //gestiona las tareas de la bucle del juego
        Canvas canvas = null;
        startTime = System.currentTimeMillis();
        while (isRunning) {
            
            //actualiza y renderiza el juego
            try {
                canvas = surfaceHolder.lockCanvas();

                //el metodo sincronizar para el surfaceHolder impide que Threads multiples
                // llamen a los metodos draw y update al mismo tiempo que el hilo actual
                synchronized (surfaceHolder) {
                    game.update();
                    updateCount++; //se incrementa inmediatamente despues de la actualizacion del juego
                    game.draw(canvas);
                }

            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++; //solo se incrementa si el canvas se publica en el surfaceHolder
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            //pausar el bucle para no sobrepasar el UPS
            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long)  (updateCount * UPS_PERIOD - elapsedTime);
            if(sleepTime > 0) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //saltar frames para no sobrepasar el UPS
            while (sleepTime < 0 && updateCount < MAX_UPS-1) { //la segunda condicion impide que se sobrepase el MAX_UPS
                game.update();
                updateCount++;
                //se recalcula el sleeptime para ver si hace falta saltar frames para mantener el paso con el UPS
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long)  (updateCount * UPS_PERIOD - elapsedTime);

            }

            //calcular la media del UPS y FPS
            elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= 1000) {
                averageUPS = updateCount / (1E-3 * elapsedTime);
                averageFPS = frameCount / (1E-3 * elapsedTime);
                //los contadores se actualizan despues de un segundo
                //se empieza otra vez a contar desde 0
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();

            }

        }
    }
}
