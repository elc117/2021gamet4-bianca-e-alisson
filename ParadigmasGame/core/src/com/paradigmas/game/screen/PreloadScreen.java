package com.paradigmas.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.paradigmas.game.ParadigmasGame;
import com.paradigmas.game.resource.Assets;

public class PreloadScreen extends ScreenAdapter {
    @Override
    public void show() {
        Assets.load();
    }

    @Override
    public void render(float delta) {
        Gdx.app.log("Progress", Assets.manager.getProgress() * 100 + "0");

        if(Assets.manager.update()) {
            ParadigmasGame.getInstance().setScreen(new GameScreen());
        }
    }
}
