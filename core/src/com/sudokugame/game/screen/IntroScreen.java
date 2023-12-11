package com.sudokugame.game.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sudokugame.game.Sudoku;
import com.sudokugame.game.assets.AssetDescriptors;
import com.sudokugame.game.assets.RegionNames;
import com.sudokugame.game.config.GameConfig;


public class IntroScreen extends ScreenAdapter {
    public static final float DURATION = 4.2f;

    private final Sudoku game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private TextureAtlas gamePlayAtlas;

    private float duration = 0f;

    private Stage stage;

    public IntroScreen(Sudoku game) {
        this.game = game;
        assetManager = game.getAssetManager();
        game.switchMusic(game.menuMusic);
    }


    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        assetManager.load(AssetDescriptors.GAME_PLAY);
        assetManager.load(AssetDescriptors.MENU_MUSIC);
        assetManager.finishLoading();

        gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        stage.addActor(createAnimation());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1); // Medium gray background
        duration += delta;
        if (duration >= DURATION) {
            game.setScreen(new MenuScreen(game));
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void hide() {
        dispose();
    }

    private Actor createAnimation() {
        Image logo = new Image(gamePlayAtlas.findRegion(RegionNames.LOGO));

        // Adjust the start position based on the new size
        float startX = viewport.getWorldWidth() / 2f - logo.getWidth() / 2f;
        float centerY = (viewport.getWorldHeight() - logo.getHeight()) / 2f;

        // Set the initial position
        logo.setPosition(startX, centerY);
        logo.setOrigin(Align.center);
        logo.setScale(0, 0);

        // Define actions for the logo
        logo.addAction(
                Actions.sequence(
                        Actions.scaleTo(1, 1, 1.5f),

                        Actions.rotateBy(180f, 0.5f),
                        Actions.rotateBy(-532f, 0.5f),

                        // Delay before scaling down
                        Actions.delay(0.5f),

                        // Scale back down to 0
                        Actions.scaleTo(0, 0, 1f),

                        // Remove actor after animation
                        Actions.removeActor()
                )
        );


        return logo;
    }


}
