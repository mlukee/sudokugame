package com.sudokugame.game.common;


public class Player {
    private String name;

    private String time;

    private GameDifficulty gameDifficulty;

    public Player() {
    }

    public Player(String name, String time, GameDifficulty gameDifficulty) {
        this.name = name;
        this.time = time;
        this.gameDifficulty = gameDifficulty;
    }

    // Getters and setters for name, time, and gameDifficulty
    public String getName() {
        return name;
    }
    public String getTime() {
        return time;
    }
    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }
    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", time=" + time +
                ", gameDifficulty=" + gameDifficulty +
                '}';
    }
}

