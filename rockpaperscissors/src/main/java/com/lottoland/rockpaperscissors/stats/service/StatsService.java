package com.lottoland.rockpaperscissors.stats.service;

import com.lottoland.rockpaperscissors.game.models.Result;
import com.lottoland.rockpaperscissors.stats.cache.StatsCache;
import com.lottoland.rockpaperscissors.stats.models.StatsModel;

import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final StatsCache statsCache;

    public StatsService(StatsCache statsCache) {
        this.statsCache = statsCache;
    }

    public void updateGameStatsCache(Result resultToBeUpdated) {
        boolean isFirstPlayerWin = false, isSecondPlayerWin = false, isDraw = false;

        switch (resultToBeUpdated.outcome()) {
        case DRAW -> isDraw = true;
        case PLAYER_1_WINS -> isFirstPlayerWin = true;
        case PLAYER_2_WINS -> isSecondPlayerWin = true;
        }

        statsCache.updateStats(isFirstPlayerWin, isSecondPlayerWin, isDraw);
    }

    public StatsModel getStats() {
        return statsCache.getStatsModel();
    }

}
