package controller.gamephases;

import controller.GameController;

public class GamePhaseManager implements Manageable{

    private GameController gameController;
    private GamePhaseManager nextPhase;

    // All Phase Controllers
    private BetPhaseController betPhaseController;
    private CardsDistributionController cardsDistributionController;
    private UsersActionsController usersActionsController;
    private DealerTurnController dealerTurnController;
    private PaymentsPhaseController paymentsPhaseController;

    public static boolean isTerminated; // A boolean to determine if the Game Phase Manager is terminated

    public GamePhaseManager(){
    }

    public GamePhaseManager(GameController gameController){
        this.gameController = gameController;
        init();
    }

    /**
     * Hook the game phases together by initializing the Phases Instances and setting the next phases to each.
     * Bet -> Card Distribution -> Users Actions -> Dealer Turn -> Payments
     */
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

    /**
     * Assign a next phase to a phase.
     * @param nextPhase
     */
    public void setNextPhase(GamePhaseManager nextPhase) {
        this.nextPhase = nextPhase;
    }

    /**
     * Manage the next phase of a previous phase.
     */
    public void manageNextPhase(){
        if (!isTerminated) {
            nextPhase.manage();
        }
    }

    public void manage(){}

    public UsersActionsController getUsersActionsController() {
        return usersActionsController;
    }

    /**
     * Terminate the Game Phase Manager.
     */
    public void terminate(){
        isTerminated = true;
    }
}
