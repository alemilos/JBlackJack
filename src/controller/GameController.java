package controller;

import controller.gamephases.GamePhaseManager;
import misc.AudioManager;
import misc.Sounds;
import model.game.Game;
import model.game.enums.Actions;
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

import static model.game.utils.Constants.BLACKJACK;

public class GameController {

    private static GameController instance;

    private GamePage gamePage;

    private Game game;

    private HumanPlayer humanPlayer;

    private List<PlayerPanel> playerPanels;

    private GamePhaseManager gamePhaseManager;

    private GameController() {
        User user = Controller.getUser();

        // Game Model initialization
        game = Game.getInstance();
        game.init(user, 2000);

        humanPlayer = game.getHumanPlayer();
        game.startGame(); // Game starts

        startNewRound();
    }

    public void startNewRound(){
        game.clearRound();

        if (!canGameContinue()){
            manageGameLoss();
            return;
        }

        if (gamePage != null){
           gamePage.dispose();
        }

        gamePage = new GamePage();

        initPlayerPanelsAndAddObservers();

        gamePage.drawInitialGameState(playerPanels);

        addActionListeners();

        /** Assemble the game flow steps */

        gamePhaseManager = new GamePhaseManager(this);
        gamePhaseManager.manageNextPhase(); // Start the flow
    }

    private boolean canGameContinue(){
        return !(game.getHumanPlayer().getBankroll().getChipsLeft() <= 0);
    }

    private void manageGameLoss(){
            AudioManager.getInstance().play(Sounds.LOSE);

            gamePage.getTablePanel().clearForLoss();

            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    javax.swing.Timer endAfter   = new javax.swing.Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            handleGameLeave();
                        }
                    });
                    endAfter.setRepeats(false);
                    endAfter.start();
                }
            };

            timer.schedule(timerTask, 8000 );
   }

   private void handleGameLeave(){
       gamePhaseManager.terminate();
       gamePhaseManager = null;
       game.finishGame();
       gamePage.dispose();
       resetInstance();
       Controller.getInstance().goToHome();
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
                handleGameLeave();
            }
        });

        gamePage.getTablePanel().getUserInterfacePanel().getActionButtons().forEach(actionBtn-> {
            actionBtn.getIconButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // If the turn is played by HumanPlayer, perform the clicked action.
                    if (Game.getInstance().getTurn().getPlayer() instanceof HumanPlayer) {
                        if (actionBtn.getAction() == Actions.HIT || actionBtn.getAction() == Actions.DOUBLE_DOWN){
                            AudioManager.getInstance().play(Sounds.CARD_DEAL);
                        }else if(actionBtn.getAction() == Actions.STAND){
                            AudioManager.getInstance().play(Sounds.KNOCK);
                        }

                        Game.getInstance().getTurn().manageAction(actionBtn.getAction());

                        if (!Game.getInstance().getTurn().isActive()){
                            gamePhaseManager.getUsersActionsController().terminateHumanTurn();

                            if (humanPlayer.getHand().softTotal() > BLACKJACK){ // Busted
                                AudioManager.getInstance().play(Sounds.BUSTED);
                            }else if(humanPlayer.getHand().softTotal() == BLACKJACK){  // Blackjack
                                AudioManager.getInstance().play(Sounds.BLACKJACK);
                            }
                        } else{  // Can make another action
                            System.out.println("Restarting Human Timer");
                            gamePhaseManager.getUsersActionsController().restartHumanTimer();
                        }
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
                        AudioManager.getInstance().play(Sounds.CHIPS_BET);
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

