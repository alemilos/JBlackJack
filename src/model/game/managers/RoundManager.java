package model.game.managers;

import model.game.models.standalones.Dealer;
import model.game.models.player.Player;
import model.game.Round;

import java.util.ArrayList;
import java.util.List;

public class RoundManager{
    private List<Player> players;
    private Dealer dealer;
    private List<Round> rounds;

    private TurnManager turnManager;

    public RoundManager(List<Player> players, Dealer dealer){
        this.players = players;
        this.dealer = dealer;
        this.rounds = new ArrayList<>();
        this.turnManager = new TurnManager(players);
    }

    /**
     * This method is responsible for handling the Game Phases.
     * 1. Players Bet
     * 2. Cards Distribution
     * 3. Players Turn
     * 4. Dealer Turn
     * 5. Payments
     */
    public void playRound(){
        rounds.add(new Round());

       // betManager.manage();

       // // Proceed only if somebody made a Bet.
       // if (betManager.betsCount() > 0){
       //     dealer.distributeCards(players);

       //     for (Player player: players){
       //         System.out.println(player);
       //     }

       //     turnManager.manage();
       // }

        // TODO: FOR each player play Turn;
        // TODO: Play Dealer turn
        // TODO: Manage Payments


        // Start a new Round()
        // next();
    }

    /**
     * Move to the next round
     */
    private void next(){
        rounds.add(new Round());
    }

}
