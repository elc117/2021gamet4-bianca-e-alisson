package com.paradigmas.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.paradigmas.game.ParadigmasGame;

public class HtmlLauncher extends GwtApplication {

        public static void main(String[] args) {
                LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.fullscreen = false;
                config.resizable = false;
                config.width = ParadigmasGame.SCREEN_WIDTH;
                config.height = ParadigmasGame.SCREEN_HEIGHT;
                config.title = ParadigmasGame.NAME;
                config.pauseWhenMinimized = true;

                new LwjglApplication(ParadigmasGame.getInstance(), config);
        }

        @Override
        public GwtApplicationConfiguration getConfig () {
                // Resizable application, uses available space in browser
                return new GwtApplicationConfiguration(true);
                // Fixed size application:
                //return new GwtApplicationConfiguration(480, 320);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return ParadigmasGame.getInstance();
        }
}