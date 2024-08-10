package controller.gamephases;

public class GamePhaseManager implements Manageable{

    private GamePhaseManager nextPhase;

    public void setNextPhase(GamePhaseManager nextPhase) {
        this.nextPhase = nextPhase;
    }

    public void manageNextPhase(){
        nextPhase.manage();
    }

    public void manage(){

    }
}
