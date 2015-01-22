package com.rontejada91.SpaceInvaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.rontejada91.SpaceInvaders.MainGame;
import com.rontejada91.SpaceInvaders.TextureManager;

public class Player extends Rectangle {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Player() {
		this.x = MainGame.WIDTH / 2 - TextureManager.PLAYER_REGION.getRegionWidth() / 2;
		this.y = 120;
		this.width = TextureManager.PLAYER_REGION.getRegionWidth();
		this.height = TextureManager.PLAYER_REGION.getRegionHeight();
	}
	
	// A) If the screen or mouse is touched, move the player's x position to that new position
	// B) If the user attempts to go out of bounds, keep the player at the edge instead
	public void processUserInput(OrthographicCamera camera) {
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			this.x = touchPos.x - TextureManager.PLAYER_REGION.getRegionWidth() / 2;
			this.y = touchPos.y + TextureManager.PLAYER_REGION.getRegionHeight();
		}
		
		// Make sure the player stays within the screen bounds for the x input
		if (this.x < 0)
			this.x = 0;
		if (this.x > MainGame.WIDTH - TextureManager.PLAYER_REGION.getRegionWidth())
			this.x = MainGame.WIDTH - TextureManager.PLAYER_REGION.getRegionWidth();
		
		// Make sure the player stays within the screen bounds for the y input
		if (this.y < 0)
			this.y = 0;
		if (this.y > MainGame.HEIGHT - TextureManager.PLAYER_REGION.getRegionHeight())
			this.y = MainGame.HEIGHT - TextureManager.PLAYER_REGION.getRegionHeight();
	}

}
