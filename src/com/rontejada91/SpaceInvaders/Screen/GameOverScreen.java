package com.rontejada91.SpaceInvaders.screen;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.rontejada91.SpaceInvaders.Save;
import com.rontejada91.SpaceInvaders.MainGame;
import com.rontejada91.SpaceInvaders.SoundManager;
import com.rontejada91.SpaceInvaders.TextureManager;
import com.rontejada91.SpaceInvaders.TweenAccessors.SpriteAccessor;

public class GameOverScreen implements Screen { 
	final MainGame game;
	OrthographicCamera camera;
	int wavesCleared;
	int enemiesDefeated;
	int treasuresObtained;
	int oldHighScore;
	String waveMessage;
	boolean isTouched;
	boolean newHighScore;
	
	Rectangle play_icon;
	Rectangle menu_icon;
	
	TweenManager tweenManager;
	Sprite background;

	public GameOverScreen(final MainGame game, int wavesCleared, int enemiesDefeated, int treasuresObtained) {
		this.game = game;
		this.wavesCleared = wavesCleared;
		this.enemiesDefeated = enemiesDefeated;
		this.treasuresObtained = treasuresObtained;  
		oldHighScore = Save.getHighScore();
		newHighScore = false;
		isTouched = true;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, MainGame.WIDTH, MainGame.HEIGHT);
		
		// Sets the last score to the waves cleared in the last game
		Save.setLastScore(wavesCleared);
		
		// Loads up a different background for the next game
		TextureManager.loadBack(MathUtils.random(0, 3));
		
		// If there is a new record, replace the old high score with the new one
		if (wavesCleared > oldHighScore) {
			Save.setHighScore(wavesCleared);
			newHighScore = true;
		} else {
			newHighScore = false;
		}
		
		createButtons();
		
	}
	
	
	public void createButtons() {
		play_icon = new Rectangle(MainGame.WIDTH - TextureManager.PLAY_REGION.getRegionWidth() * 2, MainGame.HEIGHT * 0.20f,
				TextureManager.PLAY_REGION.getRegionWidth(), TextureManager.PLAY_REGION.getRegionHeight());
		
		menu_icon = new Rectangle(TextureManager.BACK_REGION.getRegionWidth(), MainGame.HEIGHT * 0.20f,
				TextureManager.BACK_REGION.getRegionWidth(), TextureManager.BACK_REGION.getRegionHeight());
	}

	@Override
	public void render(float delta) {
		// Midnight Blue
		Gdx.gl.glClearColor(25 / 255f, 25 / 255f, 112 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		tweenManager.update(delta);
		
		game.batch.begin();

		background.draw(game.batch);
		
		game.titleShadow.draw(game.batch, "Game Over", MainGame.WIDTH / 2 - game.titleShadow.getBounds("Game Over").width / 2 + 2, MainGame.HEIGHT * 0.90f - 2);
		game.titleFont.draw(game.batch, "Game Over", MainGame.WIDTH / 2 - game.titleShadow.getBounds("Game Over").width / 2, MainGame.HEIGHT * 0.90f);
						
		// Congratulate the player if they achieved a new high score, otherwise display their score
		if (newHighScore) {
			game.smallShadow.draw(game.batch, "NEW Hi-Score: " + Save.getHighScore(), game.smallShadow.getXHeight() + 2, MainGame.HEIGHT - game.smallShadow.getXHeight() - 2);
			game.smallFont.draw(game.batch, "NEW Hi-Score: " + Save.getHighScore(), game.smallFont.getXHeight(), MainGame.HEIGHT - game.smallFont.getXHeight());
			
			game.smallShadow.draw(game.batch, "OLD Hi-Score: " + oldHighScore, MainGame.WIDTH * 0.05f + 2, MainGame.HEIGHT * 0.65f - 2);
			game.smallFont.draw(game.batch, "OLD Hi-Score: " + oldHighScore, MainGame.WIDTH * 0.05f, MainGame.HEIGHT * 0.65f);
			
			game.smallShadow.draw(game.batch, "Treasures Obtained: " + treasuresObtained, MainGame.WIDTH * 0.05f + 2, MainGame.HEIGHT * 0.55f - 2);
			game.smallFont.draw(game.batch, "Treasures Obtained: " + treasuresObtained, MainGame.WIDTH * 0.05f, MainGame.HEIGHT * 0.55f);
			
			game.smallShadow.draw(game.batch, "Enemies Defeated: " + enemiesDefeated, MainGame.WIDTH * 0.05f + 2, MainGame.HEIGHT * 0.45f - 2);
			game.smallFont.draw(game.batch, "Enemies Defeated: " + enemiesDefeated, MainGame.WIDTH * 0.05f, MainGame.HEIGHT * 0.45f);		
			
		} else {
			game.smallShadow.draw(game.batch, "Hi-Score: " + Save.getHighScore(), game.smallShadow.getXHeight() + 2, MainGame.HEIGHT - game.smallShadow.getXHeight() - 2);
			game.smallFont.draw(game.batch, "Hi-Score: " + Save.getHighScore(), game.smallFont.getXHeight(), MainGame.HEIGHT - game.smallFont.getXHeight());

			game.smallShadow.draw(game.batch, "Waves Cleared: " + wavesCleared, MainGame.WIDTH * 0.05f + 2, MainGame.HEIGHT * 0.65f - 2);
			game.smallFont.draw(game.batch, "Waves Cleared: " + wavesCleared, MainGame.WIDTH * 0.05f, MainGame.HEIGHT * 0.65f);
			
			game.smallShadow.draw(game.batch, "Treasures Obtained: " + treasuresObtained, MainGame.WIDTH * 0.05f + 2, MainGame.HEIGHT * 0.55f - 2);
			game.smallFont.draw(game.batch, "Treasures Obtained: " + treasuresObtained, MainGame.WIDTH * 0.05f, MainGame.HEIGHT * 0.55f);		
			
			game.smallShadow.draw(game.batch, "Enemies Defeated: " + enemiesDefeated, MainGame.WIDTH * 0.05f + 2, MainGame.HEIGHT * 0.45f - 2);
			game.smallFont.draw(game.batch, "Enemies Defeated: " + enemiesDefeated, MainGame.WIDTH * 0.05f, MainGame.HEIGHT * 0.45f);
			
		}

		game.batch.draw(TextureManager.PLAY_REGION, play_icon.x, play_icon.y);
		
		game.batch.draw(TextureManager.BACK_REGION, menu_icon.x, menu_icon.y);
		
		game.batch.end();
		
		// If the screen is not being touched, set isTouched to false;
		if (!Gdx.input.isTouched())
				isTouched = false;

		// If isTouched is set to false and the screen is touched, restart the game
		if (!isTouched && Gdx.input.isTouched()) {
			isTouched = true;
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			
			if (play_icon.contains(touchPos.x, touchPos.y)) {
				if (SoundManager.sound) 
					SoundManager.menuSound.play();
				
				SoundManager.gameOverSound.stop();
				
				// Loads up a different player texture for the next game
				TextureManager.loadPlayer(MathUtils.random(0, 9));
				
				game.setScreen(new GameScreen(game));
			}
			
			if (menu_icon.contains(touchPos.x, touchPos.y)) {
				if (SoundManager.sound)
					SoundManager.menuSound.play();
				
				SoundManager.gameOverSound.stop();
				
				// Loads up a different player texture for the next game
				TextureManager.loadPlayer(MathUtils.random(0, 9));
				
				game.setScreen(new MainMenuScreen(game));
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
		
		Save.setGameOvers();
		Save.setTreasures(treasuresObtained);
		Save.setEnemiesDefeated(enemiesDefeated);
		Save.setWavesDefeated(wavesCleared);
		
		if (SoundManager.sound) {
			SoundManager.gameOverSound.play();
		}
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

}
