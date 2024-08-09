package controller.gamephases;

import controller.GameController;
import model.game.Game;
import model.game.models.player.AIPlayer;
import model.game.models.player.HumanPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.*;

public class BetPhaseController {

    private GameController gameController;

    public BetPhaseController(GameController gameController){
        this.gameController = gameController;
    }

    public void manage(){
        Game game = Game.getInstance();
        game.setBetPhase(true);

        game.getPlayers().forEach(player -> {
            if (player instanceof HumanPlayer) {
                Timer timer;
                JProgressBar betCountdown = new JProgressBar();

                timer = new Timer(10000, new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        System.out.println("Bet is finished!");
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
            else{
                ((AIPlayer) player).simulateBet();
                int randomDelay = (int) (Math.random() * 9000) + 1000; // Random delay between 1 and 10 seconds
                Timer aiTimer = new Timer(randomDelay, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        gameController.drawAIBet(player);
                    }
                });
                aiTimer.setRepeats(false);
                aiTimer.start();
            }
        });

        game.setBetPhase(false);
        System.out.println(game.getPlayers());

    }


}
