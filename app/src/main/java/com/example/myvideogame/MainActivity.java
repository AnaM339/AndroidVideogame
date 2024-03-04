package com.example.myvideogame;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedIstanceState) {
        super.onCreate(savedIstanceState);

        //Establecer el contenido view a Game, para que los objetos de la clase Game sean renderizados a la pantalla
        setContentView(new Game(this));
    }
}
