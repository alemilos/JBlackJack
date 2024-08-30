package model.game.models.standalones;

public class Bankroll {

    private int chipsLeft;

    public Bankroll(int buyIn){
        this.chipsLeft = buyIn;
    }

    public boolean canPay(int amount){
        return chipsLeft >= amount;
    }

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

    @Override
    public String toString() {
        return "BANKROLL: " + chipsLeft;
    }

    public int getChipsLeft() {
        return chipsLeft;
    }
}

