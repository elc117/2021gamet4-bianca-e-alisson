package com.paradigmas.game;

import com.badlogic.gdx.Game;
import com.paradigmas.game.screen.GameScreen;

public class ParadigmasGame extends Game {
	private static ParadigmasGame instance;
	public static final boolean DEBUG = true;
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;

	private ParadigmasGame() {}

	@Override
	public void create() {
		this.setScreen(new GameScreen());
	}

	public static ParadigmasGame getInstance() {
		if(instance == null)
		{
			instance = new ParadigmasGame();
		}
		return instance;
	}
}