package com.sudokugame.game.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

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

    public String formatTime(long seconds) {
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%d:%02d", minutes, remainingSeconds);
    }

    // ... [Other GameManager methods]

    public void saveResult(String playerName, long time, GameDifficulty difficulty) {
        Array<Player> players = getPlayers();
        players.add(new Player(playerName, formatTime(time), difficulty));
        savePlayersToFile(players);
    }

    public Array<Player> getPlayers() {
        Json json = new Json();
        String playersJson = Gdx.files.local(RESULTS_FILE).readString();
        return json.fromJson(Array.class, Player.class, playersJson);
    }

    public void savePlayersToFile(Array<Player> players) {
        Json json = new Json();
        String playersJson = json.toJson(players, Array.class, Player.class);
        Gdx.files.local(RESULTS_FILE).writeString(playersJson, false);
    }

    public Array<Player> getPlayersByDifficulty(GameDifficulty difficulty) {
        Array<Player> filteredPlayers = new Array<>();
        Array<Player> players = getPlayers();

        for (Player player : players) {
            if (player.getGameDifficulty() == difficulty) {
                filteredPlayers.add(player);
            }
        }

        return filteredPlayers;
    }
}

