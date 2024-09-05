package controller.gamephases;

import controller.GameController;
import misc.AudioManager;
import misc.Sounds;
import model.game.models.Game;
import model.game.models.Hand;
import model.game.models.player.Player;
import model.game.models.Dealer;
import view.components.game.NotificationsPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.TimerTask;

public class PaymentsPhaseController extends GamePhaseManager{

    private final GameController gameController;
    private Iterator<Player> playingPlayers;
    private java.util.Timer timer;
    private Hand dealerHand;

    public PaymentsPhaseController(GameController gameController){
        this.gameController = gameController;
    }

    /**
     * Manage the Payments Phase
     */
    @Override
    public void manage() {
        gameController.getGamePage().getNotificationsPanel().addTextNotification("Il banco gestisce i pagamenti...");
        playingPlayers = gameController.getGame().getPlayingPlayers().iterator();
        dealerHand = gameController.getGame().getDealer().getHand();
        gameController.getGame().updatePlayerStats();
        managePaymentOrManageNextPhase();
    }

    /**
     * Check weather a payment or the next phase management should be performed.
     */
    private void managePaymentOrManageNextPhase(){
            if (playingPlayers.hasNext()) {
                    Player player = playingPlayers.next();
                    timer = new java.util.Timer();
                    TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        Timer paymentTimer = new Timer(2000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!isTerminated) {
                                    managePayment(player);
                                    managePaymentOrManageNextPhase();
                                }
                            }
                        });

                        paymentTimer.setRepeats(false);
                        paymentTimer.start();
                    }
                    };

                    timer.schedule(timerTask, 1000);
            }else{
                gameController.handleReshuffling();
                gameController.startNewRound();
            }
    }

    /**
     * Check if player should pay, receive or push.
     * @param player
     */
    private void managePayment(Player player ){
        Hand playerHand = player.getHand();
        NotificationsPanel notificationPanel = gameController.getGamePage().getNotificationsPanel();

        AudioManager.getInstance().play(Sounds.MONEY);
        if (playerHand.isBlackjack()){
            if (dealerHand.isBlackjack()){
                notificationPanel.addTextNotification(player.getName() + " ha un Push");
                player.doPush();
            }else{
                notificationPanel.addTextNotification(player.getName() + " Guadagna");
                player.doEarn();
            }
        }
        else if (playerHand.isBusted()){
            notificationPanel.addTextNotification(player.getName() + " Paga");
            player.doPay();
        }
        else{
            if (dealerHand.isBusted()){
                notificationPanel.addTextNotification(player.getName() + " Guadagna");
                player.doEarn();
            }else{
                if (playerHand.softTotal() > dealerHand.softTotal()){
                    notificationPanel.addTextNotification(player.getName() + " Guadagna");
                    player.doEarn();
                }else if (playerHand.softTotal() == dealerHand.softTotal()){
                    // Dealer has same cards
                    notificationPanel.addTextNotification(player.getName() + " ha un Push");
                    player.doPush();
                }else{
                    notificationPanel.addTextNotification(player.getName() + " Paga");
                    player.doPay();
                }
            }
        }

    }
}
