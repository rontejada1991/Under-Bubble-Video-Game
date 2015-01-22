package com.rontejada91.SpaceInvaders.entity;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.rontejada91.SpaceInvaders.MainGame;
import com.rontejada91.SpaceInvaders.TextureManager;

public class BubbleGenerator {
	private Array<Rectangle> bubbleArray;
	private long lastBubbleSpawnTime;
	private long bubbleSpawnDelay;
	private int bubbleSpeed;
	
	// Player's first bubbles
	public BubbleGenerator(long bubbleSpawnDelay, int bubbleSpeed) {
		bubbleArray = new Array<Rectangle>();
		lastBubbleSpawnTime = 0;
		this.bubbleSpawnDelay = bubbleSpawnDelay;
		this.bubbleSpeed = bubbleSpeed;

	}
	
	// Bubble power up applied after
	public BubbleGenerator(long bubbleSpawnDelay, int bubbleSpeed, long lastBubbleSpawnTime) {
		bubbleArray = new Array<Rectangle>();
		this.lastBubbleSpawnTime = lastBubbleSpawnTime;
		this.bubbleSpawnDelay = bubbleSpawnDelay;
		this.bubbleSpeed = bubbleSpeed;

	}

	// Spawns a bubble in front and middle of the current player's position
	public void spawnBubble(Rectangle object) {
		Rectangle newBubble = new Rectangle();
		newBubble.x = object.x + TextureManager.PLAYER_REGION.getRegionWidth() / 2 - TextureManager.BUBBLE_TEXTURE.getWidth() / 2;
		newBubble.y = object.y + TextureManager.PLAYER_REGION.getRegionHeight() - TextureManager.BUBBLE_TEXTURE.getWidth() / 2;
		newBubble.width = TextureManager.BUBBLE_TEXTURE.getWidth();
		newBubble.height = TextureManager.BUBBLE_TEXTURE.getHeight();
		bubbleArray.add(newBubble);		
		
		lastBubbleSpawnTime = TimeUtils.nanoTime();
	}
	
	public void spawnLeftBubble(Rectangle object) {
		Rectangle newBubble = new Rectangle();
		newBubble.x = object.x - TextureManager.BUBBLE_TEXTURE.getWidth() / 2;
		newBubble.y = object.y + TextureManager.PLAYER_REGION.getRegionHeight() - TextureManager.BUBBLE_TEXTURE.getWidth() / 2;
		newBubble.width = TextureManager.BUBBLE_TEXTURE.getWidth();
		newBubble.height = TextureManager.BUBBLE_TEXTURE.getHeight();
		bubbleArray.add(newBubble);		
		
		lastBubbleSpawnTime = TimeUtils.nanoTime();
	}
	
	public void spawnRightBubble(Rectangle object) {
		Rectangle newBubble = new Rectangle();
		newBubble.x = object.x + TextureManager.PLAYER_REGION.getRegionWidth() - TextureManager.BUBBLE_TEXTURE.getWidth() / 2;
		newBubble.y = object.y + TextureManager.PLAYER_REGION.getRegionHeight() - TextureManager.BUBBLE_TEXTURE.getWidth() / 2;
		newBubble.width = TextureManager.BUBBLE_TEXTURE.getWidth();
		newBubble.height = TextureManager.BUBBLE_TEXTURE.getHeight();
		bubbleArray.add(newBubble);		
		
		lastBubbleSpawnTime = TimeUtils.nanoTime();
	}
	
	// A) Moves the bubbles up 
	// B) Removes bubbles that reach the top
	public void moveBubbles() {
		Iterator<Rectangle> iter = bubbleArray.iterator();
		while (iter.hasNext()) {
			Rectangle bubble = iter.next();
			bubble.y += bubbleSpeed * Gdx.graphics.getDeltaTime();
			if (bubble.y >= MainGame.HEIGHT) {
				iter.remove();
			}
		}
	}

	public Array<Rectangle> getBubbleArray() {
		return bubbleArray;
	}

	public void setBubbleArray(Array<Rectangle> bubbleArray) {
		this.bubbleArray = bubbleArray;
	}

	public long getLastBubbleSpawnTime() {
		return lastBubbleSpawnTime;
	}

	public void setLastBubbleSpawnTime(long lastBubbleSpawnTime) {
		this.lastBubbleSpawnTime = lastBubbleSpawnTime;
	}

	public long getBubbleSpawnDelay() {
		return bubbleSpawnDelay;
	}

	public void setBubbleSpawnDelay(long bubbleSpawnDelay) {
		this.bubbleSpawnDelay = bubbleSpawnDelay;
	}

	public int getBubbleSpeed() {
		return bubbleSpeed;
	}

	public void setBubbleSpeed(int bubbleSpeed) {
		this.bubbleSpeed = bubbleSpeed;
	}
	
}
