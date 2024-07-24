package model.game.managers;

import model.game.models.player.Player;
import model.game.enums.BetNotification;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BetManager extends Observable{
    private final int BETTING_DURATION_S = 1;

    private List<Player> players;

    public BetManager(List<Player> players){
        this.players = players;

        // Subscribe each player to BetManager
        for (Player player: players){
            addObserver(player);
        }
    }

    public void manage(){
        notifyBettingStarts();

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                notifyBettingEnds();
            }
        };

        executor.schedule(task, BETTING_DURATION_S, TimeUnit.SECONDS);

        executor.shutdown();
    }


    /**
     * Notify Players that they can Bet
     */
    public void notifyBettingStarts() {
        setChanged();
        notifyObservers(BetNotification.BETTING_STARTS);
    }

    /**
     * Notify Players that they cannot Bet anymore
     */
    public void notifyBettingEnds() {
        setChanged();
        notifyObservers(BetNotification.BETTING_ENDS);
    }

    /**
     * How many Players have made a Bet.
     */
    public int betsCount(){
        int bets = 0;

        for (Player player : players) {
            if (!player.getBet().isEmpty()) bets += 1;
        }

        return bets;
    }



}
