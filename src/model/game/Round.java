package model.game;

import model.game.models.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private static int roundsCounter = 0; // For each round instantiated, increment the round field
    private int number;

    private List<Player> winners;

    public Round(){
        roundsCounter++;
        number = roundsCounter;

        this.winners = new ArrayList<>();

        System.out.println("Round " + number + " has started!");
    }

}
