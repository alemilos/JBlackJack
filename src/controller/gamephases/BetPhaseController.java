package controller.gamephases;

import controller.GameController;
import model.game.Game;
import model.game.models.player.AIPlayer;
import model.game.models.player.HumanPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import static misc.Constants.BET_TIME_MS;

public class BetPhaseController extends GamePhaseManager implements Manageable{

    private GameController gameController;

    public BetPhaseController(GameController gameController){
        this.gameController = gameController;
    }

    @Override
    public void manage(){
        Game game = Game.getInstance();
        game.setBetPhase(true);

        // Enable Chips Buttons so that user can make a bet
        gameController.getGamePage().enableBetButtons();

        Runnable onTimerEnds = () -> {
            // Disable chips buttons
            gameController.getGamePage().disableBetButtons();

            game.setBetPhase(false);
            manageNextPhase();
        };

        // Add Timer to View
        gameController.getGamePage().getNotificationsPanel().addTimer("Scommetti", BET_TIME_MS, onTimerEnds);

        game.getPlayers().forEach(player -> {
            if (!(player instanceof HumanPlayer)) {
                ((AIPlayer) player).simulateBet();
                int randomDelay = (int) (Math.random() * BET_TIME_MS - 1000) + 1000; // Random delay between 1 and bet time
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

    }


}
