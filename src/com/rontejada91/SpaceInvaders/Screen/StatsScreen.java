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
import com.rontejada91.SpaceInvaders.MainGame;
import com.rontejada91.SpaceInvaders.Save;
import com.rontejada91.SpaceInvaders.SoundManager;
import com.rontejada91.SpaceInvaders.TextureManager;
import com.rontejada91.SpaceInvaders.TweenAccessors.SpriteAccessor;
import com.rontejada91.services.ConfirmInterface;

public class StatsScreen implements Screen, ApplicationListener {
	final MainGame game;
	OrthographicCamera camera;
	boolean isTouched;
	
	Rectangle menu_icon;
	Rectangle reset_icon;

	TweenManager tweenManager;
	Sprite background;
	
	public StatsScreen(final MainGame game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, MainGame.WIDTH, MainGame.HEIGHT);
		isTouched = true;
		
		menu_icon = new Rectangle(TextureManager.BACK_REGION.getRegionWidth(), MainGame.HEIGHT * 0.20f,
				TextureManager.BACK_REGION.getRegionWidth(), TextureManager.BACK_REGION.getRegionHeight());
		
		reset_icon = new Rectangle(MainGame.WIDTH - TextureManager.DELETE_REGION.getRegionWidth() * 2, MainGame.HEIGHT * 0.20f,
				TextureManager.DELETE_REGION.getRegionWidth(), TextureManager.DELETE_REGION.getRegionHeight());
				
	}
	
	@Override
	public void render(float delta) {
		// Light Blue
		Gdx.gl.glClearColor(173 / 255f, 216 / 255f, 230 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		tweenManager.update(delta);
		
		game.batch.begin();
		
		background.draw(game.batch);
		
		game.smallShadow.draw(game.batch, "Hi-Score: " + Save.getHighScore(), game.smallShadow.getXHeight() + 2, MainGame.HEIGHT - game.smallShadow.getXHeight() - 2);
		game.smallFont.draw(game.batch, "Hi-Score: " + Save.getHighScore(), game.smallFont.getXHeight(), MainGame.HEIGHT - game.smallFont.getXHeight());
		
		game.titleShadow.draw(game.batch, "Records", MainGame.WIDTH / 2 - game.titleShadow.getBounds("Records").width / 2 + 2, MainGame.HEIGHT * 0.90f - 2);
		game.titleFont.draw(game.batch, "Records", MainGame.WIDTH / 2 - game.titleShadow.getBounds("Records").width / 2, MainGame.HEIGHT * 0.90f);
		
		game.smallShadow.draw(game.batch, "Last Games Score: " + Save.getLastScore(), MainGame.WIDTH * 0.05f + 2, MainGame.HEIGHT * 0.65f - 2);
		game.smallFont.draw(game.batch, "Last Games Score: " + Save.getLastScore(), MainGame.WIDTH * 0.05f, MainGame.HEIGHT * 0.65f);

		game.smallShadow.draw(game.batch, "Games Played: " + Save.getGameOvers(), MainGame.WIDTH * 0.05f + 2, MainGame.HEIGHT * 0.60f - 2);
		game.smallFont.draw(game.batch, "Games Played: " + Save.getGameOvers(), MainGame.WIDTH * 0.05f, MainGame.HEIGHT * 0.60f);
		
		game.smallShadow.draw(game.batch, "Waves Cleared: " + Save.getWavesDefeated(), MainGame.WIDTH * 0.05f + 2, MainGame.HEIGHT * 0.55f - 2);
		game.smallFont.draw(game.batch, "Waves Cleared: " + Save.getWavesDefeated(), MainGame.WIDTH * 0.05f, MainGame.HEIGHT * 0.55f);
		
		game.smallShadow.draw(game.batch, "Treasures Obtained: " + Save.getTreasures(), MainGame.WIDTH * 0.05f + 2, MainGame.HEIGHT * 0.50f - 2);
		game.smallFont.draw(game.batch, "Treasures Obtained: " + Save.getTreasures(), MainGame.WIDTH * 0.05f, MainGame.HEIGHT * 0.50f);
				
		game.smallShadow.draw(game.batch, "Enemies Defeated: " + Save.getEnemiesDefeated(), MainGame.WIDTH * 0.05f + 2, MainGame.HEIGHT * 0.45f - 2);
		game.smallFont.draw(game.batch, "Enemies Defeated: " + Save.getEnemiesDefeated(), MainGame.WIDTH * 0.05f, MainGame.HEIGHT * 0.45f);
										
		/*
		if (Save.getGameOvers() != 0) {	
			// Average treasures collected
			game.smallShadow.draw(game.batch, "On Average : " + Math.round(Save.getTreasures() / Save.getGameOvers()), MainGame.WIDTH * 0.15f + 2, MainGame.HEIGHT * 0.66f - 2);
			game.smallFont.draw(game.batch, "On Average : " + Math.round(Save.getTreasures() / Save.getGameOvers()), MainGame.WIDTH * 0.15f, MainGame.HEIGHT * 0.66f);
			
			// Average waves defeated
			game.smallShadow.draw(game.batch, "On Average : " + Math.round(Save.getWavesDefeated() / Save.getGameOvers()), MainGame.WIDTH * 0.15f + 2, MainGame.HEIGHT * 0.57f - 2);
			game.smallFont.draw(game.batch, "On Average : " + Math.round(Save.getWavesDefeated() / Save.getGameOvers()), MainGame.WIDTH * 0.15f, MainGame.HEIGHT * 0.57f);
			
			// Average enemies defeated
			game.smallShadow.draw(game.batch, "On Average : " + Math.round(Save.getEnemiesDefeated() / Save.getGameOvers()), MainGame.WIDTH * 0.15f + 2, MainGame.HEIGHT * 0.48f - 2);
			game.smallFont.draw(game.batch, "On Average : " + Math.round(Save.getEnemiesDefeated() / Save.getGameOvers()), MainGame.WIDTH * 0.15f, MainGame.HEIGHT * 0.48f);
		
		} else {
			// Average treasures collected
			game.smallShadow.draw(game.batch, "On Average : " + 0, MainGame.WIDTH * 0.15f + 2, MainGame.HEIGHT * 0.66f - 2);
			game.smallFont.draw(game.batch, "On Average : " + 0, MainGame.WIDTH * 0.15f, MainGame.HEIGHT * 0.66f);
			
			// Average waves defeated
			game.smallShadow.draw(game.batch, "On Average : " + 0, MainGame.WIDTH * 0.15f + 2, MainGame.HEIGHT * 0.57f - 2);
			game.smallFont.draw(game.batch, "On Average : " + 0, MainGame.WIDTH * 0.15f, MainGame.HEIGHT * 0.57f);

			// Average enemies defeated
			game.smallShadow.draw(game.batch, "On Average : " + 0, MainGame.WIDTH * 0.15f + 2, MainGame.HEIGHT * 0.48f - 2);
			game.smallFont.draw(game.batch, "On Average : " + 0, MainGame.WIDTH * 0.15f, MainGame.HEIGHT * 0.48f);
		}
		*/
		
		game.batch.draw(TextureManager.BACK_REGION, menu_icon.x, menu_icon.y);
		
		game.batch.draw(TextureManager.DELETE_REGION, reset_icon.x, reset_icon.y);
		
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
			
			if (menu_icon.contains(touchPos.x, touchPos.y)) {
				if (SoundManager.sound) SoundManager.menuSound.play(.7f);
				
				SoundManager.gameOverSound.stop();
				game.setScreen(new MainMenuScreen(game));
			}
			
			if (reset_icon.contains(touchPos.x, touchPos.y)) {				
				// Display a dialog box to confirm the reset
				
				MainGame.dialogHandler.confirm(new ConfirmInterface() {
					@Override
					public void yes() {
						Save.reset();						
						if (SoundManager.sound) SoundManager.gameOverSound.play();
					}
					
					@Override
					public void no() {
						// Do nothing
					}
				});
				
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
		// TODO Auto-generated method stub
		
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
