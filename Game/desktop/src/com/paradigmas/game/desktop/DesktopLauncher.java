package com.paradigmas.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.paradigmas.game.ParadigmasGame;


public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.fullscreen = false;
        config.resizable = false;
        config.width = ParadigmasGame.SCREEN_WIDTH;
        config.height = ParadigmasGame.SCREEN_HEIGHT;
        config.title = ParadigmasGame.NAME;
        config.pauseWhenMinimized = true;
        //config.addIcon(Assets.manager.get(Assets.Codigo_1), );


        new LwjglApplication(ParadigmasGame.getInstance(), config);
    }
}