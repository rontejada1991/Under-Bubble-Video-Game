package com.rontejada91.SpaceInvaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.rontejada91.SpaceInvaders.MainGame;
import com.rontejada91.SpaceInvaders.TextureManager;

public class Item extends Rectangle {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int vertical, itemTimer;
	private boolean displayItem;
	
	public Item() {
		vertical = 450;
		itemTimer = 0;
		displayItem = false;
	}
	
	public void spawnItem() {
		this.x = MathUtils.random(0, MainGame.WIDTH - TextureManager.TREASURE.getRegionWidth());
		this.y = MathUtils.random(MainGame.HEIGHT + TextureManager.TREASURE.getRegionHeight(), MainGame.HEIGHT * 2);
		this.width = TextureManager.TREASURE.getRegionWidth();
		this.height = TextureManager.TREASURE.getRegionHeight();
		displayItem = true;
	}

	// Check if there is currently an item being used, If so then move it down the screen
	// If the item reaches the bottom of the screen, remove it.
	public void moveItem() {
			this.y -= vertical * Gdx.graphics.getDeltaTime();

	}

	public int getVertical() {
		return vertical;
	}

	public void setVertical(int vertical) {
		this.vertical = vertical;
	}

	public int getItemTimer() {
		return itemTimer;
	}

	public void setItemTimer(int itemTimer) {
		this.itemTimer = itemTimer;
	}

	public boolean isDisplayItem() {
		return displayItem;
	}

	public void setDisplayItem(boolean displayItem) {
		this.displayItem = displayItem;
	}
	
}
