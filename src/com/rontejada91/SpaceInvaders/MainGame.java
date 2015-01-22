package com.rontejada91.SpaceInvaders;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.rontejada91.SpaceInvaders.screen.MainMenuScreen;
import com.rontejada91.services.ButtonPressHandler;
import com.rontejada91.services.RequestHandler;

public class MainGame extends Game implements ApplicationListener {
	public static int WIDTH = 480, HEIGHT = 800;
	public SpriteBatch batch;
	public BitmapFont smallFont, smallShadow, titleFont, titleShadow;
	public static RequestHandler dialogHandler;
	public static ButtonPressHandler buttonPressHandler;

	public MainGame(ButtonPressHandler button) {
		dialogHandler = (RequestHandler) button;
		buttonPressHandler = button;
	}
	
	public MainGame() {
		
	}
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		
		smallFont = new BitmapFont(Gdx.files.internal("text.fnt"));
		smallFont.setScale(.31f, .31f);
		smallShadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
		smallShadow.setScale(.31f, .31f);
		
		titleFont = new BitmapFont(Gdx.files.internal("text.fnt"));
		titleShadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));

		// Loads up the current stats and preferences
		Save.load();
		
		// Loads up all the resources to be used in the game
		TextureManager.load(MathUtils.random(0, 9), MathUtils.random(0, 3));

		// Loads up all the sound used in the game
		SoundManager.load();
		
		this.setScreen(new MainMenuScreen(this));
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		smallFont.dispose();
		smallShadow.dispose();
		titleFont.dispose();
		titleShadow.dispose();
	}

}