package com.sudokugame.game.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
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

    private AssetDescriptors() {
    }
}
