package com.paradigmas.game;

import com.badlogic.gdx.Game;
import com.paradigmas.game.resource.Assets;
import com.paradigmas.game.screen.PreloadScreen;

public class ParadigmasGame extends Game {
    private static ParadigmasGame instance;
    public static final String NAME = "ParadigmasGame";
    public static final boolean DEBUG = true;
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
        }
    }

    @Override
    public void dispose() {
        Assets.manager.dispose();
    }
}
