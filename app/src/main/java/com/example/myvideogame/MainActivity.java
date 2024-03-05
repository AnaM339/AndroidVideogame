package com.example.myvideogame;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayerIntro;
    private MediaPlayer mediaPlayerJuego;
    private Game game;
    private AnimationDrawable spaceship_animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imgRobot = findViewById(R.id.spaceship);
        spaceship_animation = (AnimationDrawable) imgRobot.getDrawable();

        // Comenzar animacion
        if (spaceship_animation != null) {
            spaceship_animation.setOneShot(false);
            spaceship_animation.start();
        }

        // Establecer el contenido de la vista en Game para que los objetos de la clase Game se representen en la pantalla
        game = new Game(this);

        // Iniciar la música de introducción
        iniciarMusicaIntro();

        // Configurar el listener para el botón de iniciar el juego
        Button btnStartGame = findViewById(R.id.btnStartGame);
        btnStartGame.setOnClickListener(v -> startGame());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detenerMusicaIntro();
        detenerMusicaJuego();
    }

    private void iniciarMusicaIntro() {
        detenerMusicaIntro(); // Detener cualquier música de introducción existente
        mediaPlayerIntro = MediaPlayer.create(this, R.raw.intro);
        mediaPlayerIntro.setOnCompletionListener(mp -> mp.start());
        mediaPlayerIntro.start();
    }

    private void detenerMusicaIntro() {
        if (mediaPlayerIntro != null) {
            try {
                if (mediaPlayerIntro.isPlaying()) {
                    mediaPlayerIntro.stop();
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } finally {
                mediaPlayerIntro.reset();
                mediaPlayerIntro.release();
                mediaPlayerIntro = null;
            }
        }
    }

    private void iniciarMusicaJuego() {
        detenerMusicaJuego(); // Detener cualquier música de juego existente
        mediaPlayerJuego = MediaPlayer.create(this, R.raw.juego);
        mediaPlayerJuego.setOnCompletionListener(mp -> mp.start());
        mediaPlayerJuego.start();
    }

    private void detenerMusicaJuego() {
        if (mediaPlayerJuego != null) {
            mediaPlayerJuego.stop();
            mediaPlayerJuego.reset();
            mediaPlayerJuego.release();
            mediaPlayerJuego = null;
        }
    }

    private void startGame() {
        // Detener la música de introducción y empezar la música del juego
        detenerMusicaIntro();
        iniciarMusicaJuego();

        // Stop the animation
        if (spaceship_animation != null) {
            spaceship_animation.stop();
        }

        // Establecer el contenido de la vista en Game
        setContentView(game);

        // Iniciar el juego
        game.startGame();
    }

}
