package com.lottoland.rockpaperscissors.strategy.choice;

import java.util.*;

import com.lottoland.rockpaperscissors.game.models.Choice;

public class RandomChoiceStrategy implements ChoiceStrategy {
    private static final Random random = new Random();
    private final Choice[] choices = Choice.values();

    @Override
    public Choice makeChoice() {
        return choices[random.nextInt(choices.length)];
    }
}
