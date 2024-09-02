package model.game.models.standalones;

import model.game.enums.Chips;

import java.util.*;
import java.util.stream.Collectors;

public class Bet {

    private Map<Chips, Integer> chips;
    private List<Chips> chipAddedQueue;

    /**
     * The Bet is composed of all the bet chips, along with the count of each.
     */
    public Bet(){
        this.chips = new HashMap<>();
        chipAddedQueue = new ArrayList<>();
    }

    /**
     * Increment or Add the chip to the Bet.
     * @param chip
     */
    public void add(Chips chip){
        chipAddedQueue.add(chip);
        if (containsChip(chip)){
            chips.replace(chip, chips.get(chip)  +1);
        }else {
            chips.put(chip, 1);
        }
    }

    /**
     * Pop the last added chip to the bet
     * @return
     */
    public Chips pop(){
        if(!chipAddedQueue.isEmpty()){
        Chips removedChip = chipAddedQueue.remove(chipAddedQueue.size()-1);

        if (chips.get(removedChip) > 1){
            chips.replace(removedChip, chips.get(removedChip) -1);
        }else{
            chips.remove(removedChip);
        }
        return  removedChip;
        }
        return null;
    }

    public Chips peek(){
        if (!chipAddedQueue.isEmpty()){
            return chipAddedQueue.get(chipAddedQueue.size()-1);
        }
        return null;
    }


    /**
     * Delete the Bet, all the chips are nullified.
     */
    public void delete(){
        this.chips = new HashMap<>();
        chipAddedQueue = new ArrayList<>();
    }

    /**
     * A Boolean representing if the Bet is empty of chips or not.
     * @return
     */
    public boolean isEmpty(){
        return chips.isEmpty();
    }

    public boolean isChipAddedQueueEmpty(){
        return chipAddedQueue.isEmpty();
    }

    /**
     * Double the bet.
     */
    public void x2(){
        for (Map.Entry<Chips, Integer> entry: chips.entrySet()){
            Chips chip = entry.getKey();
            chips.put(chip, chips.get(chip) * 2);
        }
    }

    /**
     * A Boolean that represent if a given chip is in the Bet.
     * @param chip
     * @return
     */
    public boolean containsChip(Chips chip){
       return chips.containsKey(chip);
    }

    /**
     * Return the Bet total amount
     * @return
     */
    public int total(){
        int total = 0;

        for (Map.Entry<Chips, Integer> entry: chips.entrySet()){
            Chips chip = entry.getKey();
            int chipCount = entry.getValue();

            total += chipCount * chip.getValue();
        }

        return total;
    }

    public Map<Chips, Integer> getChips() {
        return chips;
    }

    @Override
    public String toString() {
        return chips.toString();
    }
}
