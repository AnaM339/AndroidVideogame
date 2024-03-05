package com.example.myvideogame;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * La clase MainActivity es la actividad principal del juego.
 * Inicia el juego al establecer el contenido de la vista en una instancia de la clase Game.
 */
public class MainActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedIstanceState) {
        super.onCreate(savedIstanceState);

        //Establecer el contenido view a Game, para que los objetos de la clase Game sean renderizados a la pantalla
        setContentView(new Game(this));
    }
}
