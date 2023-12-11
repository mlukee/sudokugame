package com.sudokugame.game.config;

public class GameConfig {

    public static final int WIDTH = 1000; // pixels
    public static final int HEIGHT = 1000; // pixels

    public static final float HUD_WIDTH = 900f; // world units
    public static final float HUD_HEIGHT = 900f; // world units

    public static final float WORLD_WIDTH = 500; // world units
    public static final float WORLD_HEIGHT = 500; // world units

    public static final String PREFS_NAME = "SettingsPrefs";
    public static final String PREFS_TIME = "timeSetting";
    public static final String PREFS_MISTAKES = "mistakeSetting";
    public static final String PREFS_MUSIC_VOLUME = "musicVolumeSetting";
    public static final String PREFS_SFX_ENABLED = "sfxEnabledSetting";

    private GameConfig() {
    }
}
