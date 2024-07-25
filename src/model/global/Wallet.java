package model.global;

import static model.global.Constants.STARTING_BALANCE;

public class Wallet {
    private int balance;

    Wallet(){
        this.balance = STARTING_BALANCE; // Default initial balance when user first sign-in to the game
    }

    Wallet(int balance){
        this.balance = balance;
    }

    public int getBalance(){
        return this.balance;
    }
}
