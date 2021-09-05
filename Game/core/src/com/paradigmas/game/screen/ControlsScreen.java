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

public class ControlsScreen extends ScreenAdapter {
    private Stage stage;
    private Texture backgroundTexture;
    private Texture returnTexture;
    private Texture returnPressTexture;
    private Texture playTexture;
    private Texture playPressTexture;


    public ControlsScreen() {}

    @Override
    public void show() {
        stage = new Stage(new FitViewport(ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = Assets.manager.get(Assets.menu_background_002);
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        //return Button
        returnTexture = Assets.manager.get(Assets.Controls);
        returnPressTexture = Assets.manager.get(Assets.On_Controls);
        ImageButton returnToMenu = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(returnTexture)),
                new TextureRegionDrawable(new TextureRegion(returnPressTexture)));
        returnToMenu.setPosition((float)(ParadigmasGame.SCREEN_WIDTH/3 - returnTexture.getWidth()/2), ParadigmasGame.SCREEN_HEIGHT /15);

        returnToMenu.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count,
                            int button) {
                super.tap(event, x, y, count, button);

                Screen currentScreen = ParadigmasGame.getInstance().getScreen();

                ParadigmasGame.getInstance().setScreen(new InitialMenuScreen());

                if (currentScreen != null) {
                    currentScreen.dispose();
                }
            }
        });
        stage.addActor(returnToMenu);

        //play Button
        playTexture = Assets.manager.get(Assets.Start);
        playPressTexture = Assets.manager.get(Assets.On_Start);
        ImageButton play = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(playTexture)),
                new TextureRegionDrawable(new TextureRegion(playPressTexture)));
        play.setPosition((float)((ParadigmasGame.SCREEN_WIDTH/3)*2 - returnTexture.getWidth()/2), ParadigmasGame.SCREEN_HEIGHT /15);

        play.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count,
                            int button) {
                super.tap(event, x, y, count, button);

                Screen currentScreen = ParadigmasGame.getInstance().getScreen();

                ParadigmasGame.getInstance().setScreen(new GameScreen(1));

                if (currentScreen != null) {
                    currentScreen.dispose();
                }
            }
        });
        stage.addActor(play);
    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}