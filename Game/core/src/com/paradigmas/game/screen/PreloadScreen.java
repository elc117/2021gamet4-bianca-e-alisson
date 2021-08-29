package com.paradigmas.game.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.paradigmas.game.ParadigmasGame;
import com.paradigmas.game.resource.Assets;
import com.paradigmas.game.world.World;

public class PreloadScreen extends ScreenAdapter {

    protected OrthographicCamera camera;
    protected World world;
    SpriteBatch batch;

    private float progress = 0;

    @Override
    public void show() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera(ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT);
        camera.setToOrtho(false, ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT);

        Assets.load();
    }

    @Override
    public void render(float delta) {
        new DelayAction(1);
        update();

    }

    private void update() {
        if(Assets.manager.update()) {
            ParadigmasGame.getInstance().setScreen(new GameScreen());
            //insects.setScreen(new MenuScreen(insects));
        } else {
            progress = Assets.manager.getProgress();
        }
    }
}
