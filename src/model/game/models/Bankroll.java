package model.game.models;

public class Bankroll {

    private int chipsLeft;

    public Bankroll(int buyIn){
        this.chipsLeft = buyIn;
    }

    /**
     * Can the bankroll pay the given amount ?
     * @param amount
     * @return
     */
    public boolean canPay(int amount){
        return chipsLeft >= amount;
    }


    /**
     * Remove a given amount from the bankroll
     * @param amount
     */
    public void pay(int amount){
        if(canPay(amount)){
            chipsLeft -=amount;
        }
    }

    /**
     * Receive chip
     * @param amount
     */
    public void receive(int amount){
        chipsLeft += amount;
    }

    /**
     * Receive n * amount
     * @param amount
     * @param n
     */
    public void receive(int amount, int n){
        chipsLeft += amount * n;
    }

    public int getChipsLeft() {
        return chipsLeft;
    }
}

