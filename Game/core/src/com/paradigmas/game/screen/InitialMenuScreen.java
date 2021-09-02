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

public class InitialMenuScreen extends ScreenAdapter {

        private static final int PLAY_BUTTON_Y = (int) ParadigmasGame.SCREEN_HEIGHT/4;
        private static final int BUTTONS_Y = PLAY_BUTTON_Y + 24;
        private static final int OPTIONS_BUTTON_X = 400;
        private static final int SELECT_LEVELS_BUTTON_X = 2100;

        private Texture backgroundTexture;
        private Texture playTexture;
        private Texture playPressTexture;
        private Texture controlsTexture;
        private Texture controlsPressTexture;
        private Texture selectLevelsTexture;
        private Texture selectLevelsPressTexture;
        private Texture instructionsTexture;
        private Texture instructionsPressTexture;
        private Texture minigamesTexture;
        private Texture minigamesPressTexture;
        private BitmapFont font;

        private Stage stage;
        private final ParadigmasGame game;


        public InitialMenuScreen(ParadigmasGame game) {
            this.game = game;
        }

        public void show() {
            stage = new Stage(new FitViewport(ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT));
            Gdx.input.setInputProcessor(stage);
            backgroundTexture = Assets.manager.get(Assets.menu_background_002);
            Image background = new Image(backgroundTexture);
            stage.addActor(background);
            font = new BitmapFont(false);
            font.setColor(Color.DARK_GRAY);
            font.getData().setScale(2f);

            /*game.backgroundAudioID = game.getAudioHandler().playBackGroundMusic();*/

            //Play Button
            playTexture = Assets.manager.get(Assets.Start);
            playPressTexture = Assets.manager.get(Assets.On_Start);
            ImageButton play = new ImageButton(
                    new TextureRegionDrawable(new TextureRegion(playTexture)),
                    new TextureRegionDrawable(new TextureRegion(playPressTexture)));
            play.setPosition(ParadigmasGame.SCREEN_WIDTH/2 - playTexture.getWidth()/2, PLAY_BUTTON_Y);

            play.addListener(new ActorGestureListener() {
                @Override
                public void tap(InputEvent event, float x, float y, int count,
                    int button) {
                        super.tap(event, x, y, count, button);

                        Screen currentScreen = game.getScreen();

                        ParadigmasGame.getInstance().setScreen(new GameScreen());

                        if (currentScreen != null) {
                            currentScreen.dispose();
                        }
                    }
            });
            stage.addActor(play);

            //controls Button
            controlsTexture = Assets.manager.get(Assets.Controls);
            controlsPressTexture = Assets.manager.get(Assets.On_Controls);
            ImageButton controls = new ImageButton(
                    new TextureRegionDrawable(new TextureRegion(controlsTexture)),
                    new TextureRegionDrawable(new TextureRegion(controlsPressTexture)));
            controls.setPosition(ParadigmasGame.SCREEN_WIDTH/2 - controlsTexture.getWidth()/2, ParadigmasGame.SCREEN_HEIGHT/6-5);

            controls.addListener(new ActorGestureListener() {
                @Override
                public void tap(InputEvent event, float x, float y, int count,
                                int button) {
                    super.tap(event, x, y, count, button);

                    Screen currentScreen = game.getScreen();

                    ParadigmasGame.getInstance().setScreen(new GameScreen());

                    if (currentScreen != null) {
                        currentScreen.dispose();
                    }
                }
            });
            stage.addActor(controls);


            /*//Intructions Button
            instructionsTexture = assetManager.get(Constants.MENU_INSTRUCTIONS);
            instructionsPressTexture = assetManager.get(Constants.MENU_INSTRUCTIONS_PRESSED);
            ImageButton instructions = new ImageButton(
                    new TextureRegionDrawable(new TextureRegion(instructionsTexture)),
                    new TextureRegionDrawable(new TextureRegion(instructionsPressTexture)));
            instructions.setPosition(OPTIONS_BUTTON_X, PLAY_BUTTON_Y + 210);

            instructions.addListener(new ActorGestureListener() {
                @Override
                public void tap(InputEvent event, float x, float y, int count,
                                int button) {
                    super.tap(event, x, y, count, button);
                    ScreenManager.getInstance().showScreen(ScreenEnum.INSTRUCTIONS_SCREEN, game);
                }
            });
            stage.addActor(instructions);

            //Select Levels Button
            selectLevelsTexture = assetManager.get(Constants.MENU_SELECT_LEVEL);
            selectLevelsPressTexture = assetManager.get(Constants.MENU_SELECT_LEVEL_PRESSED);
            ImageButton selectLevels = new ImageButton(
                    new TextureRegionDrawable(new TextureRegion(selectLevelsTexture)),
                    new TextureRegionDrawable(new TextureRegion(selectLevelsPressTexture)));
            selectLevels.setPosition(SELECT_LEVELS_BUTTON_X, PLAY_BUTTON_Y);

            selectLevels.addListener(new ActorGestureListener() {
                @Override
                public void tap(InputEvent event, float x, float y, int count,
                                int button) {
                    super.tap(event, x, y, count, button);
                    ScreenManager.getInstance().showScreen(ScreenEnum.SELECT_LEVELS_SCREEM, game, SelectLevelsType.ONE);
                }
            });
            stage.addActor(selectLevels);*/

            //Minigames Button
            /*minigamesTexture = assetManager.get(Constants.MENU_MINIGAMES);
            minigamesPressTexture = assetManager.get(Constants.MENU_MINIGAMES_PRESSED);
            ImageButton minigames = new ImageButton(
                    new TextureRegionDrawable(new TextureRegion(minigamesTexture)),
                    new TextureRegionDrawable(new TextureRegion(minigamesPressTexture)));
            minigames.setPosition(SELECT_LEVELS_BUTTON_X, PLAY_BUTTON_Y + 210);

            minigames.addListener(new ActorGestureListener() {
                @Override
                public void tap(InputEvent event, float x, float y, int count,
                                int button) {
                    super.tap(event, x, y, count, button);
                    ScreenManager.getInstance().showScreen(ScreenEnum.MINIGAMES_SCREEN, game);
                }
            });
            stage.addActor(minigames);
            */
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
