package controller.gamephases;

import controller.GameController;

public class CardsDistributionController extends GamePhaseManager implements Manageable{

    private GameController gameController;

    public CardsDistributionController (GameController gameController){
        this.gameController = gameController;
    }

    @Override
    public void manage(){
        System.out.println("Distributing Cards");
    }
}
