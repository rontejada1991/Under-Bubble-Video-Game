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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.rontejada91.SpaceInvaders.MainGame;
import com.rontejada91.SpaceInvaders.SoundManager;
import com.rontejada91.SpaceInvaders.TextureManager;
import com.rontejada91.SpaceInvaders.TweenAccessors.SpriteAccessor;
import com.rontejada91.SpaceInvaders.entity.BubbleGenerator;
import com.rontejada91.SpaceInvaders.entity.Enemy;
import com.rontejada91.SpaceInvaders.entity.EnemyGenerator;
import com.rontejada91.SpaceInvaders.entity.Item;
import com.rontejada91.SpaceInvaders.entity.Player;

public class GameScreen implements Screen, ApplicationListener {
	private final MainGame game;

	private OrthographicCamera camera;
	
	private Player player;
	private BubbleGenerator bubbles;
	private BubbleGenerator leftBubbles;
	private BubbleGenerator rightBubbles;
	private EnemyGenerator enemies;
	private Item item;
	
	private int wavesCleared;
	
	// stats to keep track of
	private int treasures;
	private int enemiesDefeated;
	
	private final int BUBBLE_SPEED = 450;
	private final long BUBBLE_SPAWN_DELAY = 125000000;
	
	TweenManager tweenManager;
	Sprite background;
	
	// The game does not begin until the player touched the screen, we set to isTouched to true
	// at the start in case the player is already touching the screen, this prevents the game
	// from starting automatically from the last screen
	boolean isTouched;
	boolean begin;
	
	public GameScreen(final MainGame newGame) {
		game = newGame;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, MainGame.WIDTH, MainGame.HEIGHT);
		bubbles = new BubbleGenerator(BUBBLE_SPAWN_DELAY, BUBBLE_SPEED);
		enemies = new EnemyGenerator(10);
		wavesCleared = 0;
		item = new Item();
		
		treasures = 0;
		enemiesDefeated = 0;
		
		isTouched = true;
		begin = false;
		
		// Spawns the player
		player = new Player();
		
	}
	
	public void update() {
		if (!begin) {		
			// If the screen is not being touched, set isTouched to false;
			if (!Gdx.input.isTouched())
					isTouched = false;		
		
			// If isTouched is set to false and the screen is touched, start the game
			if (!isTouched && Gdx.input.isTouched()) {
				// spawn the first wave
				enemies.spawnWave();
						
				// spawn the first item
				item.spawnItem();
				
				begin = true;
			}
		}
		
		// Process user input, only if the game has begun
		if (begin)
			player.processUserInput(camera);		
		
		// Player bubbles spawn, move, and collision detection
		if (TimeUtils.nanoTime() - bubbles.getLastBubbleSpawnTime() > bubbles.getBubbleSpawnDelay())
			bubbles.spawnBubble(player);

		// Moving item as long as it is above the screen
		// We place begin because we do not want the item to be generated at the bottom of the screen
		if (begin && item.y > -item.height)
			item.moveItem();

		// Moving the enemies
		enemies.moveEnemies();
		
		// Moving the bubbles
		bubbles.moveBubbles();
		
		// Item and Player collision
		if (item.isDisplayItem()) {
			if (item.overlaps(player)) {
				Gdx.input.vibrate(250);
				if (SoundManager.sound) 
					SoundManager.powerUpSound.play();
				item.setDisplayItem(false);
				item.setItemTimer(3);
				itemEffect();
				treasures++;
			}
		}
		
		// End of the wave
		// Make sure it isn't the first wave or it will increase count from the start
		if (begin && enemies.getEnemies().size == 0)
			endOfWave();
		
		collisionDetection(bubbles.getBubbleArray());
		
		// Right bubbles spawn, move, and collision detection
		if (leftBubbles != null) {
			if (TimeUtils.nanoTime() - leftBubbles.getLastBubbleSpawnTime() > leftBubbles.getBubbleSpawnDelay())
				leftBubbles.spawnLeftBubble(player);
			
			leftBubbles.moveBubbles();
			
			collisionDetection(leftBubbles.getBubbleArray());
		}
		
		// Right bubbles spawn, move, and collision detection
		if (rightBubbles != null) {
			
			if (TimeUtils.nanoTime() - rightBubbles.getLastBubbleSpawnTime() > rightBubbles.getBubbleSpawnDelay())
				rightBubbles.spawnRightBubble(player);
			
			rightBubbles.moveBubbles();
			
			collisionDetection(rightBubbles.getBubbleArray());
		}
	}
	
	public void render(float delta) {
		// Light Blue
		Gdx.gl.glClearColor(173 / 255f, 216 / 255f, 230 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		tweenManager.update(delta);
		
		this.update();
		
		game.batch.begin();
		
		background.draw(game.batch);
		
		// Instruct the player to tap the screen to begin, remove the title once the game starts
		if (!begin) {
			game.smallShadow.draw(game.batch, "Tap Or Hold Anywhere To Begin!", MainGame.WIDTH / 2 - game.smallShadow.getBounds("Tap Or Hold Anywhere to Begin!").width / 2 + 2, MainGame.HEIGHT * 0.55f - 2);
			game.smallFont.draw(game.batch, "Tap Or Hold Anywhere To Begin!", MainGame.WIDTH / 2 - game.smallFont.getBounds("Tap Or Hold Anywhere to Begin!").width / 2, MainGame.HEIGHT * 0.55f);
		}
			
		// display the current wave score
		game.smallShadow.draw(game.batch, "Waves: " + Integer.toString(wavesCleared), game.smallShadow.getXHeight() + 2, MainGame.HEIGHT - game.smallShadow.getXHeight() - 2);
		game.smallFont.draw(game.batch, "Waves: " + Integer.toString(wavesCleared), game.smallFont.getXHeight(), MainGame.HEIGHT - game.smallFont.getXHeight());
		
		// render player
		game.batch.draw(TextureManager.PLAYER_REGION, player.x, player.y);
		
		// render bubbles
		for (Rectangle bubble : bubbles.getBubbleArray()) {
			game.batch.draw(TextureManager.BUBBLE_TEXTURE, bubble.x, bubble.y);
		}
		
		if (leftBubbles != null)
			for (Rectangle bubble : leftBubbles.getBubbleArray())
				game.batch.draw(TextureManager.BUBBLE_TEXTURE, bubble.x, bubble.y);
		
		if (rightBubbles != null)
			for (Rectangle bubble : rightBubbles.getBubbleArray())
				game.batch.draw(TextureManager.BUBBLE_TEXTURE, bubble.x, bubble.y);
		
		// Render all the enemy frames inside our array
		for (Enemy enemy : enemies.getEnemies()) {
			if (enemy.getType() == 0) {
				game.batch.draw(TextureManager.ENEMY_1, enemy.x, enemy.y);
			}
			else if (enemy.getType() == 1) {
				game.batch.draw(TextureManager.ENEMY_2, enemy.x, enemy.y);
			}
			else if (enemy.getType() == 2) {
				game.batch.draw(TextureManager.ENEMY_3, enemy.x, enemy.y);
			}
			else {
				game.batch.draw(TextureManager.ENEMY_4, enemy.x, enemy.y);
			}
		}
		
		// Render our falling item if there is any
		if (item.isDisplayItem())
			game.batch.draw(TextureManager.TREASURE, item.x, item.y);
		
		game.batch.end();
				
	}
	
	public void endOfWave() {
		if (SoundManager.sound) 
			SoundManager.scoreSound.play();
		
		// If the integer will reach max value now, increase the waves cleared
		// and set the game to game over. Otherwise, increase the number of waves cleared. 
		if (wavesCleared == Integer.MAX_VALUE - 1) {
			wavesCleared++;
			Gdx.input.vibrate(500);
			game.setScreen(new GameOverScreen(game, wavesCleared, enemiesDefeated, treasures));
		} else {
			wavesCleared++;
		}
		
		// Increase the number of enemies every even wave, except the 2nd wave
		if (wavesCleared != 0 && wavesCleared % 2 == 0)
			enemies.setEnemiesPerWave(enemies.getEnemiesPerWave() + 1);
		
		// Increase the speed of the enemies every odd wave, except the 1st wave
		if (wavesCleared != 1 && wavesCleared % 2 == 1)
			enemies.setEnemySpeedModifier(enemies.getEnemySpeedModifier() + 2);
						
		// spawn a random item, every 6th wave
		if (wavesCleared % 6 == 0)
			item.spawnItem();
		
		// subtract 1 point from the item timer if it was consumed (checked off by boolean)
		if (item.getItemTimer() > 0) {
			item.setItemTimer(item.getItemTimer() - 1);
		} else {
			if (leftBubbles != null)
				leftBubbles = null;
				
			if (rightBubbles != null)
				rightBubbles = null;
			
			this.bubbles.setBubbleSpeed(BUBBLE_SPEED);
			this.bubbles.setBubbleSpawnDelay(BUBBLE_SPAWN_DELAY);	
			
		}
		
		// Spawn the next wave of enemies
		enemies.spawnWave();
	}
	
	public void collisionDetection(Array<Rectangle> bubbles) {	
		for (Enemy e : enemies.getEnemies()) {
			// Enemy and Player collision
			if (e.overlaps(player)) {
				SoundManager.bubbleSound.stop();
				SoundManager.powerUpSound.stop();
				SoundManager.scoreSound.stop();
				enemies.getEnemies().clear();
				Gdx.input.vibrate(500);
				game.setScreen(new GameOverScreen(game, wavesCleared, enemiesDefeated, treasures));
			}
									
			// Bubble and Enemy collision
			for (Rectangle b : bubbles) {
				if (b.overlaps(e)) {
					enemies.getEnemies().removeValue(e, false);
					bubbles.removeValue(b, false);
					if (SoundManager.sound) 
						SoundManager.bubbleSound.play();
					enemiesDefeated++;
				}
			}
		}
	}

	// Remove the item since it has been consumed by the player
	// and apply the effects
	public void itemEffect() {
		switch(MathUtils.random(0, 2)) {
		// 2 more bubble streams next to the original bubble stream
		case 0: 
			leftBubbles = new BubbleGenerator(bubbles.getBubbleSpawnDelay(), bubbles.getBubbleSpeed(), bubbles.getLastBubbleSpawnTime());
			rightBubbles = new BubbleGenerator(bubbles.getBubbleSpawnDelay(), bubbles.getBubbleSpeed(), bubbles.getLastBubbleSpawnTime());
			break;
		// bubble stream is 1/3rd slower (allowing more spread) and 3 times the spawn speed
		case 1:
			bubbles.setBubbleSpeed(bubbles.getBubbleSpeed() / 3);
			bubbles.setBubbleSpawnDelay(bubbles.getBubbleSpawnDelay() / 3);
			break;
			// bubble stream is 3 times faster and 3 times the spawn speed
		case 2:
			bubbles.setBubbleSpeed(bubbles.getBubbleSpeed() * 3);
			bubbles.setBubbleSpawnDelay(bubbles.getBubbleSpawnDelay() / 3);
			break;
		}
	}
	
	@Override
	public void dispose() {
		game.dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		
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
		
	}

	@Override
	public void resize(int width, int height) {

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