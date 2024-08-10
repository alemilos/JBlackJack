package controller.gamephases;

import controller.GameController;
import model.game.Game;
import model.game.models.player.HumanPlayer;
import model.game.models.player.Player;

import java.util.*;

public class UsersActionsController extends GamePhaseManager implements Manageable{

    GameController gameController;

    public UsersActionsController(GameController gameController){
        this.gameController = gameController;
    }

    @Override
    public void manage(){
        gameController.getGamePage().getNotificationsPanel().addTextNotification("Users turn!");

        Game game = Game.getInstance();
        Iterator<Player> iterator = game.getPlayingPlayers().iterator();

        int i = 1;
        Timer timer = new Timer();

        while(iterator.hasNext()){
            Player player = iterator.next();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                   if (player instanceof HumanPlayer){
                        // gameController.getGamePage().enableActionButtons();

                   }
                }
            };

            timer.schedule(task, 1000 * i++);
        }
    }

}
