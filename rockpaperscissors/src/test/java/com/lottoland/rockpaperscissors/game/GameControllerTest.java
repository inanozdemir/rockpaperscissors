package com.lottoland.rockpaperscissors.game;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lottoland.rockpaperscissors.game.controllers.GameController;
import com.lottoland.rockpaperscissors.game.models.Choice;
import com.lottoland.rockpaperscissors.game.models.Result;
import com.lottoland.rockpaperscissors.game.models.RoundOutcome;
import com.lottoland.rockpaperscissors.game.services.GameService;
import com.lottoland.rockpaperscissors.game.services.GameSessionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import jakarta.servlet.http.HttpSession;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private GameSessionService gameSessionService;

    private HttpSession httpSession;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        httpSession = new MockHttpSession();
    }

    @Test
    void playRound_ShouldReturnResult() throws Exception {
        Result expected = new Result(1, Choice.ROCK, Choice.SCISSORS, RoundOutcome.PLAYER_1_WINS);
        given(gameService.playWithPlayersRandomAndRockStrategy(any(HttpSession.class))).willReturn(expected);

        mockMvc.perform(get("/game/play/random/rock").session((MockHttpSession) httpSession))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void restartGame_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/game/restart").session((MockHttpSession) httpSession))
                .andExpect(status().isOk());
    }

    @Test
    void getRounds_ShouldReturnListOfResults() throws Exception {
        List<Result> expectedResults = List.of(
                new Result(1, Choice.ROCK, Choice.SCISSORS, RoundOutcome.PLAYER_1_WINS)
        );
        given(gameSessionService.getResultsFromSession(any(HttpSession.class))).willReturn(expectedResults);

        mockMvc.perform(get("/game/rounds").session((MockHttpSession) httpSession))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResults)));
    }
}

