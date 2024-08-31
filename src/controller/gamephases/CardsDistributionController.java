package controller.gamephases;

import controller.GameController;
import misc.AudioManager;
import misc.Sounds;
import model.game.Game;
import model.game.models.player.HumanPlayer;
import model.game.models.player.Player;
import model.game.models.standalones.Dealer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class CardsDistributionController extends GamePhaseManager{

    private final GameController gameController;
    private Dealer dealer;

    private Iterator<Player> playingPlayersFirst;
    private Iterator<Player> playingPlayersSecond;
    private boolean distributedToDealerHidden;
    private boolean distributedToDealerVisible;

    private Timer timer;


    public CardsDistributionController (GameController gameController){
        this.gameController = gameController;
    }

    @Override
    public void manage(){
        Game game = Game.getInstance();

        gameController.getGamePage().getNotificationsPanel().addTextNotification("Il mazziere distribuisce le carte...");

        playingPlayersFirst = game.getPlayingPlayers().iterator();
        playingPlayersSecond = game.getPlayingPlayers().iterator();

        dealer = Dealer.getInstance();

        distributeCardsOrManageNextPhase();
    }

    private void distributeCardsOrManageNextPhase(){
        if(playingPlayersFirst.hasNext()){
            Player player = playingPlayersFirst.next();
            manageDealerToPlayerDistribution(player);
        }else if (!distributedToDealerHidden){
            manageDealerToHimselfDistribution(true);
            distributedToDealerHidden = true;
        }else if(playingPlayersSecond.hasNext()){
           Player player = playingPlayersSecond.next();
           manageDealerToPlayerDistribution(player);
        }else if(!distributedToDealerVisible){
            manageDealerToHimselfDistribution(false);
            distributedToDealerVisible = true;
        }else{
            manageNextPhase();
        }
    }

    private void manageDealerToPlayerDistribution(Player player){
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                javax.swing.Timer dealAfter = new javax.swing.Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!isTerminated) {
                            AudioManager.getInstance().play(Sounds.CARD_DEAL);
                            dealer.dealCard(player);

                            if (player instanceof HumanPlayer) {
                               if(player.getHand().isBlackjack()) {
                                   AudioManager.getInstance().play(Sounds.BLACKJACK);
                               }
                            }
                            distributeCardsOrManageNextPhase();
                        }
                    }
                });

                dealAfter.setRepeats(false);
                dealAfter.start();
            }
        };

        timer.schedule(timerTask, 500);

    }

    private void manageDealerToHimselfDistribution(boolean isHidden){
       timer = new Timer();

       TimerTask timerTask = new TimerTask() {
           @Override
           public void run() {
               javax.swing.Timer dealAfter = new javax.swing.Timer(500, new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       if(!isTerminated) {
                           AudioManager.getInstance().play(Sounds.CARD_DEAL);
                           dealer.dealDealerCard(isHidden);
                           distributeCardsOrManageNextPhase();
                       }
                   }
               });

               dealAfter.setRepeats(false);
               dealAfter.start();
           }
       };

       timer.schedule(timerTask, 500);
    }
}
