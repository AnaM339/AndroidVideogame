package com.example.myvideogame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.myvideogame.object.Circle;
import com.example.myvideogame.object.Enemy;
import com.example.myvideogame.object.Player;
import com.example.myvideogame.object.Spell;

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
    private GameLoop gameLoop;
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private List<Spell> spellList = new ArrayList<Spell>();
    private int joystickPointerId = 0;
    private int numberOfSpellsToCast = 0;

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
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: //el primer punto que se toca en la pantalla
            case MotionEvent.ACTION_POINTER_DOWN: //permite gestionar puntos de tacto multiple
                if (joystick.getIsPressed()) {
                    //el joystick ha sido presionado antes de este evento -> se realiza spell
                    numberOfSpellsToCast++;

                }else if(joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    //el joystick esta presionado ahora (current event)
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                } else {
                    //el joystick no ha sido presionado antes de este evento y no esta presionado ahor -> se realiza nuevo spell
                    numberOfSpellsToCast++;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                ////el joystick ha sido presionado antes de este evento y se esta moviendo ahora
                if (joystick.getIsPressed()) {
                    //si el joystick es set y movido el actuador se establece en
                    // la posicion de la pantalla donde el jugador mueve su dedo
                    joystick.setActuator((double) event.getX(), (double) event.getY());
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (joystickPointerId == event.getPointerId(event.getActionIndex())) {
                    //cuando el jugador levanta el dedo -> setPressed esta falso, se resetea el actuador
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }

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
        for (Spell spell : spellList) {
            spell.draw(canvas);
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
        while (numberOfSpellsToCast > 0) {
            spellList.add(new Spell(getContext(),player));
            numberOfSpellsToCast--;
        }
        for (Enemy enemy : enemyList) {
            enemy.update();
        }

        //Actualizar estado de cada spell
        for (Spell spell : spellList) {
            spell.update();
        }

        //Verificar colision entre los objetos del enemyList y el jugador y sus spells
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while (iteratorEnemy.hasNext()) {
            Circle enemy = iteratorEnemy.next();
            if (Circle.isColliding(enemy, player)) {
                // desaparece enemigo si se choca con el jugador
                iteratorEnemy.remove();
                continue;
            }

            Iterator<Spell> iteratorSpell = spellList.iterator();
            while (iteratorSpell.hasNext()) {
                Circle spell = iteratorSpell.next();
                // desaparece enemigo si le toca un spell
                if (Circle.isColliding(spell, enemy)) {
                    iteratorSpell.remove();
                    iteratorEnemy.remove();
                    break;
                }
            }
        }
    }

}
