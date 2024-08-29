package controller.gamephases;

import controller.GameController;

public class PaymentsPhaseController extends GamePhaseManager implements Manageable{

    private final GameController gameController;

    public PaymentsPhaseController(GameController gameController){
        this.gameController = gameController;
    }

    @Override
    public void manage() {
        System.out.println("Managing Payments");
    }
}
