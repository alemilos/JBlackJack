package controller.gamephases;

import controller.GameController;
import controller.AudioManager;
import controller.enums.Sounds;
import model.game.models.Game;
import model.game.enums.Actions;
import model.game.models.player.HumanPlayer;
import model.game.models.player.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import static controller.Constants.AI_TURN_MS;
import static controller.Constants.USER_TURN_MS;
import static model.game.utils.Constants.DEALER_STANDS_AT;

public class UsersActionsController extends GamePhaseManager{

    private Timer timer;

    private Iterator<Player> playingPlayers;

    private final GameController gameController;
    private Game game;

    public UsersActionsController(GameController gameController){
        this.gameController = gameController;
    }

    /**
     * Manage the users actions
     */
    @Override
    public void manage(){
        game = gameController.getGame();

        playingPlayers = game.getPlayingPlayers().iterator();

        // Initialize the Turns
        playNextTurnOrManageNextPhase();
    }

    /**
     * Manage the AI Turn
     * @param player
     */
    private void manageAITurn(Player player){
        // The AI player must perform actions in 15 seconds.

        // I calculate all the actions at first and give a random time between each of them.
        // Each action must take at least 2 seconds .
        gameController.getGamePage().getNotificationsPanel().addTextNotification("È il turno di " + player.getName());

        timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {

                int randomDelay = (int) (Math.random() * AI_TURN_MS - 1000) + 1000; // Random delay between 1 and ai turn duration
                javax.swing.Timer aiTimer = new javax.swing.Timer(randomDelay, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!isTerminated) {
                            simulateAiAction(player);
                            // ((AIPlayer) player).simulateActions();
                            playNextTurnOrManageNextPhase();
                        }
                    }
                });
                aiTimer.setRepeats(false);
                aiTimer.start();
            }
        };

        timer.schedule(task, AI_TURN_MS);

    }

    /**
     * Simulate a move.
     * @param player
     */
    private void simulateAiAction(Player player){
        Random rand = new Random();
        int probability;

        probability = rand.nextInt(0, 100);

        if (player.getHand().softTotal() > DEALER_STANDS_AT) {
            if (probability > 95) {
                // Risky action
                game.getTurn().manageAction(Actions.HIT);
                AudioManager.getInstance().play(Sounds.CARD_DEAL);
            } else {
                // Safe play
                game.getTurn().manageAction(Actions.STAND);
                AudioManager.getInstance().play(Sounds.KNOCK);
            }
        } else {
            game.getTurn().manageAction(Actions.HIT);
            AudioManager.getInstance().play(Sounds.CARD_DEAL);
        }


    }

    /**
     * If another player's turn can be played, play it. Go to the next phase otherwise.
     */
    private void playNextTurnOrManageNextPhase(){
        // Terminate previous turn.
        game.finishTurn();

        if (playingPlayers.hasNext()){
            Player player = playingPlayers.next();
            game.playTurn(player);

            // Turn could be terminated due to a blackjack.
            if (game.getTurn().isActive()) {
                if (player instanceof HumanPlayer) {
                    game.getTurn().startWithObserver(gameController.getGamePage().getTablePanel().getUserInterfacePanel());
                    manageHumanPlayerTurn();
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

    /**
     * Start the timer for the user action. If the user doesn't perform any action, make a Stand.
     */
    private void manageHumanPlayerTurn(){
        // If no action is performed after 15 seconds, make a Stand Automatically.
        // For each played action otherwise, restart a 15 seconds timer if the player can make another action.
        gameController.getGamePage().getNotificationsPanel().addTimer("Affrattati a compiere la tua azione!", (int)USER_TURN_MS, new Runnable() {
            @Override
            public void run() {
                if (game.getTurn().getPlayer() == game.getHumanPlayer()) {
                    makeAutoStand();
                }
            }
        });

    }

    /**
     * Restart the human timer (should be used after a user action performed in the ui)
     */
    public void restartHumanTimer(){
        gameController.getGamePage().getNotificationsPanel().abortTimer(); // abort previous timer
        gameController.getGamePage().getNotificationsPanel().addTimer("Affrattati a compiere la tua azione!", (int)USER_TURN_MS, new Runnable() {
            @Override
            public void run() {
                makeAutoStand();
            }
        });
    }

    /**
     * Finish turn.
     */
    private void makeAutoStand(){
        game.finishTurn();
        terminateHumanTurn();
        AudioManager.getInstance().play(Sounds.KNOCK);
    }

    /**
     * Force terminate the Human Player turn
     */
    public void terminateHumanTurn(){
        gameController.getGamePage().getNotificationsPanel().abortTimer();
        playNextTurnOrManageNextPhase();
    }
}

