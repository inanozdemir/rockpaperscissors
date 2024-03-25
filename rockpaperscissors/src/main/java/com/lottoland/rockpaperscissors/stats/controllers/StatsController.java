package com.lottoland.rockpaperscissors.stats.controllers;

import com.lottoland.rockpaperscissors.stats.models.StatsModel;
import com.lottoland.rockpaperscissors.stats.service.StatsService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping
    public ResponseEntity<StatsModel> getStats() {
        var gameStats = statsService.getStats();
        return ResponseEntity.ok().body(gameStats);
    }

}
