package model.game.managers;

import model.game.models.player.Player;

import java.util.*;

public class TurnManager{
    private final int TURN_DURATION_S = 3;

    private List<Player> players;

    public TurnManager(List<Player> players){
        this.players = players;
    }

    public void manage(){
        for (Player player: players){
            if (!player.getBet().isEmpty()){
                System.out.println("Player: " + player.getName()+ " started his turn!");
                turn();
            }
        }
    }

    private void turn(){
        try {
            Thread.sleep(3000); // Durata del turno 3 secondi
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
