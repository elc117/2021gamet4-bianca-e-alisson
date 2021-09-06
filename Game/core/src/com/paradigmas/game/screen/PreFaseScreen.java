package com.paradigmas.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.paradigmas.game.entity.component.PlayerComponent;
import com.paradigmas.game.resource.Assets;
import com.paradigmas.game.tools.GdxUtils;
import com.paradigmas.game.world.World;

public class PreFaseScreen extends ScreenAdapter {
    private static final int PLAY_BUTTON_Y = ParadigmasGame.SCREEN_HEIGHT /5;
    private Texture backgroundTexture;
    private Texture nextFaseTexture;
    private Texture nextFasePressTexture;
    private int level;
    private int level2;
    private Stage stage;
    private BitmapFont font;
    private final ParadigmasGame game;

    public PreFaseScreen(int level) {
        this.level = level;
        this.level2 = level;
        if(level == -1) {
            level2 = 1;
        }
        new World(new OrthographicCamera()).regenerate(level2);
        this.game = ParadigmasGame.getInstance();
    }

    public void show() {
        stage = new Stage(new FitViewport(ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = Assets.manager.get(Assets.base_background);
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        font = new BitmapFont(false);
        font.setColor(Color.WHITE);
        font.getData().setScale(2f);

        //Next Button
        nextFaseTexture = Assets.manager.get(Assets.Start);
        nextFasePressTexture = Assets.manager.get(Assets.On_Start);
        ImageButton nextFase = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(nextFaseTexture)),
                new TextureRegionDrawable(new TextureRegion(nextFasePressTexture)));
        nextFase.setPosition((float) (ParadigmasGame.SCREEN_WIDTH / 2 - nextFaseTexture.getWidth() / 2), PLAY_BUTTON_Y);

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
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render(float delta) {
        GdxUtils.clearScreen();
        stage.act(delta);
        stage.draw();
        stage.getBatch().begin();
        font.draw(stage.getBatch(), "O tempo para essa fase é: " + ((int) World.tempo) /1000 + "s.",
                (ParadigmasGame.SCREEN_WIDTH/2 - font.getRegion().getRegionWidth()+58), (ParadigmasGame.SCREEN_HEIGHT/2 + font.getRegion().getRegionHeight()));
        font.draw(stage.getBatch(), "Voce ainda tem: " + (PlayerComponent.getCoffe()) + " xícaras de café!",
                (ParadigmasGame.SCREEN_WIDTH/2 - font.getRegion().getRegionWidth()+58), (ParadigmasGame.SCREEN_HEIGHT/2));
        stage.getBatch().end();
        //game.getAudioHandler().updateMusic();
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
    }
}
