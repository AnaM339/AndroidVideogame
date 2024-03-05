package com.example.myvideogame.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.myvideogame.GameLoop;
import com.example.myvideogame.R;

public class Spell extends Circle {

    public static final double SPEED_PIXELS_PER_SECOND = 800.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    public Spell(Context context, Player spellCaster) {
        super(
                context,
                ContextCompat.getColor(context, R.color.spell),
                spellCaster.getPositionX(),
                spellCaster.getPositionY(),
               25
        );
        velocityX = spellCaster.getDirectionX()*MAX_SPEED;
        velocityY = spellCaster.getDirectionY()*MAX_SPEED;
    }

    public void update() {
        positionX += velocityX;
        positionY += velocityY;
    }
}