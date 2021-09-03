package com.paradigmas.game.screen;

import static com.paradigmas.game.ParadigmasGame.LEVEL_MAX;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.paradigmas.game.ParadigmasGame;
import com.paradigmas.game.entity.component.RigidBodyComponent;
import com.paradigmas.game.entity.component.TransformComponent;
import com.paradigmas.game.resource.Assets;
import com.paradigmas.game.world.World;

public class GameScreen extends ScreenAdapter {

    protected OrthographicCamera camera;
    protected World world;
    SpriteBatch batch;
    protected int level;
    private long finalTime;

    protected final Vector3 screenCoordinate = new Vector3();

    public GameScreen(int level) {
        super();
        this.level = level;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera(ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT);
        camera.setToOrtho(false, ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT);

        world = new World(camera);
        world.regenerate(1);

        // finalTime = System.currentTimeMillis() + 10_000;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(Assets.manager.get(Assets.cobblestone), 100, 100);
        batch.end();

        long currentTime = System.currentTimeMillis();
        world.update(delta);

        if(World.quantObjetivos == 0 && level < LEVEL_MAX) {
            World.quantObjetivos = 1;
            level++;

            ParadigmasGame.getInstance().setScreen(new NextFaseScreen(ParadigmasGame.getInstance(), world, level, camera));
        }
        //else if (level >= LEVEL_MAX || currentTime >= finalTime) {
            //ParadigmasGame.getInstance().setScreen(new InitialMenuScreen(ParadigmasGame.getInstance()));
        //}

        if (ParadigmasGame.DEBUG) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
                if (world.getEntityTrackerWindow() != null) {
                    world.getEntityTrackerWindow().setVisible(!world.getEntityTrackerWindow().isVisible());
                }
            }

            if(Gdx.app.getInput().isTouched()) {
                screenCoordinate.set(Gdx.input.getX(), Gdx.input.getY(), 0);

                camera.unproject(screenCoordinate);

                world.getArtemisWorld().getEntity(world.getPlayer()).getComponent(TransformComponent.class).position.set(screenCoordinate.x, screenCoordinate.y);
                world.getArtemisWorld().getEntity(world.getPlayer()).getComponent(RigidBodyComponent.class).velocity.set(Vector2.Zero);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
    }
}