package com.lottoland.rockpaperscissors.game;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.lottoland.rockpaperscissors.game.models.Choice;
import com.lottoland.rockpaperscissors.game.models.Result;
import com.lottoland.rockpaperscissors.game.models.RoundOutcome;
import com.lottoland.rockpaperscissors.game.services.GameSessionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)

public class GameSessionServiceTest {
    private static final String RESULTS_SESSION_KEY = "gameResults";

    @Mock
    private HttpSession session;

    private GameSessionService gameSessionService;

    @BeforeEach
    void setUp() {
        gameSessionService = new GameSessionService();
    }

    @Test
    void testAddResultToSession() {
        Result newResult = new Result(1, Choice.ROCK, Choice.SCISSORS, RoundOutcome.PLAYER_1_WINS);
        gameSessionService.addResultToSession(session, newResult);

        ArgumentCaptor<List<Result>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        verify(session).getAttribute(eq(RESULTS_SESSION_KEY));
        verify(session).setAttribute(eq(RESULTS_SESSION_KEY), argumentCaptor.capture());

        List<Result> capturedResults = argumentCaptor.getValue();
        assertNotNull(capturedResults);
        assertFalse(capturedResults.isEmpty());
        assertEquals(newResult, capturedResults.get(0));
    }

    @Test
    void testGetResultsFromSession() {
        List<Result> existingResults = List.of(new Result(1, Choice.PAPER, Choice.ROCK, RoundOutcome.PLAYER_1_WINS));

        when(session.getAttribute(RESULTS_SESSION_KEY)).thenReturn(existingResults);
        List<Result> results = gameSessionService.getResultsFromSession(session);

        assertNotNull(results);
        assertEquals(1, results.size());
    }

    @Test
    void testResetSessionResults() {
        gameSessionService.resetSessionResults(session);

        verify(session).removeAttribute(RESULTS_SESSION_KEY);
    }

    @Test
    void testGetNextRoundNumberWithEmptyResults() {
        when(session.getAttribute(RESULTS_SESSION_KEY)).thenReturn(null);

        assertEquals(1, gameSessionService.getNextRoundNumber(session));
    }

    @Test
    void testGetNextRoundNumberWithExistingResults() {
        List<Result> existingResults = List.of(new Result(1, Choice.ROCK, Choice.PAPER, RoundOutcome.PLAYER_2_WINS));
        when(session.getAttribute(RESULTS_SESSION_KEY)).thenReturn(existingResults);

        assertEquals(2, gameSessionService.getNextRoundNumber(session));
    }

}
