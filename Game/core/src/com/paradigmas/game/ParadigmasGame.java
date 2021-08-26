package com.paradigmas.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.paradigmas.game.resource.Assets;
import com.paradigmas.game.screen.PreloadScreen;

public class ParadigmasGame extends Game {
    private static ParadigmasGame instance;
    public static final String NAME = "ParadigmasGame";
    public static final boolean DEBUG = false;
    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 512;

    FPSLogger fpslogger = new FPSLogger(NAME, true, false);

    private ParadigmasGame() {
    }

    @Override
    public void create() {
        this.setScreen(new PreloadScreen());
    }

    public static ParadigmasGame getInstance() {
        if (instance == null) {
            instance = new ParadigmasGame();
        }
        return instance;
    }

    @Override
    public void render() {
        super.render();

        if (DEBUG) {
            fpslogger.log();

            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                    getScreen().show();
                }
            }
        }
    }

    @Override
    public void dispose() {
        Assets.manager.dispose();
    }
}
