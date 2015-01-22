package com.rontejada91.SpaceInvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Save {
	
	public static Preferences save;
	
	// loads up the current high score
	public static void load() {
		save = Gdx.app.getPreferences("BubbleFrenzy");
		
		// If there is no sound, set it to true
		if (!save.contains("sound")) {
			save.putBoolean("sound", true);
		}
		
		// If there is no game overs, set it to 0
		if (!save.contains("gameOvers")) {
			save.putInteger("gameOvers", 0);
		}
		
		// If there is no high score, set it to 0
		if (!save.contains("highScore")) {
			save.putInteger("highScore", 0);
		}
		
		// If there is no last score, set it to 0
		if (!save.contains("lastScore")) {
			save.putInteger("lastScore", 0);
		}
		
		// If there is no treasures, set it to 0
		if (!save.contains("treasures")) {
			save.putInteger("treasures", 0);
		}
		
		// If there is no enemies defeated, set it to 0
		if (!save.contains("enemiesDefeated")) {
			save.putInteger("enemiesDefeated", 0);
		}
		
		// If there is no waves defeated, set it to 0
		if (!save.contains("wavesDefeated")) {
			save.putInteger("wavesDefeated", 0);
		}
		
		save.flush();				
	}
	
	// get and set mute preference
	public static void setSound(boolean val) {
		save.putBoolean("sound", val);
		save.flush();
	}
	
	public static boolean getSound() {
		return save.getBoolean("sound");
	}
	
	// get and set methods for the high score
	public static void setHighScore(int val) {
		save.putInteger("highScore", val);
		save.flush();
	}
	
	public static int getHighScore() {
		return save.getInteger("highScore");
	}
	
	// get and set methods for the last score
	public static void setLastScore(int val) {
		save.putInteger("lastScore", val);
		save.flush();
	}
	
	public static int getLastScore() {
		return save.getInteger("lastScore");
	}
	
	public static void setTreasures(int val) {
		if (save.getInteger("treasures") + val < 0) save.putInteger("treasures", Integer.MAX_VALUE);
		else save.putInteger("treasures", save.getInteger("treasures") + val);
		save.flush();
	}
	
	public static int getTreasures() {
		return save.getInteger("treasures");
	}
	
	public static void setGameOvers() {
		if (save.getInteger("gameOvers") < 0) save.putInteger("gameOvers", Integer.MAX_VALUE);
		else save.putInteger("gameOvers", save.getInteger("gameOvers") + 1);
		save.flush();
	}
	
	public static int getGameOvers() {
		return save.getInteger("gameOvers");
	}
	
	public static void setEnemiesDefeated(int val) {
		if (save.getInteger("enemiesDefeated") + val < 0) save.putInteger("enemiesDefeated", Integer.MAX_VALUE);
		else save.putInteger("enemiesDefeated", save.getInteger("enemiesDefeated") + val);
		save.flush();
	}
	
	public static int getEnemiesDefeated() {
		return save.getInteger("enemiesDefeated");
	}
	
	public static void setWavesDefeated(int val) {
		if (save.getInteger("wavesDefeated") + val < 0) save.putInteger("wavesDefeated", Integer.MAX_VALUE);
		else save.putInteger("wavesDefeated", save.getInteger("wavesDefeated") + val);
		save.flush();
	}
	
	public static int getWavesDefeated() { 
		return save.getInteger("wavesDefeated");
	}

	public static void reset() {
		save.putInteger("gameOvers", 0);
		save.putInteger("highScore", 0);
		save.putInteger("lastScore", 0);
		save.putInteger("treasures", 0);
		save.putInteger("enemiesDefeated", 0);
		save.putInteger("wavesDefeated", 0);
		save.flush();
	}

}
