package com.rontejada91.SpaceInvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	public static boolean sound;
	public static Sound bubbleSound; 
	public static Sound gameOverSound;
	public static Sound menuSound; 
	public static Sound powerUpSound;
	public static Sound scoreSound;
	
	public static void load() {
		sound = Save.getSound();
		bubbleSound = Gdx.audio.newSound(Gdx.files.internal("bubble.aac"));
		gameOverSound = Gdx.audio.newSound(Gdx.files.internal("game_over.aac"));
		menuSound = Gdx.audio.newSound(Gdx.files.internal("menu.aac"));
		powerUpSound = Gdx.audio.newSound(Gdx.files.internal("power_up.aac"));
		scoreSound = Gdx.audio.newSound(Gdx.files.internal("score.aac"));
	}

}
