package model.game.models.standalones;

import model.game.Game;
import model.game.models.hand.Hand;
import model.game.models.player.Player;

import java.util.List;
import java.util.Observable;

import static misc.Updates.CARD_ADD;

public class Dealer extends Observable {

    private static Dealer instance;

    private Sabot sabot;
    private DiscardDeck discardDeck;

    protected final String name = "Dealer";
    private Hand hand;

    /**
     * The Dealer is the Player that manages the game.
     * He controls the Sabot and Discard Deck instances,
     * shuffles, reintegrates decks, deals cards to the players.
     */
    private Dealer() {
        this.hand = new Hand();
        this.sabot = Sabot.getInstance(6);
        this.discardDeck = new DiscardDeck();
    }

    /**
     * Get the Singleton instance of Dealer or create a new instance;
     * @return
     */
    public static Dealer getInstance(){
        if (instance ==null){
            instance = new Dealer();
        }
        return instance;
    }

    /**
     * Directly sends the card to the player's current hand (it can either be Hand or SplitHand);
     * @param player
     */
    public void dealCard(Player player){
        Card dealtCard = sabot.dealCard();
        player.receiveCard(dealtCard);
    }

    public void dealDealerCard(boolean isHidden){
        if (isHidden) {
           hand.add(sabot.dealHiddenCard());
        }else{
            hand.add(sabot.dealCard());
        }

        notifyCardUpdate();
    }

    /**
     * Distribute 2 cards to each player that has made a Bet.
     */
    public void distributeCards(){
        List<Player> players = Game.getInstance().getPlayers();

        // First Distribution
       for (Player player: players) {
            if (!player.getBet().isEmpty()){
               dealCard(player);
            }
       }

       // Deal a visible card to the Dealer himself.
       dealDealerCard(false);

       // Second distribution
       for (Player player: players){
           if (!player.getBet().isEmpty()){
               dealCard(player);
           }
       }

       // Deal a hidden card to the Dealer himself
       dealDealerCard(true);
    }

    public Hand getHand() {
        return hand;
    }

    public void resetInstance(){
        instance = null;
        sabot.resetInstance();
    }

    private void notifyCardUpdate(){
        setChanged();
        notifyObservers(CARD_ADD);
    }
}
