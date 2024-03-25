package com.lottoland.rockpaperscissors.strategy.choice;

import com.lottoland.rockpaperscissors.game.models.Choice;

public class RockOnlyChoiceStrategy implements ChoiceStrategy {

    @Override
    public Choice makeChoice() {
        return Choice.ROCK;
    }
}
