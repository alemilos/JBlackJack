package controller;

import controller.gamephases.BetPhaseController;
import controller.gamephases.CardsDistributionController;
import controller.gamephases.GamePhaseManager;
import controller.gamephases.UsersActionsController;
import model.game.Game;
import model.game.enums.Actions;
import model.game.enums.Chips;
import model.game.enums.Ranks;
import model.game.enums.Suits;
import model.game.models.player.AIPlayer;
import model.game.models.player.HumanPlayer;
import model.game.models.player.Player;
import model.game.models.standalones.Dealer;
import model.global.User;
import view.components.game.PlayerPanel;
import view.pages.GamePage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import misc.Utils;

import javax.swing.*;

public class GameController {

    private static GameController instance;

    private GamePage gamePage;

    private Game game;

    private HumanPlayer humanPlayer;

    private Map<Player, PlayerPanel> mapPlayerToPanel;

    private GameController(){
        gamePage = new GamePage();

        User user = Controller.getUser();

        // Game Model initialization
        game = Game.getInstance();
        game.init(user, 2000);

        initMapPlayerToPanel();

        humanPlayer = game.getHumanPlayer();
        game.startGame();

        List<String> actions = Arrays.stream(Actions.values()).map(action->Utils.toCapitalizedString(action.toString(), "_", " ")).collect(Collectors.toList());
        List chips = new ArrayList<>(Arrays.asList(Chips.values()));

        gamePage.drawUserInterface(actions, chips);
        gamePage.drawInitialGameState(getPlayerPanels());

        addActionListeners();

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

    private void initMapPlayerToPanel(){
        mapPlayerToPanel = new HashMap<>();
        game.getPlayers().forEach(player -> {
            mapPlayerToPanel.put(player, new PlayerPanel(player));
        });
    }

    private List<PlayerPanel> getPlayerPanels(){
       ArrayList<PlayerPanel> playerPanels = new ArrayList<>();
       ArrayList<Player> players = new ArrayList<>(mapPlayerToPanel.keySet());

        for (Player player : players) {
            if (!(player instanceof HumanPlayer)) {
                playerPanels.add(mapPlayerToPanel.get(player));
            }
        }

        for (Player player : players) {
            if (player instanceof HumanPlayer) {
                playerPanels.add(2, mapPlayerToPanel.get(player)); // Make sure the human player is always 2nd.
                break;
            }
        }
       return playerPanels;
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

        gamePage.getActionButtons().forEach(actionBtn-> {
            actionBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Action clicked");
                }
            });
        });

        PlayerPanel playerPanel = (PlayerPanel)gamePage.getTablePanel().getUsersPanel().getComponent(2);
        for (int i = 0; i < gamePage.getChipButtons().size(); i++) {
            JButton chipBtn = gamePage.getChipButtons().get(i);
            Chips chip = Chips.values()[i];
            chipBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(humanPlayer.canBet(chip)){
                        humanPlayer.addToBet(chip);
                        playerPanel.updateTotalChipsPanel(humanPlayer.getBet().total());
                        playerPanel.updateLastChipPanel(chip.name().toLowerCase());
                    }
                }
            });
        }

        gamePage.getUndoBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                humanPlayer.popBet();
                playerPanel.updateTotalChipsPanel(humanPlayer.getBet().total());

                Chips chip = humanPlayer.getBet().peek();
                if (chip != null) {
                    playerPanel.updateLastChipPanel(chip.name().toLowerCase());
                }else{
                    playerPanel.updateLastChipPanel(null);
                }
            }
        });

        gamePage.getDeleteBetBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                humanPlayer.deleteBet();
                playerPanel.updateTotalChipsPanel(humanPlayer.getBet().total());
                playerPanel.updateLastChipPanel(null);
            }
        });
    }

    public void drawAIBet(Player player){
        PlayerPanel playerPanel = mapPlayerToPanel.get(player);
        playerPanel.updateTotalChipsPanel(player.getBet().total());
        Chips chip = player.getBet().peek();
        if (chip != null)
        playerPanel.updateLastChipPanel(chip.name().toLowerCase());
    }

    public void drawDealerCards(){
        Dealer dealer = Dealer.getInstance();

        dealer.getHand().getCards().forEach(card -> {
            Suits suit = card.lookupSuit();
            gamePage.getTablePanel().addDealerCard(card.lookupValue(), suit != null ? suit.toString() : null);
        });
    }

    public void drawPlayerCards(Player player){
       PlayerPanel playerPanel = mapPlayerToPanel.get(player);

       player.getHand().getCards().forEach(card -> {
           playerPanel.addCard(card.lookupValue(), card.lookupSuit().toString());
       });

       playerPanel.updateCardsTotalValue(player.getHand().softTotal());
    }

    /***********************
     * GETTERS
     * *********************/

    public GamePage getGamePage() {
        return gamePage;
    }
}

