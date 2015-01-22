package com.rontejada91.SpaceInvaders.entity;

import com.badlogic.gdx.math.Rectangle;
import com.rontejada91.SpaceInvaders.MainGame;

public class Enemy extends Rectangle {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type;
	private float vertical, horizontal;
	
	public Enemy(int type, float vertical, float horizontal) {
		this.type = type;
		this.vertical = vertical;
		this.horizontal = horizontal;
	}
	
	public void checkWalls() {
		// Bounces off of walls
		if (this.x < 0) {
			// y axis is being kept the same, we are reversing the x axis in case
			// the enemy hits a wall so that they go to the opposite direction
			this.x = 0;
			horizontal = -horizontal;
		}
		
		if (this.x > MainGame.WIDTH - this.width) {
			this.x = MainGame.WIDTH - this.width;
			horizontal = -horizontal;
		}
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public float getVertical() {
		return vertical;
	}

	public void setVertical(float vertical) {
		this.vertical = vertical;
	}

	public float getHorizontal() {
		return horizontal;
	}

	public void setHorizontal(float horizontal) {
		this.horizontal = horizontal;
	}
	
}