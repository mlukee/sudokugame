package com.sudokugame.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sudokugame.game.Sudoku;
import com.sudokugame.game.assets.AssetDescriptors;
import com.sudokugame.game.config.GameConfig;

public class SettingsScreen extends ScreenAdapter {
    private final Sudoku game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;
    private Skin skin;

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        assetManager.load(AssetDescriptors.UI_SKIN);
        assetManager.finishLoading();

        skin = assetManager.get(AssetDescriptors.UI_SKIN);

        stage.addActor(createUi());
        showBackButton(stage);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1); // Medium gray background
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

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public SettingsScreen(Sudoku game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    private Actor createUi() {
        Table table = new Table();
        table.setFillParent(true);
        table.pad(10);

        // Title label
        Label titleLabel = new Label("Settings", skin, "title");
        table.add(titleLabel).padBottom(20).center().row();

        // Access Preferences
        final Preferences prefs = Gdx.app.getPreferences(GameConfig.PREFS_NAME);

        // Time setting checkbox
        final CheckBox timeCheckbox = new CheckBox(" Time", skin);
        timeCheckbox.setChecked(prefs.getBoolean(GameConfig.PREFS_TIME, false)); // Set checked state
        timeCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                prefs.putBoolean(GameConfig.PREFS_TIME, timeCheckbox.isChecked());
                prefs.flush();
            }
        });
        table.add(timeCheckbox).padBottom(10).left().row();

        // Mistakes setting checkbox
        final CheckBox mistakesCheckbox = new CheckBox(" Mistakes", skin);
        mistakesCheckbox.setChecked(prefs.getBoolean(GameConfig.PREFS_MISTAKES, false)); // Set checked state
        mistakesCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                prefs.putBoolean(GameConfig.PREFS_MISTAKES, mistakesCheckbox.isChecked());
                prefs.flush();
            }
        });
        table.add(mistakesCheckbox).padBottom(10).left().row();

        return table;
    }



    public void showBackButton(Stage stage) {
        TextButton backButton = new TextButton("Back", skin);
        backButton.setPosition(10, stage.getHeight()-backButton.getHeight()-10);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
        stage.addActor(backButton);
    }
}
