package controller.gamephases;

import controller.GameController;
import controller.AudioManager;
import controller.enums.Sounds;
import model.game.models.Game;
import model.game.models.player.HumanPlayer;
import model.game.models.player.Player;
import model.game.models.Dealer;

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

    /**
     * Manage the card distribution phase.
     * Deal a first round of cards to all the players,
     * then a card to himself in hidden state.
     * Deal a second round of cards to all the players,
     * then a card to himself, in visible state.
     */
    @Override
    public void manage(){
        Game game = gameController.getGame();

        gameController.getGamePage().getNotificationsPanel().addTextNotification("Il Dealer distribuisce le carte...");

        playingPlayersFirst = game.getPlayingPlayers().iterator();
        playingPlayersSecond = game.getPlayingPlayers().iterator();

        dealer = gameController.getGame().getDealer();

        distributeCardsOrManageNextPhase();
    }

    /**
     * Control which card must be dealt.
     */
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

    /**
     * The Dealer deals to the input Player
     * @param player
     */
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

    /**
     * The Dealer deals to himself a card in "isHidden" state.
     * @param isHidden
     */
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
