package controller.gamephases;

import controller.GameController;
import misc.AudioManager;
import model.game.Game;
import model.game.models.hand.Hand;
import model.game.models.player.Player;
import model.game.models.standalones.Dealer;
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

        System.out.println("Game controller from payments: " + gameController);
    }

    @Override
    public void manage() {
        gameController.getGamePage().getNotificationsPanel().addTextNotification("Il banco gestisce i pagamenti...");

        playingPlayers = Game.getInstance().getPlayingPlayers().iterator();

        dealerHand = Dealer.getInstance().getHand();

        managePaymentOrManageNextPhase();

    }

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
                                managePayment(player);
                                managePaymentOrManageNextPhase();
                            }
                        });

                        paymentTimer.setRepeats(false);
                        paymentTimer.start();
                    }
                    };

                    timer.schedule(timerTask, 1000);
            }else{
                System.out.println("add timer before restarting round");
                gameController.startNewRound();
            }
    }

    private void managePayment(Player player ){
        Hand playerHand = player.getHand();
        NotificationsPanel notificationPanel = gameController.getGamePage().getNotificationsPanel();

        AudioManager.getInstance().play("./assets/sounds/bankrollreceive.wav");
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
                }else{
                    notificationPanel.addTextNotification(player.getName() + " Paga");
                    player.doPay();
                }
            }
        }

    }
}
