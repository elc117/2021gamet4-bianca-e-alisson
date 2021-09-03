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

public class ControlsScreen extends ScreenAdapter {
    private BitmapFont font;
    private Stage stage;

    private Texture backgroundTexture;
    private Texture returnTexture;
    private Texture returnPressTexture;

    public ControlsScreen() {}

    @Override
    public void show() {
        stage = new Stage(new FitViewport(ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = Assets.manager.get(Assets.menu_background_002);
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        font = new BitmapFont(false);
        font.setColor(Color.DARK_GRAY);
        font.getData().setScale(2f);

        //return Button
        returnTexture = Assets.manager.get(Assets.Start);
        returnPressTexture = Assets.manager.get(Assets.On_Start);
        ImageButton play = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(returnTexture)),
                new TextureRegionDrawable(new TextureRegion(returnPressTexture)));
        play.setPosition((float)(ParadigmasGame.SCREEN_WIDTH/2 - returnTexture.getWidth()/2), ParadigmasGame.SCREEN_HEIGHT /5);

        play.addListener(new ActorGestureListener() {
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