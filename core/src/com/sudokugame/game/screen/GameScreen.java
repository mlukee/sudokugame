package com.sudokugame.game.screen;

import static com.sudokugame.game.config.GameConfig.HUD_HEIGHT;
import static com.sudokugame.game.config.GameConfig.HUD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
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
import com.sudokugame.game.common.GameDifficulty;
import com.sudokugame.game.common.GameManager;
import com.sudokugame.game.config.GameConfig;
import com.sudokugame.game.gameLogic.Generator;
import com.sudokugame.game.gameLogic.Grid;
import com.sudokugame.game.gameLogic.Solver;


public class GameScreen extends ScreenAdapter {
    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);

    private String difficulty;
    private final Sudoku game;
    private GameManager gameManager;
    private final AssetManager assetManager;

    private Generator sudokuGenerator;
    private Grid grid;
    private Grid solvedGrid;
    private Solver solver;

    private Stage gameplayStage;
    private Stage uiStage;

    private Viewport viewport;
    private Viewport hudViewport;

    private long time;

    private Skin skin;
    private Label timerLabel;

    private Table gridTable;
    private Table labelsTable;

    private TextButton selectedNumberButton;

    public GameScreen(Sudoku game, String difficulty) {
        this.game = game;
        assetManager = game.getAssetManager();
        this.difficulty = difficulty;
        gameManager = new GameManager();

        //get GameDifficulty from difficulty
        switch (difficulty) {
            case "Medium":
                gameManager.gameDifficulty = GameDifficulty.MEDIUM;
                break;
            case "Hard":
                gameManager.gameDifficulty = GameDifficulty.HARD;
                break;
            default:
                gameManager.gameDifficulty = GameDifficulty.EASY;
                break;
        }


        game.switchMusic(game.gameMusic);

        sudokuGenerator = new Generator();
        int numberOfEmptyCells = gameManager.getInitialNumbersCount(gameManager.gameDifficulty);
        grid = sudokuGenerator.generate(numberOfEmptyCells);

        log.debug("Generated grid: " + grid.toString());
        try {
        } catch (Exception e) {
            log.debug("Error: " + e.getMessage());
        }
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        hudViewport = new FitViewport(HUD_WIDTH, GameConfig.HUD_HEIGHT);

        gameplayStage = new Stage(viewport, game.getBatch());
        uiStage = new Stage(hudViewport, game.getBatch());

        skin = assetManager.get(AssetDescriptors.UI_SKIN);

        gridTable = createGrid(9, 9, 30); // Assuming 'cellSize' is defined
        gridTable.pack();
        gridTable.setPosition(
                (GameConfig.WORLD_WIDTH - gridTable.getWidth()) / 2,
                (GameConfig.WORLD_HEIGHT - gridTable.getHeight()) / 2
        );
        gameplayStage.addActor(gridTable);

        // Create the labels table
        labelsTable = createLabels();
        labelsTable.pack();

        // Position the labels table
        labelsTable.setPosition(HUD_WIDTH / 2 - labelsTable.getWidth() / 2, HUD_HEIGHT - 5 * labelsTable.getHeight() - 10);
        uiStage.addActor(labelsTable);
        showBackButton(uiStage);

        Gdx.input.setInputProcessor(new InputMultiplexer(gameplayStage, uiStage));
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    public String formatTime(long seconds) {
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%d:%02d", minutes, remainingSeconds);
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1);

        time = gameManager.getElapsedTime();
        timerLabel.setText("Time: " + formatTime(time));

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

    private Table createLabels() {
        Table table = new Table();
        table.defaults().pad(0);
        table.setDebug(false); // Set to true to debug the layout

        // Mistakes label, left-aligned
        Label mistakesLabel = new Label("Mistakes: 0/3", skin, "white");

        // Timer label, right-aligned
        timerLabel = new Label("Time: " + formatTime(time), skin, "white");

        // Add both labels to the table, aligning them on opposite sides
        table.add(mistakesLabel).fill().left().padRight(30);
        table.add(timerLabel).fill().right();

        table.setPosition(uiStage.getWidth() / 2, uiStage.getHeight() / 2);
        return table;
    }


    private Table createGrid(int rows, int cols, int cellSize) {
        Table table = new Table();
        table.defaults().pad(0);
        table.setDebug(false); // Set to true to debug the layout


        // Creating the grid cells
        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= cols; col++) {
                Grid.Cell cell = grid.getCell(row - 1, col - 1);
                int value = cell.getValue();
                Cell<Actor> cellActor = table.add(createCell(cellSize, value, skin, row, col)).size(cellSize, cellSize);

                if (col % 3 == 0 && col != cols) cellActor.padRight(5);
                if (row % 3 == 0 && row != rows) cellActor.padBottom(5);
            }
            table.row(); // Move to the next row after each grid row
        }

        table.row().padTop(10); // Add some padding after the grid (10 pixels
        // Add numbers row below the grid
        for (int i = 1; i <= 9; i++) {
            final int numberValue = i;
            TextButton numberButton = createNumberButton(numberValue, cellSize);
            table.add(numberButton).size(cellSize, cellSize);
        }
        return table;
    }


    private TextButton createNumberButton(final int number, int cellSize) {
        final TextButton numberButton = new TextButton(String.valueOf(number), skin, "number");
        numberButton.getLabel().setAlignment(Align.center);
        numberButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.playClickSound();
                gameManager.setSelectedNumber(number);
                log.debug("Selected number: " + gameManager.getSelectedNumber());

                // Revert the previously selected button to the default color
                if (selectedNumberButton != null && selectedNumberButton != numberButton) {
                    selectedNumberButton.setStyle(skin.get("number", TextButton.TextButtonStyle.class));
                }

                // Change the current button color to WHITE to show it's selected
                numberButton.setStyle(skin.get("emphasis", TextButton.TextButtonStyle.class));

                // Keep a reference to the currently selected button
                selectedNumberButton = numberButton;
            }
        });
        return numberButton;
    }


    private Actor createCell(int cellSize, int cellValue, Skin skin, final int row, final int col) {
        String cellText = cellValue == 0 ? "" : String.valueOf(cellValue);
        final TextButton cellButton = new TextButton(cellText, skin, "number");
        cellButton.getLabel().setAlignment(Align.center);
        cellButton.setTransform(true);
        cellButton.setSize(cellSize, cellSize);
        cellButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.playClickSound();
                if (gameManager.getSelectedNumber() != -1) {
                    // Update the cell value
                    grid.getCell(row-1, col-1).setValue(gameManager.getSelectedNumber());
                    log.debug("Updated grid: " + grid.toString());
                    cellButton.getLabel().setText(String.valueOf(gameManager.getSelectedNumber()));
                }
            }
        });
        return cellButton;
    }


    public void showBackButton(Stage stage) {
        // Back button
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.playClickSound();
                game.setScreen(new MenuScreen(game));
            }
        });

        // Position the back button in the top left corner of the screen
        backButton.setPosition(10, stage.getHeight() - backButton.getHeight() - 10);

        // Add the back button to the stage
        stage.addActor(backButton);
    }

}
