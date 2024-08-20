package controller.gamephases;

import controller.GameController;
import model.game.Game;
import model.game.models.player.HumanPlayer;
import model.game.models.player.Player;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class CardsDistributionController extends GamePhaseManager implements Manageable{

    private GameController gameController;

    public CardsDistributionController (GameController gameController){
        this.gameController = gameController;
    }

    @Override
    public void manage(){
        Game game = Game.getInstance();

        gameController.getGamePage().getNotificationsPanel().addTextNotification("Il mazziere distribuisce le carte...");

        game.getDealer().distributeCards();


        Iterator<Player> iterator = game.getPlayingPlayers().iterator();

        int i = 0;
        Timer timer = new Timer();

        while(iterator.hasNext()){
            Player player = iterator.next();
            int currentIndex = i;

            TimerTask task = new TimerTask() {
                @Override
                public void run() {

                    // On last player go to the next controller manager
                    if (currentIndex >= game.getPlayingPlayers().size()-1){
                        manageNextPhase();
                    }
                }
            };

            timer.schedule(task, 1000 * i++);
        }


    }
}
