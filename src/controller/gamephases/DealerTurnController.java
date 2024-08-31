package controller.gamephases;

import controller.GameController;
import misc.AudioManager;
import model.game.models.standalones.Dealer;

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

    @Override
    public void manage() {
        gameController.getGamePage().getNotificationsPanel().addTextNotification("Revealing Card");
        dealer = Dealer.getInstance();
        dealCardsUntilStandingOrBustOrManageNextPhase();

    }

    private void dealCardsUntilStandingOrBustOrManageNextPhase(){
            if (!dealer.isCardRevealed()) {
                manageCardReveal();
            } else if (dealer.getHand().softTotal() >= DEALER_STANDS_AT){
                manageNextPhase();
            } else {
                manageCardDeal();
            }
    }

    private void manageCardReveal(){
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                javax.swing.Timer revealAfter  = new javax.swing.Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AudioManager.getInstance().play("./assets/sounds/carddeal.wav");
                        dealer.revealHiddenCard();
                        dealCardsUntilStandingOrBustOrManageNextPhase();
                    }
                });
                revealAfter.setRepeats(false);
                revealAfter.start();
            }
        };

        timer.schedule(timerTask,1000);
    }

    private void manageCardDeal(){
        timer = new Timer();
        TimerTask timerTask  = new TimerTask() {
            @Override
            public void run() {
                javax.swing.Timer dealAfter = new javax.swing.Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AudioManager.getInstance().play("./assets/sounds/carddeal.wav");
                        dealer.dealDealerCard(false);
                        dealCardsUntilStandingOrBustOrManageNextPhase();
                    }
                });

                dealAfter.setRepeats(false);
                dealAfter.start();
            }
        };

        timer.schedule(timerTask,1000);
    }



}
