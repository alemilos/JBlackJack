package controller;

import controller.gamephases.BetPhaseController;
import controller.gamephases.CardsDistributionController;
import controller.gamephases.GamePhaseManager;
import controller.gamephases.UsersActionsController;
import model.game.Game;
import model.game.enums.Suits;
import model.game.models.player.HumanPlayer;
import model.game.models.standalones.Dealer;
import model.global.User;
import view.components.game.ChipButton;
import view.components.game.PlayerPanel;
import view.pages.GamePage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class GameController {

    private static GameController instance;

    private GamePage gamePage;

    private Game game;

    private HumanPlayer humanPlayer;

    private List<PlayerPanel> playerPanels;

    private GameController(){
        gamePage = new GamePage();

        User user = Controller.getUser();

        // Game Model initialization
        game = Game.getInstance();
        game.init(user, 2000);

        humanPlayer = game.getHumanPlayer();
        game.startGame(); // Game starts

        initPlayerPanelsAndAddObservers();

        gamePage.drawInitialGameState(playerPanels);

        addActionListeners();

         /** Assemble the game flow steps */

        GamePhaseManager gamePhaseManager = new GamePhaseManager();
        /** BET PHASE **/
        BetPhaseController betPhaseController = new BetPhaseController(this);
        /** CARDS DISTRIBUTIONS PHASE **/
        CardsDistributionController cardsDistributionController = new CardsDistributionController(this);
        /** USERS ACTIONS PHASE **/
        UsersActionsController usersActionsController = new UsersActionsController(this);

        /** PAYMENTS PHASE **/

        /** Create the game flow */
        gamePhaseManager.setNextPhase(betPhaseController);
        betPhaseController.setNextPhase(cardsDistributionController);
        cardsDistributionController.setNextPhase(usersActionsController);

        gamePhaseManager.manageNextPhase(); // Start the flow
    }

    public static GameController getInstance() {
        if (instance == null){
            instance = new GameController();
        }
        return instance;
    }


    private void resetInstance(){
        instance = null;
    }


    public void addActionListeners(){
        gamePage.getLeaveBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.err.println("Should save game state to DB");

                game.finishGame();
                gamePage.dispose();
                resetInstance();
                Controller.getInstance().goToHome();
            }
        });

        gamePage.getTablePanel().getUserInterfacePanel().getActionButtons().forEach(actionBtn-> {
            actionBtn.getIconButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // If the turn is played by HumanPlayer, perform the clicked action.
                    if (Game.getInstance().getTurn().getPlayer() instanceof HumanPlayer) {
                        Game.getInstance().getTurn().manageAction(actionBtn.getAction());
                        restartHumanTimer();
                    }
                }
            });
        });

        for (int i = 0; i < gamePage.getTablePanel().getUserInterfacePanel().getChipButtons().size(); i++) {
            ChipButton chipBtn = gamePage.getTablePanel().getUserInterfacePanel().getChipButtons().get(i);
            chipBtn.getIconBtn().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(humanPlayer.canBet(chipBtn.getChip())){
                        humanPlayer.addToBet(chipBtn.getChip());
                    }
                }
            });
        }

        gamePage.getTablePanel().getUserInterfacePanel().getUndoBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                humanPlayer.popBet();
            }
        });

        gamePage.getTablePanel().getUserInterfacePanel().getDeleteBetBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                humanPlayer.deleteBet();
            }
        });
    }


    public void initPlayerPanelsAndAddObservers(){
        playerPanels = new ArrayList<>();
       game.getPlayers().forEach(player -> {
           PlayerPanel playerPanel = new PlayerPanel(player);
            playerPanels.add(playerPanel);

            // PlayerPanels observe Player models
            player.addObserver(playerPanel);
       });

       // UserInterfacePanel observes the game
        game.addObserver(gamePage.getTablePanel().getUserInterfacePanel());

        // DealerPanel observes the Dealer
        Dealer.getInstance().addObserver(gamePage.getTablePanel().getDealerPanel());

        // UserInterfacePanel observes HumanPlayer
        game.getHumanPlayer().addObserver(gamePage.getTablePanel().getUserInterfacePanel());
    }

    /***********************
     * GETTERS
     * *********************/

    public GamePage getGamePage() {
        return gamePage;
    }
}

