package com.rontejada91.SpaceInvaders.entity;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.rontejada91.SpaceInvaders.MainGame;
import com.rontejada91.SpaceInvaders.TextureManager;

public class EnemyGenerator {
	private Array<Enemy> enemies;
	private int enemiesPerWave;
	private int enemySpeedModifier;
	
	public EnemyGenerator(int enemiesPerWave) {
		enemies = new Array<Enemy>();
		this.enemiesPerWave = enemiesPerWave;
		enemySpeedModifier = 0;
	}
	
	public void spawnWave() {
		// There are 4 enemy patterns, each with an equal chance of appearing
		// 1. All of 1 type, 2. All of 2 types, 3. All of 3 types, and 4. All of 4 types
		float enemyPattern = MathUtils.random(0, 3);
		
		if (enemyPattern == 0) {
			float enemyType = MathUtils.random(0, 3);
			
			// Spawn all the minions based on the enemy type we chose at random ...
			for (int i = 0; i < enemiesPerWave; i++) {
				setEnemyByType(enemyType);
			}
			
		} else if (enemyPattern == 1) {
			float enemyType = MathUtils.random(0, 3);
			
			float enemyType2 = MathUtils.random(0, 3);			
			// Guarantees that we have 2 different enemies
			while (enemyType2 == enemyType)
				enemyType2 = MathUtils.random(0, 3);
			
			// We will be decreasing the number of enemies left to spawn
			int currentEnemies = enemiesPerWave;
			
			// As long as there are enemies left to spawn, spawn one of each
			while (currentEnemies != 0) {
				setEnemyByType(enemyType);
				currentEnemies--;
				
				if (currentEnemies != 0) {
					setEnemyByType(enemyType2);
					currentEnemies--;
				}
			}
			
		} else if (enemyPattern == 2) {
			// Similar to our previous else if statement above this one, but with 3 instead of 2 enemies
			float enemyType = MathUtils.random(0, 3);

			float enemyType2 = MathUtils.random(0, 3);		
			// Guarantees that we get 2 different enemies
			while (enemyType2 == enemyType)
				enemyType2 = MathUtils.random(0, 3);
			
			float enemyType3 = MathUtils.random(0, 3);		
			// Guarantees that we get 3 different enemies
			while (enemyType3 == enemyType || enemyType3 == enemyType2)
					enemyType3 = MathUtils.random(0, 3);
			
			// We will be decreasing the number of enemies left to spawn
			int currentEnemies = enemiesPerWave;
			
			// As long as there are enemies left to spawn, spawn one of each
			while (currentEnemies != 0) {
				setEnemyByType(enemyType);
				currentEnemies--;
				
				if (currentEnemies != 0) {
					setEnemyByType(enemyType2);
					currentEnemies--;
				}
				
				if (currentEnemies != 0) {
					setEnemyByType(enemyType3);
					currentEnemies--;
				}
			}
			
		} else {
			// Enemy type will always be random
			float enemyType = MathUtils.random(0, 3);
			
			for (int i = 0; i < enemiesPerWave; i++) {
				setEnemyByType(enemyType);	
				enemyType = MathUtils.random(0, 3);
			}
		}
	}	
	
	public void setEnemyByType(float enemyType) {
		Enemy newEnemy;
		if (enemyType == 0) {
			newEnemy = new Enemy(0, 450f, 250f);
			newEnemy.width = TextureManager.ENEMY_1.getRegionWidth();
			newEnemy.height = TextureManager.ENEMY_1.getRegionHeight();
		} else if (enemyType == 1) {
			newEnemy = new Enemy(1, 420f, 280f);
			newEnemy.width = TextureManager.ENEMY_2.getRegionWidth();
			newEnemy.height = TextureManager.ENEMY_2.getRegionHeight();
		} else if (enemyType == 2) {
			newEnemy = new Enemy(2, 390f, 310f);
			newEnemy.width = TextureManager.ENEMY_3.getRegionWidth();
			newEnemy.height = TextureManager.ENEMY_3.getRegionHeight();
		} else {
			newEnemy = new Enemy(3, 360f, 340f);
			newEnemy.width = TextureManager.ENEMY_4.getRegionWidth();
			newEnemy.height = TextureManager.ENEMY_4.getRegionHeight();
		}
		
		// Set where the enemy will be located
		newEnemy.x = MathUtils.random(newEnemy.width, MainGame.WIDTH - newEnemy.width);
		newEnemy.y = MathUtils.random(MainGame.HEIGHT + newEnemy.height, MainGame.HEIGHT * 2 - newEnemy.height);
		
		// Set whether the enemy starts going from the left or right
		if (MathUtils.randomBoolean()) {
			newEnemy.setHorizontal(-newEnemy.getHorizontal());
		}
		
		// Add the new enemy to the enemy array
		enemies.add(newEnemy);
	}
	
	public void moveEnemies() {
		Iterator<Enemy> iter = enemies.iterator();
		while (iter.hasNext()) {
			Enemy enemy = iter.next();
			enemy.x -= enemy.getHorizontal() * Gdx.graphics.getDeltaTime();
			// Check that the enemy is not attached to the edges of the screen
			enemy.checkWalls();
			enemy.y -= (enemy.getVertical() + enemySpeedModifier) * Gdx.graphics.getDeltaTime();
			// Makes the enemy reappear at the top of the screen if it reaches the bottom of the screen
			if (enemy.y + enemy.height < 0) {
				enemy.y = MainGame.HEIGHT;
			}
		}
	}

	public Array<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(Array<Enemy> enemies) {
		this.enemies = enemies;
	}

	public int getEnemiesPerWave() {
		return enemiesPerWave;
	}

	public void setEnemiesPerWave(int enemiesPerWave) {
		this.enemiesPerWave = enemiesPerWave;
	}

	public int getEnemySpeedModifier() {
		return enemySpeedModifier;
	}

	public void setEnemySpeedModifier(int enemySpeedModifier) {
		this.enemySpeedModifier = enemySpeedModifier;
	}

}