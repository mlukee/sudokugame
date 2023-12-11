package com.sudokugame.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sudokugame.game.Sudoku;
import com.sudokugame.game.assets.AssetDescriptors;
import com.sudokugame.game.config.GameConfig;

public class LeaderBoardScreen extends ScreenAdapter {
    private final Sudoku game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;
    private Skin skin;

    public LeaderBoardScreen(Sudoku game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

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
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1); // Medium gray background
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private Actor createUi() {
        // Main table for layout
        Table table = new Table();
        table.setFillParent(true);
        table.pad(10);

        // Title label
        Label titleLabel = new Label("Leaderboard", skin, "title");
        table.add(titleLabel).padBottom(20).center().row();

        // Leaderboard table for usernames and times
        Table leaderboardTable = new Table(skin);

        // Populate the leaderboard table
        for (int i = 1; i <= 30; i++) {
            String userName = "User" + i;
            int timeInSeconds = (int) (Math.random() * (180 - 30)) + 30;
            String formattedTime = String.format("%d:%02d", timeInSeconds / 60, timeInSeconds % 60);

            // Username label, aligned to the left
            Label nameLabel = new Label(userName, skin);
            nameLabel.setAlignment(Align.left);

            // Time label, aligned to the right
            Label timeLabel = new Label(formattedTime, skin);
            timeLabel.setAlignment(Align.right);

            // Add labels to the leaderboard table
            leaderboardTable.add(nameLabel).expandX().align(Align.left);
            leaderboardTable.add(timeLabel).expandX().align(Align.right).row();
        }

        // Wrap the leaderboard table in a scroll pane
        ScrollPane scrollPane = new ScrollPane(leaderboardTable, skin);
        scrollPane.setFadeScrollBars(false);

        // Add the scroll pane to the main table
        table.add(scrollPane).width(250).height(300).center().row();

        // Return the main table
        return table;
    }


    public void showBackButton(Stage stage) {
        // Back button
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Code to go back to the menu screen
                game.setScreen(new MenuScreen(game));
            }
        });

        // Position the back button in the top left corner of the screen
        backButton.setPosition(10, stage.getHeight() - backButton.getHeight() - 10);

        // Add the back button to the stage
        stage.addActor(backButton);
    }


}
