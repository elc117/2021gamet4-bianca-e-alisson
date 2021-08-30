package com.paradigmas.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.paradigmas.game.ParadigmasGame;
import com.paradigmas.game.resource.Assets;
import com.paradigmas.game.world.World;

public class PreloadScreen extends ScreenAdapter {

    protected OrthographicCamera camera;
    protected World world;
    private ShapeRenderer shapeRenderer;
    SpriteBatch batch;

    private float progress = 0;

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera(ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT);
        camera.setToOrtho(false, ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT);

        Assets.load();
    }

    // TODO: DAR OS CREDITOS AO INSECT

    @Override
    public void render(float delta) {
        update();
        clearScreen(Color.BLACK);
        draw();
    }

    private void update() {
        if(Assets.manager.update()) {
            ParadigmasGame.getInstance().setScreen(new InitialMenuScreen(ParadigmasGame.getInstance()));
            //insects.setScreen(new MenuScreen(insects));
        } else {
            progress = Assets.manager.getProgress();
        }
    }

    public static void clearScreen(Color color){
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void draw() {
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(
                (ParadigmasGame.SCREEN_WIDTH - 400) / 2, ParadigmasGame.SCREEN_HEIGHT / 2,
                progress * 400, 50);
        shapeRenderer.end();
    }
}
