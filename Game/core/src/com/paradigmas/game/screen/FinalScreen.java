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

public class FinalScreen extends ScreenAdapter {
    private static final int PLAY_BUTTON_Y = ParadigmasGame.SCREEN_HEIGHT /5;
    private Texture backgroundTexture;
    private Texture skipTexture;
    private Texture skipPressTexture;

    private int level;
    private Stage stage;
    private final ParadigmasGame game;

    public FinalScreen(int level) {
        this.level = level;
        this.game = ParadigmasGame.getInstance();
    }

    public void show() {
        stage = new Stage(new FitViewport(ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = Assets.manager.get(Assets.final_background_001);
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        /*game.backgroundAudioID = game.getAudioHandler().playBackGroundMusic();*/

        //RestartarGame Button
        // TODO: trocar as variavel de textura
        skipTexture = Assets.manager.get(Assets.menu);
        skipPressTexture = Assets.manager.get(Assets.On_menu);
        ImageButton skip = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(skipTexture)),
                new TextureRegionDrawable(new TextureRegion(skipPressTexture)));
        skip.setPosition((float)(ParadigmasGame.SCREEN_WIDTH/2 - skipTexture.getWidth()/2), PLAY_BUTTON_Y);

        skip.addListener(new ActorGestureListener() {
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
        stage.addActor(skip);
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
