package com.lottoland.rockpaperscissors.game.controllers;

import java.util.List;

import com.lottoland.rockpaperscissors.game.models.PlayerChoice;
import com.lottoland.rockpaperscissors.game.services.GameService;
import com.lottoland.rockpaperscissors.game.services.GameSessionService;
import com.lottoland.rockpaperscissors.game.models.Result;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    private final GameSessionService gameSessionService;

    public GameController(GameService gameService, GameSessionService gameSessionService) {
        this.gameService = gameService;
        this.gameSessionService = gameSessionService;
    }

    @PostMapping("/play/p1")
    public ResponseEntity<Result> playP1vsComp(HttpSession session, @RequestBody PlayerChoice playerChoice) {
        var result = gameService.playP1vsComp(session, playerChoice);
        gameSessionService.addResultToSession(session, result);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/play/random/rock")
    public ResponseEntity<Result> playRound(HttpSession session) {
        var result = gameService.playWithPlayersRandomAndRockStrategy(session);
        gameSessionService.addResultToSession(session, result);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/restart")
    public ResponseEntity restartGame(HttpSession session) {
        gameSessionService.resetSessionResults(session);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/rounds")
    public ResponseEntity<List<Result>> getRounds(HttpSession session) {
        var results = gameSessionService.getResultsFromSession(session);
        return ResponseEntity.ok().body(results);
    }
}
