package com.sudokugame.game.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

public class GameManager {
    private static final String PREFS_NAME = "GamePreferences";
    private static final String RESULTS_FILE = "results.json";

    private final Preferences prefs;
    private int mistakes;
    private int selectedNumber;
    private long startTime;
    public GameDifficulty gameDifficulty;

    public GameManager() {
        prefs = Gdx.app.getPreferences(PREFS_NAME);
        mistakes = 0;
        startTime = System.currentTimeMillis();
        selectedNumber = -1; // Default to an invalid number to indicate no selection
        gameDifficulty = GameDifficulty.EASY;
    }

    public void incrementMistakes() {
        mistakes++;
        if (mistakes >= 3) {
            // Handle game over logic here
        }
    }

    public int getInitialNumbersCount(GameDifficulty difficulty) {
        int totalCells = 81;
        switch (difficulty) {
            case EASY:
                return totalCells - getRandomNumber(36, 49); // Returns the number of empty cells
            case MEDIUM:
                return totalCells - getRandomNumber(32, 36);
            case HARD:
                return totalCells - getRandomNumber(27, 31); // Adjusted for appropriate difficulty
            default:
                return totalCells - 38; // Default to EASY if difficulty is not set
        }
    }

    private int getRandomNumber(int min, int max) {
        return MathUtils.random(min, max);
    }

    public void setSelectedNumber(int number) {
        if (number >= 1 && number <= 9) {
            selectedNumber = number;
        }
    }

    public int getSelectedNumber() {
        return selectedNumber;
    }

    public long getElapsedTime() {
        return (System.currentTimeMillis() - startTime) / 1000; // Return time in seconds
    }

    public void saveResult(int score) {
        List<Integer> scores = getResults();
        saveResultsToFile(scores);
    }

    private List<Integer> getResults() {
        Json json = new Json();
        String resultsJson = Gdx.files.local(RESULTS_FILE).readString();
        return json.fromJson(List.class, resultsJson);
    }

    private void saveResultsToFile(List<Integer> results) {
        Json json = new Json();
        String resultsJson = json.toJson(results, ArrayList.class);
        Gdx.files.local(RESULTS_FILE).writeString(resultsJson, false);
    }

}

