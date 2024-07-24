package model.game.models.player;


import model.game.enums.Actions;
import model.game.enums.Chips;

import java.util.*;

import static model.game.utils.Constants.DEALER_STANDS_AT;

public class AIPlayer extends Player{



    /**
     * The AIPlayer will make decisions based on probability and other factors.
     * The probabilities are not based on the game hidden state, but only on what's visible on the table.
     * @param name
     */
    public AIPlayer(String name, int buyIn) {
        super(name, buyIn);
    }


    public void update(Observable o, Object arg){
        super.update(o, arg);
        simulateBet();
    }

    /**
     * Make a simulated Bet.
     */
    public void simulateBet() {
        if (isBetAvailable){
            // TODO: make this based on wins/loss

            Random rand = new Random();
            // Just a random betting strategy
            int probability = rand.nextInt(0, 100);

            // No Bet
            if (probability > 95){
                return;
            }


            // High Bet
            if (probability > 85){
                int isPurple = rand.nextInt(0,1);
                if (isPurple == 1){
                    bet.add(Chips.PURPLE);
                }else{
                    bet.add(Chips.PINK);
                }
            }

            // Medium Bet
            if (probability > 60){
                bet.add(Chips.BLUE);
            }

            // All other cases
            else{
                int kChips = rand.nextInt(1,4);
                int whichChip = rand.nextInt(0,3);
                for (int i = 0; i < kChips; i++) {
                if (whichChip == 0){
                    bet.add(Chips.YELLOW);
                }else if(whichChip ==1){
                    bet.add(Chips.GREEN);
                }else if(whichChip == 2){
                    bet.add(Chips.RED);
                }else if (whichChip == 3){
                    bet.add(Chips.BLACK);
                }
            }
            }
        }
    }

    /**
     * Make simulated actions
     * The AI Player has a high probability of playing following game rules, meaning
     * playing safe.
     */
    public void simulateActions(){
        Random rand = new Random();
        int probability;

        probability = rand.nextInt(0,100);

        if(hand.softTotal() > DEALER_STANDS_AT){
            if (probability > 95){
                // Risky action
                makeAction(Actions.HIT);
            }else{
                // Safe play
                makeAction(Actions.STAND);
            }
        }


    }

}
