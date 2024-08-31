package controller.gamephases;

import controller.GameController;
import misc.AudioManager;
import misc.Sounds;
import model.game.Game;
import model.game.enums.Actions;
import model.game.models.player.HumanPlayer;
import model.game.models.player.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import static misc.Constants.AI_TURN_MS;
import static misc.Constants.USER_TURN_MS;
import static model.game.utils.Constants.DEALER_STANDS_AT;

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

    private void simulateAiAction(Player player){
        Random rand = new Random();
        int probability;

        probability = rand.nextInt(0, 100);

        if (player.getHand().softTotal() > DEALER_STANDS_AT) {
            if (probability > 95) {
                // Risky action
                Game.getInstance().getTurn().manageAction(Actions.HIT);
                AudioManager.getInstance().play(Sounds.CARD_DEAL);
            } else {
                // Safe play
                Game.getInstance().getTurn().manageAction(Actions.STAND);
                AudioManager.getInstance().play(Sounds.KNOCK);
            }
        } else {
            Game.getInstance().getTurn().manageAction(Actions.HIT);
            AudioManager.getInstance().play(Sounds.CARD_DEAL);
        }


    }

    /**
     * If another player's turn can be played, play it. Go to the next phase otherwise.
     */
    private void playNextTurnOrManageNextPhase(){
        // Terminate previous turn.
        Game.getInstance().finishTurn();

        if (playingPlayers.hasNext()){
            Player player = playingPlayers.next();
            Game.getInstance().playTurn(player);

            // Turn could be terminated due to a blackjack.
            if (Game.getInstance().getTurn().isActive()) {
                if (player instanceof HumanPlayer) {
                    Game.getInstance().getTurn().startWithObserver(gameController.getGamePage().getTablePanel().getUserInterfacePanel());
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

    private void manageHumanPlayerTurn(){
        // If no action is performed after 15 seconds, make a Stand Automatically.
        // For each played action otherwise, restart a 15 seconds timer if the player can make another action.
        gameController.getGamePage().getNotificationsPanel().addTimer("Affrattati a compiere la tua azione!", (int)USER_TURN_MS, new Runnable() {
            @Override
            public void run() {
                if (Game.getInstance().getTurn().getPlayer() == Game.getInstance().getHumanPlayer()) {
                    makeAutoStand();
                }
            }
        });

    }

    public void restartHumanTimer(){
        gameController.getGamePage().getNotificationsPanel().abortTimer(); // abort previous timer
        gameController.getGamePage().getNotificationsPanel().addTimer("Affrattati a compiere la tua azione!", (int)USER_TURN_MS, new Runnable() {
            @Override
            public void run() {
                makeAutoStand();
            }
        });
    }

    private void makeAutoStand(){
        Game.getInstance().finishTurn();
        terminateHumanTurn();
        AudioManager.getInstance().play(Sounds.KNOCK);
    }

    public void terminateHumanTurn(){
        gameController.getGamePage().getNotificationsPanel().abortTimer();
        playNextTurnOrManageNextPhase();
    }
}

