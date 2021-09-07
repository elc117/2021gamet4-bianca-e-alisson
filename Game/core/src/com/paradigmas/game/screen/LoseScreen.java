package com.paradigmas.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.paradigmas.game.ParadigmasGame;
import com.paradigmas.game.resource.Assets;
import com.paradigmas.game.tools.GdxUtils;

public class LoseScreen extends ScreenAdapter {
    private static final int PLAY_BUTTON_Y = ParadigmasGame.SCREEN_HEIGHT /5;

    private Texture backgroundTexture;
    private Texture retryTexture;
    private Texture retryPressTexture;
    private Texture quitTexture;
    private Texture quitPressTexture;

    private int level;
    private Stage stage;
    private final ParadigmasGame game;


    public LoseScreen(int level) {
        this.level = level;
        this.game = ParadigmasGame.getInstance();
    }

    public void show() {
        stage = new Stage(new FitViewport(ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = Assets.manager.get(Assets.lose_background_001);
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        /*game.backgroundAudioID = game.getAudioHandler().playBackGroundMusic();*/

        //Retry Button
        retryTexture = Assets.manager.get(Assets.Retry);
        retryPressTexture = Assets.manager.get(Assets.On_Retry);
        ImageButton retry = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(retryTexture)),
                new TextureRegionDrawable(new TextureRegion(retryPressTexture)));
        retry.setPosition((float)(ParadigmasGame.SCREEN_WIDTH/2 - retryTexture.getWidth()/2), PLAY_BUTTON_Y);

        retry.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);

                Screen currentScreen = game.getScreen();

                game.setScreen(new GameScreen(level));

                if (currentScreen != null) {
                    currentScreen.dispose();
                }
            }
        });
        stage.addActor(retry);

        //quit Button
        // TODO: trocar as variavel de textura
        quitTexture = Assets.manager.get(Assets.quit);
        quitPressTexture = Assets.manager.get(Assets.On_quit);
        ImageButton quit = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(quitTexture)),
                new TextureRegionDrawable(new TextureRegion(quitPressTexture)));
        quit.setPosition((float)(ParadigmasGame.SCREEN_WIDTH/2 - quitTexture.getWidth()/2), PLAY_BUTTON_Y-(PLAY_BUTTON_Y/2));

        quit.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);

                Screen currentScreen = game.getScreen();

                game.setScreen(new InitialMenuScreen(level));

                if (currentScreen != null) {
                    currentScreen.dispose();
                }
            }
        });
        stage.addActor(quit);
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render(float delta) {
        GdxUtils.clearScreen();
        stage.act(delta);
        stage.draw();

        //game.getAudioHandler().updateMusic();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
