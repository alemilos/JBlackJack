package controller.gamephases;

import controller.GameController;
import controller.AudioManager;
import controller.enums.Sounds;
import model.game.models.Game;
import model.game.models.player.AIPlayer;
import model.game.models.player.HumanPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import static controller.Constants.BET_TIME_MS;

public class BetPhaseController extends GamePhaseManager{
    private final GameController gameController;

    public BetPhaseController(GameController gameController){
        this.gameController = gameController;
    }

    /**
     * Manage the Bet Phase.
     * Each player can bet concurrently.
     */
    @Override
    public void manage(){
        Game game = gameController.getGame();
        game.setBetPhase(true);

        Runnable onTimerEnds = () -> {
            game.setBetPhase(false);
            gameController.getGamePage().getNotificationsPanel().clearNotificationBar();
            manageNextPhase();
        };

        // Add Timer to View
        gameController.getGamePage().getNotificationsPanel().addTimer("Scommetti", BET_TIME_MS, onTimerEnds);

        game.getPlayers().forEach(player -> {
            if (!(player instanceof HumanPlayer)) {
                int randomDelay = (int) (Math.random() * BET_TIME_MS - 1000) + 1000; // Random delay between 1 and bet time
                Timer aiTimer = new Timer(randomDelay, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!isTerminated) {
                            if (game.isBetPhase()) {
                                AudioManager.getInstance().play(Sounds.CHIPS_BET);
                                ((AIPlayer) player).simulateBet();
                            }
                        }
                    }
                });
                aiTimer.setRepeats(false);
                aiTimer.start();
            }
        });

    }

}
