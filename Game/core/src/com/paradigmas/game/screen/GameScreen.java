package com.paradigmas.game.screen;

import static com.paradigmas.game.ParadigmasGame.LEVEL_MAX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.paradigmas.game.ParadigmasGame;
import com.paradigmas.game.entity.component.RigidBodyComponent;
import com.paradigmas.game.entity.component.TransformComponent;
import com.paradigmas.game.world.World;

public class GameScreen extends ScreenAdapter {

    protected final Vector3 screenCoordinate = new Vector3();
    private OrthographicCamera camera;
    private ParadigmasGame game;
    private long finalTime;
    private World world;
    private int level;
    SpriteBatch batch;

    public GameScreen(int level) {
        super();
        this.game = ParadigmasGame.getInstance();
        this.level = level;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera(ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT);
        camera.setToOrtho(false, ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT);

        world = new World(camera);
        world.regenerate(level);

        finalTime = System.currentTimeMillis() + 30_000;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        if (ParadigmasGame.DEBUG) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
                if (world.getEntityTrackerWindow() != null) {
                    world.getEntityTrackerWindow().setVisible(!world.getEntityTrackerWindow().isVisible());
                }
            }

            if (Gdx.app.getInput().isTouched()) {
                screenCoordinate.set(Gdx.input.getX(), Gdx.input.getY(), 0);

                camera.unproject(screenCoordinate);

                world.getArtemisWorld().getEntity(world.getPlayer()).getComponent(TransformComponent.class).position.set(screenCoordinate.x, screenCoordinate.y);
                world.getArtemisWorld().getEntity(world.getPlayer()).getComponent(RigidBodyComponent.class).velocity.set(Vector2.Zero);
            }
        }
    }

    public void update(float delta) {
        world.update(delta);
        long currentTime = System.currentTimeMillis();

        Screen currentScreen = game.getScreen();
        if (World.quantObjetivos == 0 && level < LEVEL_MAX) {
            World.quantObjetivos = 1;
            level++;

            game.setScreen(new NextFaseScreen(level));
            if (currentScreen != null) {
                currentScreen.dispose();
            }
        } else if (currentTime >= finalTime) {
            game.setScreen(new LoseScreen(level));
            if (currentScreen != null) {
                currentScreen.dispose();
            }
        } else if (level >= LEVEL_MAX) {
            game.setScreen(new InitialMenuScreen());
            if (currentScreen != null) {
                currentScreen.dispose();
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