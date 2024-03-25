package com.lottoland.rockpaperscissors.game.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.lottoland.rockpaperscissors.game.models.Result;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class GameSessionService {
    private static final String RESULTS_SESSION_KEY = "gameResults";

    @SuppressWarnings("unchecked")
    public void addResultToSession(HttpSession session, Result result) {
        List<Result> results = (List<Result>) session.getAttribute(RESULTS_SESSION_KEY);
        if (results == null) {
            results = new ArrayList<>();
        }
        results.add(result);
        session.setAttribute(RESULTS_SESSION_KEY, results);
    }

    @SuppressWarnings("unchecked")
    public List<Result> getResultsFromSession(HttpSession session) {
        List<Result> results = (List<Result>) session.getAttribute(RESULTS_SESSION_KEY);
        return Objects.requireNonNullElseGet(results, ArrayList::new);
    }

    public void resetSessionResults(HttpSession session) {
        session.removeAttribute(RESULTS_SESSION_KEY);
    }

    public int getNextRoundNumber(HttpSession session) {
        List<Result> results = (List<Result>) session.getAttribute(RESULTS_SESSION_KEY);

        if (results == null || results.isEmpty()) {
            return 1;
        } else {
            return results.get(results.size() - 1).roundNo() + 1;
        }
    }

}
