package controller;

import controller.gamephases.GamePhaseManager;
import misc.AudioManager;
import misc.Sounds;
import model.game.models.Game;
import model.game.enums.Actions;
import model.game.models.player.HumanPlayer;
import view.components.game.ChipButton;
import view.components.game.PlayerPanel;
import view.pages.GamePage;

import java.awt.event.*;
import java.util.*;
import java.util.List;

import static model.game.utils.Constants.BLACKJACK;

public class GameController {

    private GamePage gamePage;
    private Game game;
    private HumanPlayer humanPlayer;
    private List<PlayerPanel> playerPanels;
    private GamePhaseManager gamePhaseManager;

    public GameController() {
        // Game Model initialization
        game = new Game(Controller.getUser(), 2000);
        humanPlayer = game.getHumanPlayer();
        game.startGame(); // Game starts
        startNewRound();
    }

    /**
     * If the Sabot needs to be reshuffled, perform a reshuffle.
     */
    public void handleReshuffling(){
        if (game.getDealer().getSabot().mustReshuffle()) {
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    AudioManager.getInstance().play(Sounds.SHUFFLE);
                    javax.swing.Timer shuffleTimer = new javax.swing.Timer(3000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            gamePage.getNotificationsPanel().addTextNotification("Il Dealer rimescola il Deck");
                            game.getDealer().getSabot().reshuffle();
                        }
                    });
                    shuffleTimer.setRepeats(false);
                    shuffleTimer.start();

                }
            };

            timer.schedule(timerTask, 0);
        }
    }

    /**
     * Start a new round if it can be done, otherwise terminate the game with a loss.
     */
    public void startNewRound(){
        game.clearRound();

        if (!canGameContinue()){
            manageGameLoss();
            return;
        }

        if (gamePage != null){
           gamePage.dispose();
        }

        gamePage = new GamePage(game);

        initPlayerPanelsAndAddObservers();

        gamePage.drawInitialGameState(playerPanels);

        addActionListeners();

        gamePhaseManager = new GamePhaseManager(this);
        gamePhaseManager.manageNextPhase(); // Start the flow
    }

    /**
     * If human player's bankroll contains chips, the game can continue.
     * @return
     */
    private boolean canGameContinue(){
        return !(game.getHumanPlayer().getBankroll().getChipsLeft() <= 0);
    }

    /**
     * Perform a game loss, handling the game leaving and sound playing.
     */
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

    /**
     * Clear the game state and leave it
     */
   private void handleGameLeave(){
       gamePhaseManager.terminate();
       gamePhaseManager = null;
       game.finishGame(); // save informations on db.
       gamePage.dispose();
       Controller.getInstance().goToHome();
    }


    /**
     * Add listeners to the game page.
     */
    public void addActionListeners(){
        gamePage.getLeaveBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGameLeave();
            }
        });

        // ACTION BUTTONS
        gamePage.getTablePanel().getUserInterfacePanel().getActionButtons().forEach(actionBtn-> {
            actionBtn.getIconButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // If the turn is played by HumanPlayer, perform the clicked action.
                    if (game.getTurn().getPlayer() instanceof HumanPlayer) {
                        if (actionBtn.getAction() == Actions.HIT || actionBtn.getAction() == Actions.DOUBLE_DOWN){
                            AudioManager.getInstance().play(Sounds.CARD_DEAL);
                        }else if(actionBtn.getAction() == Actions.STAND){
                            AudioManager.getInstance().play(Sounds.KNOCK);
                        }

                        game.getTurn().manageAction(actionBtn.getAction());

                        if (!game.getTurn().isActive()){
                            gamePhaseManager.getUsersActionsController().terminateHumanTurn();

                            if (humanPlayer.getHand().softTotal() > BLACKJACK){ // Busted
                                AudioManager.getInstance().play(Sounds.BUSTED);
                            }else if(humanPlayer.getHand().softTotal() == BLACKJACK){  // Blackjack
                                AudioManager.getInstance().play(Sounds.BLACKJACK);
                            }
                        } else{  // Can make another action
                            gamePhaseManager.getUsersActionsController().restartHumanTimer();
                        }
                    }
                }
            });
        });

        // CHIP BUTTONS
        for (int i = 0; i < gamePage.getTablePanel().getUserInterfacePanel().getChipButtons().size(); i++) {
            ChipButton chipBtn = gamePage.getTablePanel().getUserInterfacePanel().getChipButtons().get(i);
            chipBtn.getIconBtn().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (game.isBetPhase()) {
                        if (humanPlayer.canBet(chipBtn.getChip())) {
                            AudioManager.getInstance().play(Sounds.CHIPS_BET);
                            humanPlayer.addToBet(chipBtn.getChip());
                        }
                    }
                }
            });
        }

        // UNDO BUTTON
        gamePage.getTablePanel().getUserInterfacePanel().getUndoBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                humanPlayer.popBet();
            }
        });

        // DELETE BUTTON
        gamePage.getTablePanel().getUserInterfacePanel().getDeleteBetBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                humanPlayer.deleteBet();
            }
        });
    }


    /**
     * Connect Observers and Observables and initialize player panels.
     */
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
        game.getDealer().addObserver(gamePage.getTablePanel().getDealerPanel());

        // UserInterfacePanel observes HumanPlayer
        game.getHumanPlayer().addObserver(gamePage.getTablePanel().getUserInterfacePanel());
    }

    /***********************
     * GETTERS
     * *********************/

    public GamePage getGamePage() {
        return gamePage;
    }

    public Game getGame() {
        return game;
    }
}

