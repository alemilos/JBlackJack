package model.game.models.player;


import model.game.models.Game;
import model.game.enums.Chips;

import java.util.*;

public class AIPlayer extends Player{

    /**
     * The AIPlayer will make decisions based on probability and other factors.
     * The probabilities are not based on the game hidden state, but only on what's visible on the table.
     * @param name
     */
    public AIPlayer(String name, int buyIn) {
        super(name, buyIn);
    }

    /**
     * Make a simulated Bet.
     */
    public void simulateBet() {
        if (Game.getInstance().isBetPhase()){
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
                    addToBet(Chips.PURPLE);
                }else{
                    addToBet(Chips.PINK);
                }
            }

            // Medium Bet
            if (probability > 60){
                addToBet(Chips.BLUE);
            }

            // All other cases
            else{
                int kChips = rand.nextInt(1,4);
                int whichChip = rand.nextInt(0,3);
                for (int i = 0; i < kChips; i++) {
                if (whichChip == 0){
                    addToBet(Chips.YELLOW);
                }else if(whichChip ==1){
                    addToBet(Chips.GREEN);
                }else if(whichChip == 2){
                    addToBet(Chips.RED);
                }else if (whichChip == 3){
                    addToBet(Chips.BLACK);
                }
            }
            }
        }
    }

}
