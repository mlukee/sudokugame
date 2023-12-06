package com.sudokugame.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sudokugame.game.Sudoku;
import com.sudokugame.game.assets.AssetDescriptors;
import com.sudokugame.game.config.GameConfig;
import com.sudokugame.game.gameLogic.Generator;
import com.sudokugame.game.gameLogic.Grid;
import com.sudokugame.game.gameLogic.Solver;


public class GameScreen extends ScreenAdapter {
    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);

    private final Sudoku game;
    private final AssetManager assetManager;

    private Generator sudokuGenerator;
    private Grid grid;
    private Grid solvedGrid;
    private Solver solver;

    private Stage gameplayStage;
    private Stage uiStage;

    private Viewport viewport;
    private Viewport hudViewport;

    private Skin skin;

    public GameScreen(Sudoku game) {
        this.game = game;
        assetManager = game.getAssetManager();

        sudokuGenerator = new Generator();
        grid = sudokuGenerator.generate(42);

        log.debug("Generated grid: " + grid.toString());
        try {
        } catch (Exception e) {
            log.debug("Error: " + e.getMessage());
        }
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);

        gameplayStage = new Stage(viewport, game.getBatch());
        uiStage = new Stage(hudViewport, game.getBatch());

        skin = assetManager.get(AssetDescriptors.UI_SKIN);
        gameplayStage.addActor(createGrid(9, 9, 35));

        showBackButton(uiStage);

        Gdx.input.setInputProcessor(new InputMultiplexer(gameplayStage, uiStage));

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1);

        //update
        gameplayStage.act(delta);
        uiStage.act(delta);

        //draw
        gameplayStage.draw();
        uiStage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        gameplayStage.dispose();
        uiStage.dispose();
    }

    private Actor createGrid(int rows, int cols, int cellSize) {
        Table table = new Table();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Grid.Cell cell = grid.getCell(row, col);
                int value = cell.getValue();

                table.add(createCell(cellSize, value, skin)).pad(1).size(cellSize, cellSize);
            }
            table.row();
        }

        table.pack();
        table.setPosition(
                (GameConfig.WORLD_WIDTH - table.getWidth()) / 2,
                ((GameConfig.WORLD_HEIGHT - table.getHeight()) / 2) - 50
        );

        return table;
    }

    private Actor createCell(int cellSize, int cellValue, Skin skin) {
        String cellText = cellValue == 0 ? "" : String.valueOf(cellValue);
        TextButton cellButton = new TextButton(cellText, skin, "number");
        cellButton.getLabel().setAlignment(Align.center);
        return cellButton;
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
