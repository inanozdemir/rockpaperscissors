package com.lottoland.rockpaperscissors.game.services;

import com.lottoland.rockpaperscissors.factory.player.PlayerFactory;
import com.lottoland.rockpaperscissors.factory.player.Player;
import com.lottoland.rockpaperscissors.game.models.Choice;
import com.lottoland.rockpaperscissors.game.models.PlayerChoice;
import com.lottoland.rockpaperscissors.game.models.Result;
import com.lottoland.rockpaperscissors.game.models.RoundOutcome;
import com.lottoland.rockpaperscissors.stats.service.StatsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpSession;

@Service
public class GameService {

    @Autowired
    private final GameSessionService gameSessionService;

    @Autowired
    private final StatsService statsService;

    public GameService(GameSessionService gameSessionService, StatsService statsService) {
        this.gameSessionService = gameSessionService;
        this.statsService = statsService;
    }

    public Result playP1vsComp(HttpSession httpSession, PlayerChoice playerChoice) {
        Player playerComp = PlayerFactory.createRandomPlayer();
        Player player = PlayerFactory.createPlayer(Choice.fromString(playerChoice.getPlayerChoice()));

        var result = playRound(httpSession, player, playerComp);
        statsService.updateGameStatsCache(result);
        return result;
    }

    public Result playWithPlayersRandomAndRockStrategy(HttpSession httpSession) {
        Player player1 = PlayerFactory.createRandomPlayer();
        Player player2 = PlayerFactory.createRockOnlyPlayer();
        var result = playRound(httpSession, player1, player2);
        statsService.updateGameStatsCache(result);
        return result;
    }

    public Result playRound(HttpSession httpSession, Player player1, Player player2) {
        validatePlayer(player1);
        validatePlayer(player2);

        return calculateResult(httpSession, player1.makeChoice(), player2.makeChoice());
    }

    public Result calculateResult(HttpSession httpSession, Choice player1Choice, Choice player2Choice) {
        validateChoice(player1Choice);
        validateChoice(player2Choice);

        RoundOutcome outcome = determineOutcome(player1Choice, player2Choice);
        return new Result(gameSessionService.getNextRoundNumber(httpSession), player1Choice, player2Choice, outcome);
    }

    private RoundOutcome determineOutcome(Choice player1Choice, Choice player2Choice) {
        if (player1Choice == player2Choice) {
            return RoundOutcome.DRAW;
        }

        if (isPlayer1Winner(player1Choice, player2Choice)) {
            return RoundOutcome.PLAYER_1_WINS;
        } else {
            return RoundOutcome.PLAYER_2_WINS;
        }
    }

    public boolean isPlayer1Winner(Choice player1Choice, Choice player2Choice) {
        return (player1Choice == Choice.ROCK && player2Choice == Choice.SCISSORS) ||
                (player1Choice == Choice.SCISSORS && player2Choice == Choice.PAPER) ||
                (player1Choice == Choice.PAPER && player2Choice == Choice.ROCK);
    }

    // Validations
    public void validateChoice(Choice playerChoice) {
        if (playerChoice == null) {
            throw new IllegalArgumentException("Player choices cannot be null");
        }
    }

    public void validatePlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
    }

}


