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

// TODO: DAR OS CREDITOS AO INSEC

public class InitialMenuScreen extends ScreenAdapter {

        private static final int PLAY_BUTTON_Y = ParadigmasGame.SCREEN_HEIGHT /5;

        private final int LVL1 = 1;
        private Texture backgroundTexture;
        private Texture playTexture;
        private Texture playPressTexture;
        private Texture controlsPressTexture;
        private Texture controlsTexture;
        private Stage stage;
        private final ParadigmasGame game;
        private int continueGame;


        public InitialMenuScreen() {
            this.game = ParadigmasGame.getInstance();
            this.continueGame = 0;
        }

        public InitialMenuScreen(int level) {
            this.game = ParadigmasGame.getInstance();
            this.continueGame = level;
        }

        public void show() {
            stage = new Stage(new FitViewport(ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT));
            Gdx.input.setInputProcessor(stage);

            backgroundTexture = Assets.manager.get(Assets.menu_background_002);
            Image background = new Image(backgroundTexture);
            stage.addActor(background);

            /*game.backgroundAudioID = game.getAudioHandler().playBackGroundMusic();*/

            //Play Button
            playTexture = Assets.manager.get(Assets.Start);
            playPressTexture = Assets.manager.get(Assets.On_Start);
            ImageButton play = new ImageButton(
                    new TextureRegionDrawable(new TextureRegion(playTexture)),
                    new TextureRegionDrawable(new TextureRegion(playPressTexture)));
            play.setPosition((float)(ParadigmasGame.SCREEN_WIDTH/2 - playTexture.getWidth()/2), PLAY_BUTTON_Y+PLAY_BUTTON_Y/2);

            play.addListener(new ActorGestureListener() {
                @Override
                public void tap(InputEvent event, float x, float y, int count,
                    int button) {
                        super.tap(event, x, y, count, button);

                        Screen currentScreen = game.getScreen();

                        game.setScreen(new GameScreen(LVL1-2));

                        if (currentScreen != null) {
                            currentScreen.dispose();
                        }
                    }
            });
            stage.addActor(play);

            //ContinueGame button
            controlsTexture = Assets.manager.get(Assets.Continue);
            controlsPressTexture = Assets.manager.get(Assets.On_Continue);
            ImageButton contGame = new ImageButton(
                    new TextureRegionDrawable(new TextureRegion(controlsTexture)),
                    new TextureRegionDrawable(new TextureRegion(controlsPressTexture)));
            contGame.setPosition(ParadigmasGame.SCREEN_WIDTH/2 - controlsTexture.getWidth()/2, PLAY_BUTTON_Y);

            contGame.addListener(new ActorGestureListener() {
                @Override
                public void tap(InputEvent event, float x, float y, int count,
                                int button) {
                    super.tap(event, x, y, count, button);

                    if(continueGame != 0) {
                        Screen currentScreen = game.getScreen();

                        game.setScreen(new GameScreen(continueGame));

                        if (currentScreen != null) {
                            currentScreen.dispose();
                        }
                    } else {
                        ;
                    }
                }
            });
            stage.addActor(contGame);

            //controls Button
            controlsTexture = Assets.manager.get(Assets.Controls);
            controlsPressTexture = Assets.manager.get(Assets.On_Controls);
            ImageButton controls = new ImageButton(
                    new TextureRegionDrawable(new TextureRegion(controlsTexture)),
                    new TextureRegionDrawable(new TextureRegion(controlsPressTexture)));
            controls.setPosition(ParadigmasGame.SCREEN_WIDTH/2 - controlsTexture.getWidth()/2, PLAY_BUTTON_Y-(PLAY_BUTTON_Y/2));

            controls.addListener(new ActorGestureListener() {
                @Override
                public void tap(InputEvent event, float x, float y, int count,
                                int button) {
                    super.tap(event, x, y, count, button);

                    Screen currentScreen = game.getScreen();

                    game.setScreen(new ControlsScreen());

                    if (currentScreen != null) {
                        currentScreen.dispose();
                    }
                }
            });
            stage.addActor(controls);

            Image image = new Image(Assets.manager.get(Assets.menu_icon_1));
            image.setPosition((float)(ParadigmasGame.SCREEN_WIDTH/2 - playTexture.getWidth()/2)-75, PLAY_BUTTON_Y*2+75);
            //image.setColor(Color.BLUE);

            stage.addActor(image);
        }

        public void resize(int width, int height) {
            stage.getViewport().update(width, height, true);
        }

        public void render(float delta) {
            GdxUtils.clearScreen();
            stage.act(delta);
            stage.draw();
            String vers =  (String) "" + System.currentTimeMillis();

            //game.getAudioHandler().updateMusic();
        }

        @Override
        public void dispose() {
            stage.dispose();
        }
}
