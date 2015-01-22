package com.rontejada91.SpaceInvaders.screen;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.rontejada91.SpaceInvaders.Save;
import com.rontejada91.SpaceInvaders.MainGame;
import com.rontejada91.SpaceInvaders.SoundManager;
import com.rontejada91.SpaceInvaders.TextureManager;
import com.rontejada91.SpaceInvaders.TweenAccessors.SpriteAccessor;

public class MainMenuScreen implements Screen, ApplicationListener {
	final MainGame game;
	OrthographicCamera camera;
	// Prevents actions from taking place if the user is still touching the screen from the
	// last screen
	boolean isTouched;
	Rectangle player_icon;
	Rectangle leaderboards_icon;
	Rectangle sound_icon;
	Rectangle mute_icon;
	Rectangle rank_icon;
	Rectangle stats_icon;
	
	TweenManager tweenManager;
	Sprite background;
	
	// When coming from the MainGame
	public MainMenuScreen(final MainGame game) {
		this.game = game;
		// Assume the user is currently touching the screen
		// This will be lifted once they release their finger from the screen
		// or ignored if they do not have their finger on the screen
		isTouched = true;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, MainGame.WIDTH, MainGame.HEIGHT);
		createButtons();
	}
	
	// When coming from another screen
	public MainMenuScreen(final MainGame game, boolean isTouched) {
		this.game = game;
		this.isTouched = isTouched;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, MainGame.WIDTH, MainGame.HEIGHT);
		createButtons();
	}
	
	public void createButtons() {

		player_icon = new Rectangle(TextureManager.PLAY_REGION.getRegionWidth(), MainGame.HEIGHT * .40f, 
				TextureManager.PLAY_REGION.getRegionWidth(), TextureManager.PLAY_REGION.getRegionHeight());

		stats_icon = new Rectangle(MainGame.WIDTH - TextureManager.RECORDS_REGION.getRegionWidth() * 2, MainGame.HEIGHT * .40f,
				TextureManager.RECORDS_REGION.getRegionWidth(), TextureManager.RECORDS_REGION.getRegionHeight());
		
		sound_icon = new Rectangle(TextureManager.SOUND_REGION.getRegionWidth(), MainGame.HEIGHT * .20f,
				TextureManager.SOUND_REGION.getRegionWidth(), TextureManager.SOUND_REGION.getRegionHeight());
		
		mute_icon = new Rectangle(TextureManager.MUTE_REGION.getRegionWidth(), MainGame.HEIGHT * .20f,
				TextureManager.MUTE_REGION.getRegionWidth(), TextureManager.MUTE_REGION.getRegionHeight());
		
		rank_icon = new Rectangle(MainGame.WIDTH - TextureManager.RATE_REGION.getRegionWidth() * 2, MainGame.HEIGHT * .20f,
				TextureManager.RATE_REGION.getRegionWidth(), TextureManager.RATE_REGION.getRegionHeight());

	}

	@Override
	public void render(float delta) {
		// Light Blue
		Gdx.gl.glClearColor(173 / 255f, 216 / 255f, 230 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		
		background.draw(game.batch);
		
		game.smallShadow.draw(game.batch, "Hi-Score: " + Save.getHighScore(), game.smallShadow.getXHeight() + 2, MainGame.HEIGHT - game.smallShadow.getXHeight() - 2);
		game.smallFont.draw(game.batch, "Hi-Score: " + Save.getHighScore(), game.smallFont.getXHeight(), MainGame.HEIGHT - game.smallFont.getXHeight());
		
		game.titleShadow.draw(game.batch, "Under", MainGame.WIDTH / 2 - game.titleShadow.getBounds("Under").width / 2 + 2, MainGame.HEIGHT * 0.85f - 2);
		game.titleFont.draw(game.batch, "Under", MainGame.WIDTH / 2 - game.titleShadow.getBounds("Under").width / 2, MainGame.HEIGHT * 0.85f);	
		
		game.titleShadow.draw(game.batch, "Bubble", MainGame.WIDTH / 2 - game.titleShadow.getBounds("Bubble").width / 2 + 2, MainGame.HEIGHT * 0.75f - 2);
		game.titleFont.draw(game.batch, "Bubble", MainGame.WIDTH / 2 - game.titleShadow.getBounds("Bubble").width / 2, MainGame.HEIGHT * 0.75f);
		
		game.batch.draw(TextureManager.PLAY_REGION, player_icon.x, player_icon.y);
		
		game.batch.draw(TextureManager.RATE_REGION, rank_icon.x, rank_icon.y);

		if (SoundManager.sound)
			game.batch.draw(TextureManager.SOUND_REGION, sound_icon.x, sound_icon.y);
		else 
			game.batch.draw(TextureManager.MUTE_REGION, mute_icon.x, mute_icon.y);
		
		game.batch.draw(TextureManager.RECORDS_REGION, stats_icon.x, stats_icon.y);
		
		game.batch.end();
		
		// If the screen is not being touched, set isTouched to false;
		if (!Gdx.input.isTouched())
			isTouched = false;

		// If the screen is not being touched (isTouched is false) and the screen is then touched, proceed
		if (!isTouched && Gdx.input.isTouched()) {
			isTouched = true;
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			
			if (player_icon.contains(touchPos.x, touchPos.y)) {
				if (SoundManager.sound) SoundManager.menuSound.play();
				game.setScreen(new GameScreen(game));
			}
			
			// If the game is on sound, set sound to false and play a sound effect
			// Otherwise set it to true
			if (sound_icon.contains(touchPos.x, touchPos.y)) {
				if (SoundManager.sound) {
					Save.setSound(false);
					SoundManager.sound = Save.getSound();
				} else {
					Save.setSound(true);
					SoundManager.sound = Save.getSound();
					SoundManager.menuSound.play();
				}

			}
			
			if (stats_icon.contains(touchPos.x, touchPos.y)) {
				if (SoundManager.sound) SoundManager.menuSound.play();
				game.setScreen(new StatsScreen(game));
			}
			
			if (rank_icon.contains(touchPos.x, touchPos.y)) {
				if (SoundManager.sound) SoundManager.menuSound.play();
				MainGame.buttonPressHandler.buttonPressed();
			}
			
		}		
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		
		background = new Sprite(TextureManager.BACKGROUND_REGION);
		background.setSize(MainGame.WIDTH, MainGame.HEIGHT);	
		
		Tween.set(background, SpriteAccessor.ALPHA)
			.target(0)
			.start(tweenManager);
		Tween.to(background, SpriteAccessor.ALPHA, 2.5f)
		.target(1)
		.ease(TweenEquations.easeOutQuad)
		.start(tweenManager);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

}
