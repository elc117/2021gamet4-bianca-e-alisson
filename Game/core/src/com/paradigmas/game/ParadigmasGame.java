package com.paradigmas.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.paradigmas.game.resource.Assets;
import com.paradigmas.game.screen.PreloadScreen;

public class ParadigmasGame extends Game {
    private static ParadigmasGame instance;
    public static final String NAME = "ParadigmasGame";
    public static final boolean DEBUG = false; // muda para true para debugar
    public static final int SCREEN_WIDTH = 840;
    public static final int SCREEN_HEIGHT = 504;
    public static final int LEVEL_MAX = 2;

    FPSLogger fpslogger = new FPSLogger(NAME, true, false);

    // Construtor
    private ParadigmasGame() {
    }

    @Override
    public void create() {
        // A preeloadScreen que carrega as texturas
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
