package com.sudokugame.game.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import netscape.javascript.JSObject;

public class AssetDescriptors {

    public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_PLAY, TextureAtlas.class);

    public static final AssetDescriptor<Skin> UI_SKIN =
            new AssetDescriptor<Skin>(AssetPaths.SKIN_JSON, Skin.class);

    public static final AssetDescriptor<TextureAtlas> UI_SKIN_ATLAS =
            new AssetDescriptor<TextureAtlas>(AssetPaths.SKIN_ATLAS, TextureAtlas.class);

    public static final AssetDescriptor<Music> GAME_MUSIC =
            new AssetDescriptor<Music>(AssetPaths.GAME_MUSIC, Music.class);

    public static final AssetDescriptor<Music> MENU_MUSIC =
            new AssetDescriptor<Music>(AssetPaths.MENU_MUSIC, Music.class);

    public static final AssetDescriptor<Sound> CLICK_SOUND =
            new AssetDescriptor<Sound>(AssetPaths.CLICK_SOUND, Sound.class);

    private AssetDescriptors() {
    }
}
