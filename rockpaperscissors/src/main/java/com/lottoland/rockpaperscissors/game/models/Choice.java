package com.lottoland.rockpaperscissors.game.models;

public enum Choice {
    ROCK, PAPER, SCISSORS;

    public static Choice fromString(String choice) {
        return Enum.valueOf(Choice.class, choice);
    }
}
