package com.paradigmas.game.tools;

import com.badlogic.gdx.Screen;
import com.paradigmas.game.ParadigmasGame;

// TODO: DAR OS CREDITOS AO INSECT

/**
 * Based on http://www.pixnbgames.com/blog/libgdx/how-to-manage-screens-in-libgdx/
 */

public class ScreenManager {

    private static ScreenManager instance;
    private ParadigmasGame game;
    private ScreenManager() {
    }

    // Singleton: retrieve instance
    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void initialize(ParadigmasGame game) {
        this.game = game;
    }

    // Show in the game the screen which enum type is received
    public void showScreen(/*ScreenEnum screenEnum*/ Object... params) {

        // Get current screen to dispose it
        Screen currentScreen = game.getScreen();

        // Show new screen
        //Screen newScreen = screenEnum.getScreen(params);
        //game.setScreen(newScreen);

        // Dispose previous screen
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }
}
