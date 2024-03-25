package com.lottoland.rockpaperscissors.game.models;

public record Result(Integer roundNo, Choice player1Choice, Choice player2Choice, RoundOutcome outcome) {

    @Override
    public String toString() {
        return String.format("Round %s, Player 1 choose %s, Player 2 choose %s, Result: %s",
            roundNo, player1Choice, player2Choice, outcome);
    }
}
