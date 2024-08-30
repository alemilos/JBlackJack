package controller.gamephases;

import controller.GameController;
import model.game.Game;
import model.game.models.hand.Hand;
import model.game.models.player.Player;
import model.game.models.standalones.Dealer;

public class PaymentsPhaseController extends GamePhaseManager implements Manageable{

    private final GameController gameController;

    public PaymentsPhaseController(GameController gameController){
        this.gameController = gameController;
    }

    @Override
    public void manage() {
        gameController.getGamePage().getNotificationsPanel().addTextNotification("Il banco gestisce i pagamenti...");

        Hand dealerHand = Dealer.getInstance().getHand();

        for (Player player : Game.getInstance().getPlayingPlayers()){
            System.out.println(player.getName());
            System.out.println("Before payment\n" + player.getBankroll());

            Hand playerHand = player.getHand();

            if (playerHand.isBlackjack()){
                if (dealerHand.isBlackjack()){
                    player.doPush();
                }else{
                    player.doEarn();
                }
            }
            else if (playerHand.isBusted()){
                player.doPay();
            }
            else{
                if (dealerHand.isBusted()){
                    player.doEarn();
                }else{
                    if (playerHand.softTotal() > dealerHand.softTotal()){
                        player.doEarn();
                    }else{
                        player.doPay();
                    }
                }
            }
            System.out.println("After Payment\n" + player.getBankroll());
        }
    }
}
