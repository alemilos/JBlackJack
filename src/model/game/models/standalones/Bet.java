package model.game.models.standalones;

import model.game.enums.Chips;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Bet {

    private Map<Chips, Integer> chips;

    /**
     * The Bet is composed of all the bet chips, along with the count of each.
     */
    public Bet(){
        this.chips = new HashMap<>();
    }

    /**
     * Increment or Add the chip to the Bet.
     * @param chip
     */
    public void add(Chips chip){
        if (containsChip(chip)){
            chips.replace(chip, chips.get(chip)  +1);
        }else {
            chips.put(chip, 1);
        }
    }

    /**
     * Decrement the chip or remove it if there is not one.
     * @param chip
     */
    public void remove(Chips chip){
        if (containsChip(chip)){
            if (chips.get(chip) > 1){
                chips.replace(chip, chips.get(chip) -1);
            }else{
                chips.remove(chip);
            }
        }
    }

    /**
     * Delete the Bet, all the chips are nullified.
     */
    public void delete(){
        this.chips = new HashMap<>();
    }

    /**
     * Double the Bet (x2)
     */
    public void x2(){
        chips = chips.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue() * 2
        ));
    }

    /**
     * A Boolean representing if the Bet is empty of chips or not.
     * @return
     */
    public boolean isEmpty(){
        return chips.isEmpty();
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

        // TODO: refactor with streams
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
