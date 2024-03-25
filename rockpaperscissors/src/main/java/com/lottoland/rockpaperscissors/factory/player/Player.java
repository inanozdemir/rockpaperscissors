package com.lottoland.rockpaperscissors.factory.player;

import com.lottoland.rockpaperscissors.game.models.Choice;
import com.lottoland.rockpaperscissors.strategy.choice.ChoiceStrategy;

public class Player implements IPlayer{
    private final String name;
    private final ChoiceStrategy choiceStrategy;
    private Choice choice;

    private Player(String name, ChoiceStrategy choiceStrategy, Choice choice) {
        this.name = name;
        this.choiceStrategy = choiceStrategy;
        this.choice = choice;
    }

    @Override
    public Choice makeChoice() {
        if (choice == null)
            return choiceStrategy.makeChoice();
        else
            return choice;
    }

    public String getName() {
        return this.name;
    }

    public static class PlayerBuilder {
        private String name;
        private ChoiceStrategy choiceStrategy;
        private Choice choice;

        public PlayerBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public PlayerBuilder setChoiceStrategy(ChoiceStrategy choiceStrategy) {
            this.choiceStrategy = choiceStrategy;
            return this;
        }

        public PlayerBuilder setChoice(Choice choice) {
            this.choice = choice;
            return this;
        }

        public Player build() {
            return new Player(name, choiceStrategy, choice);
        }
    }
}

