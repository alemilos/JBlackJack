package controller.gamephases;

import controller.GameController;
import model.game.Game;
import model.game.enums.Actions;
import model.game.models.player.AIPlayer;
import model.game.models.player.HumanPlayer;
import model.game.models.player.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import static misc.Constants.AI_TURN_MS;
import static misc.Constants.USER_TURN_MS;
import static model.game.utils.Constants.BLACKJACK;

public class UsersActionsController extends GamePhaseManager{

    private Timer timer;

    private Iterator<Player> playingPlayers;

    private final GameController gameController;

    public UsersActionsController(GameController gameController){
        this.gameController = gameController;
    }

    @Override
    public void manage(){

        Game game = Game.getInstance();

        playingPlayers = game.getPlayingPlayers().iterator();

        // Initialize the Turns
        playNextTurnOrManageNextPhase();
    }

    private void manageAITurn(Player player){
        // The AI player must perform actions in 15 seconds.

        // I calculate all the actions at first and give a random time between each of them.
        // Each action must take at least 2 seconds .
        gameController.getGamePage().getNotificationsPanel().addTextNotification(player.getName() + " Turn");

        timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {

                int randomDelay = (int) (Math.random() * AI_TURN_MS - 1000) + 1000; // Random delay between 1 and ai turn duration
                javax.swing.Timer aiTimer = new javax.swing.Timer(randomDelay, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ((AIPlayer) player).simulateActions();
                        playNextTurnOrManageNextPhase();
                    }
                });
                aiTimer.setRepeats(false);
                aiTimer.start();
            }
        };

        timer.schedule(task, AI_TURN_MS);

    }


    /**
     * If another player's turn can be played, play it. Go to the next phase otherwise.
     */
    private void playNextTurnOrManageNextPhase(){
        // Terminate previous turn.
        Game.getInstance().finishTurn();

        if (playingPlayers.hasNext()){
            Player player = playingPlayers.next();
            // System.out.println(player.getName() + " TURN");

            Game.getInstance().playTurn(player);

            // Turn could be terminated due to a blackjack.
            if (Game.getInstance().getTurn().isActive()) {
                if (player instanceof HumanPlayer) {
                    Game.getInstance().getTurn().startWithObserver(gameController.getGamePage().getTablePanel().getUserInterfacePanel());
                    manageHumanPlayerTurn();
                    // Game.getInstance().deleteObserver(gameController.getGamePage().getTablePanel().getUserInterfacePanel());
                } else {
                    manageAITurn(player);
                }
            }else{
                playNextTurnOrManageNextPhase();
            }
        }
        else{
            manageNextPhase();
        }
    }

    private void manageHumanPlayerTurn(){
        // If no action is performed after 15 seconds, make a Stand Automatically.
        // For each played action otherwise, restart a 15 seconds timer if the player can make another action.
        gameController.getGamePage().getNotificationsPanel().addTimer("Affrattati a compiere la tua azione!", (int)USER_TURN_MS, new Runnable() {
            @Override
            public void run() {
                if (Game.getInstance().getTurn().getPlayer() == Game.getInstance().getHumanPlayer()) {
                    Game.getInstance().finishTurn();
                    terminateHumanTurn();
                }
            }
        });

    }

    public void restartHumanTimer(){
        gameController.getGamePage().getNotificationsPanel().abortTimer(); // abort previous timer
        gameController.getGamePage().getNotificationsPanel().addTimer("Affrattati a compiere la tua azione!", (int)USER_TURN_MS, new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public void terminateHumanTurn(){
        gameController.getGamePage().getNotificationsPanel().abortTimer();
        playNextTurnOrManageNextPhase();
    }
}

