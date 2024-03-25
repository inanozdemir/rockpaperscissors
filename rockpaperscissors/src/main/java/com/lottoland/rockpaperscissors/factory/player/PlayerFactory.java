package com.lottoland.rockpaperscissors.factory.player;

import com.lottoland.rockpaperscissors.game.models.Choice;
import com.lottoland.rockpaperscissors.strategy.choice.RandomChoiceStrategy;
import com.lottoland.rockpaperscissors.strategy.choice.RockOnlyChoiceStrategy;

public class PlayerFactory {

    public static Player createRandomPlayer() {
        return new Player.PlayerBuilder()
            .setName("RandomPlayer")
            .setChoiceStrategy(new RandomChoiceStrategy())
            .build();
    }

    public static Player createRockOnlyPlayer() {
        return new Player.PlayerBuilder()
            .setName("RockPlayer")
            .setChoiceStrategy(new RockOnlyChoiceStrategy())
            .build();
    }

    public static Player createPlayer(Choice choice) {
        return new Player.PlayerBuilder()
                .setName("Player")
                .setChoiceStrategy(new RockOnlyChoiceStrategy())
                .setChoice(choice)
                .build();
    }
}
