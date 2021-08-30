package com.paradigmas.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

// TODO: DAR OS CREDITOS AO INSECT

/**
 * Created by Gabriel on 15/10/2017.
 * Based on Goran's course
 */

public class GdxUtils {

    public static void clearScreen(){
        clearScreen(Color.BLACK);
    }
    public static void clearScreen(Color color){
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private GdxUtils(){}
}
