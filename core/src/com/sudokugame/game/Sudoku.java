package com.sudokugame.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.sudokugame.game.assets.AssetDescriptors;
import com.sudokugame.game.config.GameConfig;
import com.sudokugame.game.screen.GameScreen;
import com.sudokugame.game.screen.IntroScreen;
import com.sudokugame.game.screen.MenuScreen;

public class Sudoku extends Game {

    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);

    private AssetManager assetManager;
    private SpriteBatch batch;

    public Music menuMusic;
    public Music gameMusic;

    public Sound clickSound;


    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);

        assetManager.load(AssetDescriptors.MENU_MUSIC);
        assetManager.load(AssetDescriptors.GAME_MUSIC);
        assetManager.load(AssetDescriptors.CLICK_SOUND);
        assetManager.finishLoading();

        menuMusic = assetManager.get(AssetDescriptors.MENU_MUSIC);
        gameMusic = assetManager.get(AssetDescriptors.GAME_MUSIC);
        clickSound = assetManager.get(AssetDescriptors.CLICK_SOUND);
        batch = new SpriteBatch();

        setScreen(new IntroScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        assetManager.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void switchMusic(Music newMusic) {
        // Determine which music is to be paused
        Music musicToPause = (newMusic == menuMusic) ? gameMusic : menuMusic;

        // Pause the other music if it's playing
        if (musicToPause.isPlaying()) {
            musicToPause.pause();
        }

        // Play the new music if it's not already playing
        if (!newMusic.isPlaying()) {
            // Set volume from preferences
            Preferences prefs = Gdx.app.getPreferences(GameConfig.PREFS_NAME);
            float volume = prefs.getFloat(GameConfig.PREFS_MUSIC_VOLUME, 0.5f);
            newMusic.setVolume(volume);
            newMusic.setLooping(true);
            newMusic.play();
        }

        log.debug("Music switched to " + (newMusic == menuMusic ? "Menu Music" : "Game Music"));
    }


    public void setMusicVolume(float volume) {
        menuMusic.setVolume(volume);
        gameMusic.setVolume(volume);
    }

    public void playClickSound() {
        Preferences prefs = Gdx.app.getPreferences(GameConfig.PREFS_NAME);
        boolean SFxEnabled = prefs.getBoolean(GameConfig.PREFS_SFX_ENABLED, true);
        if (SFxEnabled)
            clickSound.play();
    }
}
