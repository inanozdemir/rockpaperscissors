package com.lottoland.rockpaperscissors.stats.cache;

import java.util.concurrent.atomic.AtomicInteger;

import com.lottoland.rockpaperscissors.stats.models.StatsModel;

import org.springframework.stereotype.Component;

@Component
public class StatsCache {
    private final AtomicInteger totalRoundsPlayed = new AtomicInteger(0);

    private final AtomicInteger totalWinsForFirstPlayer = new AtomicInteger(0);

    private final AtomicInteger totalWinsForSecondPlayer = new AtomicInteger(0);

    private final AtomicInteger totalDraws = new AtomicInteger(0);

    public synchronized void updateStats(boolean isFirstPlayerWin, boolean isSecondPlayerWin, boolean isDraw) {
        if (isFirstPlayerWin) {
            totalWinsForFirstPlayer.incrementAndGet();
        } else if (isSecondPlayerWin) {
            totalWinsForSecondPlayer.incrementAndGet();
        } else if (isDraw) {
            totalDraws.incrementAndGet();
        }
        totalRoundsPlayed.incrementAndGet();
    }

    public StatsModel getStatsModel() {
        return new StatsModel(totalRoundsPlayed.get(), totalWinsForFirstPlayer.get(), totalWinsForSecondPlayer.get(),
                totalDraws.get());
    }

    @Override
    public String toString() {
        return String.format("TotalRounds: %s, TotalWinsForFirstPlayer: %s, TotalWinsForSecondPlayer: %s, TotalDraws: %s",
                totalRoundsPlayed.get(), totalWinsForFirstPlayer.get(), totalWinsForSecondPlayer.get(), totalDraws.get());
    }

}

