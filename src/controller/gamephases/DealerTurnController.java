package controller.gamephases;

import controller.GameController;
import model.game.models.standalones.Dealer;

import java.util.Timer;
import java.util.TimerTask;

import static model.game.utils.Constants.DEALER_STANDS_AT;

public class DealerTurnController extends GamePhaseManager{

    private final GameController gameController;

    private Timer timer;

    public DealerTurnController(GameController gameController){
        this.gameController = gameController;
    }

    @Override
    public void manage() {
        gameController.getGamePage().getNotificationsPanel().addTextNotification("Revealing Card");
        Dealer.getInstance().revealHiddenCard();

        while(Dealer.getInstance().getHand().softTotal() < DEALER_STANDS_AT){
            Dealer.getInstance().dealDealerCard(false);
        }
        // dealCardsUntilStandingOrBust();
        manageNextPhase();
    }

    private void dealCardsUntilStandingOrBust(){
        if (Dealer.getInstance().getHand().softTotal() >=DEALER_STANDS_AT) {
            System.out.println("Recursion terminates");
            manageNextPhase();
            return;
        }

        timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                javax.swing.Timer revealCardTimer= new javax.swing.Timer(1000, e -> {
                    System.out.println("Dealing dealer's card");
                    Dealer.getInstance().dealDealerCard(false);
                    dealCardsUntilStandingOrBust();
                });

                revealCardTimer.setRepeats(false);
                revealCardTimer.start();
            }
        };

        timer.schedule(task, 1000);
   }
}
