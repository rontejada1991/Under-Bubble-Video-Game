package com.rontejada91.SpaceInvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureManager {
	public static Texture BUBBLE_TEXTURE;
	public static Texture ENEMY_TEXTURE;
	public static Texture PLAYER_TEXTURE;
	public static Texture BACKGROUND_TEXTURE;
	public static Texture BUTTONS_TEXTURE;
		
	public static TextureRegion PLAYER_REGION;
	
	public static TextureRegion BACKGROUND_REGION;
	
	public static TextureRegion ENEMY_1;
	public static TextureRegion ENEMY_2;
	public static TextureRegion ENEMY_3;
	public static TextureRegion ENEMY_4;
	public static TextureRegion TREASURE;
	
	public static TextureRegion PLAY_REGION;
	public static TextureRegion BACK_REGION;
	public static TextureRegion RATE_REGION;
	public static TextureRegion DELETE_REGION;
	public static TextureRegion RECORDS_REGION;
	public static TextureRegion SOUND_REGION;
	public static TextureRegion MUTE_REGION;

	public static void load(int playerColor, int backgroundType) {
		BUBBLE_TEXTURE = new Texture(Gdx.files.internal("bubble.png"));
		ENEMY_TEXTURE = new Texture(Gdx.files.internal("enemies.png"));
		PLAYER_TEXTURE = new Texture(Gdx.files.internal("player.png"));
		BACKGROUND_TEXTURE = new Texture(Gdx.files.internal("background.png"));
		BUTTONS_TEXTURE = new Texture(Gdx.files.internal("buttons.png"));
		
		ENEMY_1 = new TextureRegion(ENEMY_TEXTURE, 0, 0, 50, 50);
		ENEMY_2 = new TextureRegion(ENEMY_TEXTURE, 50, 0, 50, 50);
		ENEMY_3 = new TextureRegion(ENEMY_TEXTURE, 0, 50, 50, 50);
		ENEMY_4 = new TextureRegion(ENEMY_TEXTURE, 50, 50, 50, 50);
		TREASURE = new TextureRegion(ENEMY_TEXTURE, 0, 100, 50, 50);
		
		loadButtons();
		loadPlayer(playerColor);
		loadBack(backgroundType);
		
	}
	
	public static void loadButtons() {
		PLAY_REGION = new TextureRegion(BUTTONS_TEXTURE, 0, 0, 100, 64);

		BACK_REGION = new TextureRegion(BUTTONS_TEXTURE, 0, 64, 100, 64);
		RATE_REGION = new TextureRegion(BUTTONS_TEXTURE, 100, 64, 100, 64);
		
		DELETE_REGION = new TextureRegion(BUTTONS_TEXTURE, 0, 128, 100, 64);
		RECORDS_REGION = new TextureRegion(BUTTONS_TEXTURE, 100, 128, 100, 64);
		
		SOUND_REGION = new TextureRegion(BUTTONS_TEXTURE, 0, 192, 100, 64);
		MUTE_REGION = new TextureRegion(BUTTONS_TEXTURE, 100, 192, 100, 64);
	}

	public static void loadPlayer(int playerColor) {
		switch (playerColor) {
		case 0:
			PLAYER_REGION = new TextureRegion(PLAYER_TEXTURE, 0, 0, 50, 70);
			break;
		case 1:
			PLAYER_REGION = new TextureRegion(PLAYER_TEXTURE, 50, 0, 50, 70);
			break;
		case 2:
			PLAYER_REGION = new TextureRegion(PLAYER_TEXTURE, 100, 0, 50, 70);
			break;
		case 3:
			PLAYER_REGION = new TextureRegion(PLAYER_TEXTURE, 150, 0, 50, 70);
			break;
		case 4:
			PLAYER_REGION = new TextureRegion(PLAYER_TEXTURE, 200, 0, 50, 70);
			break;
		case 5:
			PLAYER_REGION = new TextureRegion(PLAYER_TEXTURE, 0, 70, 50, 70);
			break;
		case 6:
			PLAYER_REGION = new TextureRegion(PLAYER_TEXTURE, 50, 70, 50, 70);
			break;
		case 7:
			PLAYER_REGION = new TextureRegion(PLAYER_TEXTURE, 100, 70, 50, 70);
			break;
		case 8:
			PLAYER_REGION = new TextureRegion(PLAYER_TEXTURE, 150, 70, 50, 70);
			break;
		case 9:
			PLAYER_REGION = new TextureRegion(PLAYER_TEXTURE, 200, 70, 50, 70);
			break;
		}
	}
	
	public static void loadBack(int backgroundType) {
		switch(backgroundType) {
		case 0:
			BACKGROUND_REGION = new TextureRegion(BACKGROUND_TEXTURE, 0, 0, 480, 800);
			break;
		case 1:
			BACKGROUND_REGION = new TextureRegion(BACKGROUND_TEXTURE, 480, 0, 480, 800);
			break;
		case 2:
			BACKGROUND_REGION = new TextureRegion(BACKGROUND_TEXTURE, 960, 0, 480, 800);
			break;
		case 3:
			BACKGROUND_REGION = new TextureRegion(BACKGROUND_TEXTURE, 1440, 0, 480, 800);
			break;
		}
	}
	
}