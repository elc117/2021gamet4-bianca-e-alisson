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

public class StoryScreen extends ScreenAdapter  {
    private static final int PLAY_BUTTON_Y = ParadigmasGame.SCREEN_HEIGHT /5;
    private Texture backgroundTexture;
    private Texture nextFaseTexture;
    private Texture nextFasePressTexture;
    private Stage stage;
    private final ParadigmasGame game;

    public StoryScreen() {
        this.game = ParadigmasGame.getInstance();
    }

    public void show() {
        stage = new Stage(new FitViewport(ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = Assets.manager.get(Assets.story_background);
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        //Next Button
        nextFaseTexture = Assets.manager.get(Assets.Continue);
        nextFasePressTexture = Assets.manager.get(Assets.On_Continue);
        ImageButton nextFase = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(nextFaseTexture)),
                new TextureRegionDrawable(new TextureRegion(nextFasePressTexture)));
        nextFase.setPosition((float) (ParadigmasGame.SCREEN_WIDTH / 2 - nextFaseTexture.getWidth() / 2), PLAY_BUTTON_Y-(PLAY_BUTTON_Y/2));

        nextFase.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                Screen currentScreen = game.getScreen();

                game.setScreen(new InitialMenuScreen());

                if (currentScreen != null) {
                    currentScreen.dispose();
                }
            }
        });
        stage.addActor(nextFase);
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
