package com.paradigmas.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    private int level;
    private Stage stage;
    private BitmapFont font;
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

        font = new BitmapFont(false);
        font.setColor(Color.DARK_GRAY);
        font.getData().setScale(2f);

        /*game.backgroundAudioID = game.getAudioHandler().playBackGroundMusic();*/

        //Retry Button
        skipTexture = Assets.manager.get(Assets.Start);
        skipPressTexture = Assets.manager.get(Assets.On_Start);
        ImageButton skip = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(skipTexture)),
                new TextureRegionDrawable(new TextureRegion(skipPressTexture)));
        skip.setPosition((float)(ParadigmasGame.SCREEN_WIDTH/2 - skipTexture.getWidth()/2), PLAY_BUTTON_Y);

        skip.addListener(new ActorGestureListener() {
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
        skipTexture = Assets.manager.get(Assets.Skip);
        skipPressTexture = Assets.manager.get(Assets.On_Skip);
        ImageButton quit = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(skipTexture)),
                new TextureRegionDrawable(new TextureRegion(skipPressTexture)));
        quit.setPosition((float)(ParadigmasGame.SCREEN_WIDTH/2 - skipTexture.getWidth()/2), PLAY_BUTTON_Y-(PLAY_BUTTON_Y/2));

        quit.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);

                Screen currentScreen = game.getScreen();

                game.setScreen(new FinalScreen(level));

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
        stage.getBatch().begin();
        font.draw(stage.getBatch(), "1.0.1",
                (ParadigmasGame.SCREEN_WIDTH * .87f), (ParadigmasGame.SCREEN_HEIGHT * .95f));
        stage.getBatch().end();
        //game.getAudioHandler().updateMusic();
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
    }
}
