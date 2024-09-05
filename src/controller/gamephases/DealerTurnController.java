package controller.gamephases;

import controller.GameController;
import misc.AudioManager;
import misc.Sounds;
import model.game.models.Dealer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import static model.game.utils.Constants.DEALER_STANDS_AT;

public class DealerTurnController extends GamePhaseManager{

    private final GameController gameController;
    private Dealer dealer;

    private Timer timer;

    public DealerTurnController(GameController gameController){
        this.gameController = gameController;
    }

    /**
     * Manage the Dealer Turn Phase.
     * The Dealer must keep dealing cards to himself until a soft total of 17 is reached.
     */
    @Override
    public void manage() {
        gameController.getGamePage().getNotificationsPanel().addTextNotification("Il Dealer rivela la carta");
        dealer = gameController.getGame().getDealer();
        dealCardsUntilStandingOrBustOrManageNextPhase();
    }

    /**
     * Control which operation must be performed.
     */
    private void dealCardsUntilStandingOrBustOrManageNextPhase(){
            if (!dealer.isCardRevealed()) {
                manageCardReveal();
            } else if (dealer.getHand().softTotal() >= DEALER_STANDS_AT){
                // Update Dealer state
                gameController.getGamePage().getTablePanel().getDealerPanel().updateDealerHandState(dealer.getHand());
                manageNextPhase();
            } else {
                manageCardDeal();
            }
    }

     /**
      * Reveal the hidden card.
      * */
    private void manageCardReveal(){
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                javax.swing.Timer revealAfter  = new javax.swing.Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!isTerminated) {
                            AudioManager.getInstance().play(Sounds.CARD_DEAL);
                            dealer.revealHiddenCard();
                            dealCardsUntilStandingOrBustOrManageNextPhase();
                        }
                    }
                });
                revealAfter.setRepeats(false);
                revealAfter.start();
            }
        };

        timer.schedule(timerTask,1000);
    }

    /**
     * Dealer deals a card to himself.
     */
    private void manageCardDeal(){
        timer = new Timer();
        TimerTask timerTask  = new TimerTask() {
            @Override
            public void run() {
                javax.swing.Timer dealAfter = new javax.swing.Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!isTerminated) {
                            AudioManager.getInstance().play(Sounds.CARD_DEAL);
                            dealer.dealDealerCard(false);
                            if (dealer.getHand().isBusted()){
                                AudioManager.getInstance().play(Sounds.DEALER_BUSTED);
                            }
                            dealCardsUntilStandingOrBustOrManageNextPhase();
                        }
                    }
                });

                dealAfter.setRepeats(false);
                dealAfter.start();
            }
        };

        timer.schedule(timerTask,1000);
    }



}
