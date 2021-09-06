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

// TODO: DAR OS CREDITOS AO INSEC

public class NextFaseScreen extends ScreenAdapter {

    private static final int PLAY_BUTTON_Y = ParadigmasGame.SCREEN_HEIGHT /5;

    private Texture backgroundTexture;
    private Texture nextFaseTexture;
    private Texture nextFasePressTexture;
    private Texture quitTexture;
    private Texture quitPressTexture;

    private int level;
    private float tempRestante;
    private Stage stage;
    private BitmapFont font;
    private final ParadigmasGame game;

    public NextFaseScreen(int level, float tempRestante) {
        this.game = ParadigmasGame.getInstance();
        this.level = level;
        this.tempRestante = tempRestante;
    }

    public void show() {
        stage = new Stage(new FitViewport(ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = Assets.manager.get(Assets.nextfase_background_001);
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        font = new BitmapFont(false);
        font.setColor(Color.DARK_GRAY);
        font.getData().setScale(2f);

        /*game.backgroundAudioID = game.getAudioHandler().playBackGroundMusic();*/

        //Next Button
        nextFaseTexture = Assets.manager.get(Assets.NextFase);
        nextFasePressTexture = Assets.manager.get(Assets.On_NextFase);
        ImageButton nextFase = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(nextFaseTexture)),
                new TextureRegionDrawable(new TextureRegion(nextFasePressTexture)));
        nextFase.setPosition((float)(ParadigmasGame.SCREEN_WIDTH/2 - nextFaseTexture.getWidth()/2), PLAY_BUTTON_Y);

        nextFase.addListener(new ActorGestureListener() {
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
        stage.addActor(nextFase);

        //quit Button
        // TODO: trocar as variavel de textura
        quitTexture = Assets.manager.get(Assets.Start);
        quitPressTexture = Assets.manager.get(Assets.Start);
        ImageButton quit = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(quitTexture)),
                new TextureRegionDrawable(new TextureRegion(quitPressTexture)));
        quit.setPosition((float)(ParadigmasGame.SCREEN_WIDTH/2 - quitTexture.getWidth()/2), PLAY_BUTTON_Y-(PLAY_BUTTON_Y/2));

        quit.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);

                Screen currentScreen = game.getScreen();

                game.setScreen(new FinalScreen(level-1));

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
        font.draw(stage.getBatch(), "Tempo restante: " + ((int) tempRestante) /1000 + "s.",
                (24), (ParadigmasGame.SCREEN_HEIGHT * .95f));
        stage.getBatch().end();
        //game.getAudioHandler().updateMusic();
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
    }
}
