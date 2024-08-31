package controller.gamephases;

import controller.GameController;

public class GamePhaseManager implements Manageable{

    private GameController gameController;
    private GamePhaseManager nextPhase;

    private BetPhaseController betPhaseController;
    private CardsDistributionController cardsDistributionController;
    private UsersActionsController usersActionsController;
    private DealerTurnController dealerTurnController;
    private PaymentsPhaseController paymentsPhaseController;

    public static boolean isTerminated;

    public GamePhaseManager(){
    }

    public GamePhaseManager(GameController gameController){
        this.gameController = gameController;
        init();
    }

    private void init(){
        isTerminated = false;
        /** BET PHASE **/
        betPhaseController = new BetPhaseController(gameController);
        /** CARDS DISTRIBUTIONS PHASE **/
        cardsDistributionController = new CardsDistributionController(gameController);
        /** USERS ACTIONS PHASE **/
        usersActionsController = new UsersActionsController(gameController);
        /** DEALER TURN PHASE**/
        dealerTurnController = new DealerTurnController(gameController);
        /** PAYMENTS PHASE **/
        paymentsPhaseController = new PaymentsPhaseController(gameController);

        /** Create the game flow */
        this.setNextPhase(betPhaseController);
        betPhaseController.setNextPhase(cardsDistributionController);
        cardsDistributionController.setNextPhase(usersActionsController);
        usersActionsController.setNextPhase(dealerTurnController);
        dealerTurnController.setNextPhase(paymentsPhaseController);
    }

    public void setNextPhase(GamePhaseManager nextPhase) {
        this.nextPhase = nextPhase;
    }

    public void manageNextPhase(){
        if (!isTerminated) {
            nextPhase.manage();
        }
    }

    public void manage(){}

    public UsersActionsController getUsersActionsController() {
        return usersActionsController;
    }

    public void terminate(){
        isTerminated = true;
    }
}
