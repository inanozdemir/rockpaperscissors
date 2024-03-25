package com.lottoland.rockpaperscissors.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.lottoland.rockpaperscissors.factory.player.Player;
import com.lottoland.rockpaperscissors.game.models.Choice;
import com.lottoland.rockpaperscissors.game.models.Result;
import com.lottoland.rockpaperscissors.game.models.RoundOutcome;
import com.lottoland.rockpaperscissors.game.services.GameService;
import com.lottoland.rockpaperscissors.game.services.GameSessionService;
import com.lottoland.rockpaperscissors.stats.service.StatsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameSessionService gameSessionService;

    @Mock
    private StatsService statsService;

    @Mock
    private MockHttpSession httpSession;

    private GameService gameService;

    private Player player1;
    private Player player2;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        httpSession = new MockHttpSession();
        gameService = new GameService(gameSessionService, statsService);
    }

    @Test
    void testPlayWithPlayersRandomAndRockStrategy() {
        when(gameSessionService.getNextRoundNumber(httpSession)).thenReturn(1);
        doNothing().when(statsService).updateGameStatsCache(any(Result.class));

        Result result = gameService.playWithPlayersRandomAndRockStrategy(httpSession);

        assertNotNull(result);
        verify(statsService).updateGameStatsCache(any());
        verify(gameSessionService).getNextRoundNumber(httpSession);
    }

    @Test
    void testCalculateResultRockVsScissors() {
        // Execute
        Result result = gameService.calculateResult(httpSession, Choice.ROCK, Choice.SCISSORS);

        // Verify
        assertEquals(RoundOutcome.PLAYER_1_WINS, result.outcome());
    }

    @Test
    void testCalculateResultPaperVsRock() {
        Result result = gameService.calculateResult(httpSession, Choice.PAPER, Choice.ROCK);

        assertEquals(RoundOutcome.PLAYER_1_WINS, result.outcome());
    }

    @Test
    void testCalculateResultScissorsVsPaper() {
        Result result = gameService.calculateResult(httpSession, Choice.SCISSORS, Choice.PAPER);

        assertEquals(RoundOutcome.PLAYER_1_WINS, result.outcome());
    }

    @Test
    void testCalculateResultWithDraw() {
        // Test for each draw scenario
        for (Choice choice : Choice.values()) {
            Result result = gameService.calculateResult(httpSession, choice, choice);
            assertEquals(RoundOutcome.DRAW, result.outcome(), "Failed draw test for choice: " + choice);
        }
    }

    @Test
    void testCalculateWithNullPlayer1Choice() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.calculateResult(httpSession, null, Choice.PAPER);
        });

        String expectedMessage = "Player choices cannot be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCalculateWithNullPlayer2Choice() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.calculateResult(httpSession, Choice.PAPER, null);
        });

        String expectedMessage = "Player choices cannot be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testPlayRoundWithNullPlayer1() {
        player2 = mock(Player.class);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            gameService.playRound(httpSession, null, player2);
        });

        assertTrue(thrown.getMessage().contains("Player cannot be null"));
    }

    @Test
    void testPlayRoundWithNullPlayer2() {
        player1 = mock(Player.class);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            gameService.playRound(httpSession, player1, null);
        });

        assertTrue(thrown.getMessage().contains("Player cannot be null"));
    }
}