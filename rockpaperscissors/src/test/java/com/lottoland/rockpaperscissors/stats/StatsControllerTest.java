package com.lottoland.rockpaperscissors.stats;

import static org.mockito.BDDMockito.given;

import com.lottoland.rockpaperscissors.stats.controllers.StatsController;
import com.lottoland.rockpaperscissors.stats.models.StatsModel;
import com.lottoland.rockpaperscissors.stats.service.StatsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StatsController.class)
public class StatsControllerTest {

    @MockBean
    private StatsService statsService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        StatsModel mockStats = new StatsModel(10, 4, 3, 3);
        given(statsService.getStats()).willReturn(mockStats);
    }

    @Test
    public void getStats_ReturnsStatsModel() throws Exception {
        mockMvc.perform(get("/stats")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"totalRoundsPlayed\":10,\"totalWinsForFirstPlayer\":4,\"totalWinsForSecondPlayer\":3,\"totalDraws\":3}"));
    }
}
