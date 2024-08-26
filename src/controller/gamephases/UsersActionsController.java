package controller.gamephases;

import controller.GameController;
import model.game.Game;
import model.game.models.player.AIPlayer;
import model.game.models.player.HumanPlayer;
import model.game.models.player.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import static misc.Constants.BET_TIME_MS;
import static misc.Constants.USER_TURN_MS;

public class UsersActionsController extends GamePhaseManager implements Manageable{

    private GameController gameController;
    private Timer timer;

    private javax.swing.Timer humanTurnTimer;

    private Iterator<Player> playingPlayers;

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

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                gameController.getGamePage().getNotificationsPanel().addTextNotification(player.getName() + " Turn");

                int randomDelay = (int) (Math.random() * USER_TURN_MS - 1000) + 1000; // Random delay between 1 and user turn duration
                javax.swing.Timer aiTimer = new javax.swing.Timer(randomDelay, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ((AIPlayer) player).simulateActions();
                    }
                });
                aiTimer.setRepeats(false);
                aiTimer.start();
            }
        };

        timer.schedule(task, USER_TURN_MS);

    }

    private void manageHumanPlayerTurn(){
       HumanPlayer player = Game.getInstance().getHumanPlayer();
        // If no action is performed after 15 seconds, make a Stand Automatically.
        // For each played action otherwise, restart a 15 seconds timer if the player can make another action.
        startHumanTimer();

        playNextTurnOrManageNextPhase();
    }

    /**
     * If another player's turn can be played, play it. Go to the next phase otherwise.
     */
    private void playNextTurnOrManageNextPhase(){
        if (playingPlayers.hasNext()){
            Player player = playingPlayers.next();
            Game.getInstance().playTurn(player);

            if (player instanceof HumanPlayer){
                manageHumanPlayerTurn();
            }else {
                manageAITurn(player);
            }
        } else{
            manageNextPhase();
        }
    }

    private void startHumanTimer(){
       humanTurnTimer = new javax.swing.Timer((int)USER_TURN_MS, );
    }

    public void restartHumanTimer(){
        humanTurnTimer.restart();
    }
}

