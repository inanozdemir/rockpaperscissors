package com.lottoland.rockpaperscissors.stats;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lottoland.rockpaperscissors.game.models.Choice;
import com.lottoland.rockpaperscissors.game.models.Result;
import com.lottoland.rockpaperscissors.game.models.RoundOutcome;
import com.lottoland.rockpaperscissors.stats.cache.StatsCache;
import com.lottoland.rockpaperscissors.stats.models.StatsModel;
import com.lottoland.rockpaperscissors.stats.service.StatsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

public class StatsServiceTest {
    private StatsCache statsCache;

    private StatsService statsService;

    @BeforeEach
    void setUp() {
        statsCache = mock(StatsCache.class);
        statsService = new StatsService(statsCache);
    }

    @Test
    void updateGameStatsCacheWithPlayer1Win() {
        Result result = new Result(1, Choice.ROCK, Choice.SCISSORS, RoundOutcome.PLAYER_1_WINS);

        statsService.updateGameStatsCache(result);

        verify(statsCache).updateStats(true, false, false);
    }

    @Test
    void updateGameStatsCacheWithPlayer2Win() {
        Result result = new Result(1, Choice.PAPER, Choice.SCISSORS, RoundOutcome.PLAYER_2_WINS);

        statsService.updateGameStatsCache(result);

        verify(statsCache).updateStats(false, true, false);
    }

    @Test
    void updateGameStatsCacheWithDraw() {
        Result result = new Result(1, Choice.ROCK, Choice.ROCK, RoundOutcome.DRAW);

        statsService.updateGameStatsCache(result);

        verify(statsCache).updateStats(false, false, true);
    }

    @Test
    void getStatsRetrievesAccurateStatsModel() {
        StatsModel expectedStatsModel = new StatsModel(10, 5, 3, 2);
        when(statsCache.getStatsModel()).thenReturn(expectedStatsModel);

        StatsModel actualStatsModel = statsService.getStats();

        assertEquals(expectedStatsModel, actualStatsModel);
        verify(statsCache).getStatsModel();
    }
}
