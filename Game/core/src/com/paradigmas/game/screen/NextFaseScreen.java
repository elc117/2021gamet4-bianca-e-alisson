package com.paradigmas.game.screen;

import com.badlogic.gdx.Gdx;
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
import com.paradigmas.game.world.World;

public class NextFaseScreen extends ScreenAdapter {

    private Texture backgroundTexture;
    private Texture nextTexture;
    private Texture nextPressTexture;

    private BitmapFont font;

    private Stage stage;
    private final ParadigmasGame game;
    private World world;
    private int level;

    public NextFaseScreen (ParadigmasGame game, World world, int level) {
        this.game = game;
        this.world = world;
        this.level = level;
    }

    public void show() {
        stage = new Stage(new FitViewport(ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        backgroundTexture = Assets.manager.get(Assets.menu_background_001);
        Image background = new Image(backgroundTexture);
        stage.addActor(background);
        font = new BitmapFont(false);
        font.setColor(Color.DARK_GRAY);
        font.getData().setScale(2f);

        /*game.backgroundAudioID = game.getAudioHandler().playBackGroundMusic();*/

        //Play Button
        nextTexture = Assets.manager.get(Assets.Start);
        nextPressTexture = Assets.manager.get(Assets.On_Start);
        ImageButton play = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(nextTexture)),
                new TextureRegionDrawable(new TextureRegion(nextPressTexture)));
        play.setPosition(ParadigmasGame.SCREEN_WIDTH / 2 - nextTexture.getWidth() / 2, 200);

        play.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count,
                            int button) {
                super.tap(event, x, y, count, button);

                world.regenerate(level);
            }
        });
        stage.addActor(play);
    }
}
