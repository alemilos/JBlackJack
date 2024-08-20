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

    GameController gameController;

    public UsersActionsController(GameController gameController){
        this.gameController = gameController;
    }

    @Override
    public void manage(){

        Game game = Game.getInstance();
        Iterator<Player> iterator = game.getPlayingPlayers().iterator();

        int i = 0;
        Timer timer = new Timer();

        while(iterator.hasNext()){
            Player player = iterator.next();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    gameController.getGamePage().getNotificationsPanel().addTextNotification(player.getName() + " turn");

                   if (player instanceof HumanPlayer){

                   }else {
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
                }
            };

            timer.schedule(task, USER_TURN_MS * i++);
        }
    }

}
