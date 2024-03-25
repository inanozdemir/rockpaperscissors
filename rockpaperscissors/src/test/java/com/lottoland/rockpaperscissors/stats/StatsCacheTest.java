package com.lottoland.rockpaperscissors.stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.lottoland.rockpaperscissors.stats.cache.StatsCache;
import com.lottoland.rockpaperscissors.stats.models.StatsModel;

public class StatsCacheTest {

    private StatsCache statsCache;

    @BeforeEach
    void setUp() {
        statsCache = new StatsCache();
    }

    @Test
    void testInitialStatsAreZero() {
        StatsModel stats = statsCache.getStatsModel();
        assertEquals(0, stats.totalRoundsPlayed());
        assertEquals(0, stats.totalWinsForFirstPlayer());
        assertEquals(0, stats.totalWinsForSecondPlayer());
        assertEquals(0, stats.totalDraws());
    }

    @Test
    void testUpdateStatsWithFirstPlayerWin() {
        statsCache.updateStats(true, false, false);
        StatsModel stats = statsCache.getStatsModel();
        assertEquals(1, stats.totalRoundsPlayed());
        assertEquals(1, stats.totalWinsForFirstPlayer());
        assertEquals(0, stats.totalWinsForSecondPlayer());
        assertEquals(0, stats.totalDraws());
    }

    @Test
    void testUpdateStatsWithSecondPlayerWin() {
        statsCache.updateStats(false, true, false);
        StatsModel stats = statsCache.getStatsModel();
        assertEquals(1, stats.totalRoundsPlayed());
        assertEquals(0, stats.totalWinsForFirstPlayer());
        assertEquals(1, stats.totalWinsForSecondPlayer());
        assertEquals(0, stats.totalDraws());
    }

    @Test
    void testUpdateStatsWithDraw() {
        statsCache.updateStats(false, false, true);
        StatsModel stats = statsCache.getStatsModel();
        assertEquals(1, stats.totalRoundsPlayed());
        assertEquals(0, stats.totalWinsForFirstPlayer());
        assertEquals(0, stats.totalWinsForSecondPlayer());
        assertEquals(1, stats.totalDraws());
    }

    @Test
    void testMultipleUpdatesAccumulateStatsCorrectly() {
        // Simulate 2 wins for the first player, 1 win for the second player, and 1 draw
        statsCache.updateStats(true, false, false); // First player win
        statsCache.updateStats(true, false, false); // First player win
        statsCache.updateStats(false, true, false); // Second player win
        statsCache.updateStats(false, false, true); // Draw

        StatsModel stats = statsCache.getStatsModel();
        assertEquals(4, stats.totalRoundsPlayed());
        assertEquals(2, stats.totalWinsForFirstPlayer());
        assertEquals(1, stats.totalWinsForSecondPlayer());
        assertEquals(1, stats.totalDraws());
    }

    @Test
    void testConcurrentUpdatesToStatsCache() throws InterruptedException {
        int iterations = 1000; // Number of iterations per thread
        Thread firstPlayerWins = new Thread(() -> {
            for (int i = 0; i < iterations; i++) {
                statsCache.updateStats(true, false, false);
            }
        });

        Thread secondPlayerWins = new Thread(() -> {
            for (int i = 0; i < iterations; i++) {
                statsCache.updateStats(false, true, false);
            }
        });

        Thread draws = new Thread(() -> {
            for (int i = 0; i < iterations; i++) {
                statsCache.updateStats(false, false, true);
            }
        });

        // Start threads
        firstPlayerWins.start();
        secondPlayerWins.start();
        draws.start();

        // Wait for all threads to finish
        firstPlayerWins.join();
        secondPlayerWins.join();
        draws.join();

        // Verify final state
        StatsModel stats = statsCache.getStatsModel();
        assertEquals(iterations * 3, stats.totalRoundsPlayed());
        assertEquals(iterations, stats.totalWinsForFirstPlayer());
        assertEquals(iterations, stats.totalWinsForSecondPlayer());
        assertEquals(iterations, stats.totalDraws());
    }

}

