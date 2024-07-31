package controller;

import model.game.Game;
import model.game.enums.Actions;
import model.game.enums.Chips;
import view.pages.GamePage;
import view.pages.ProfilePage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameController {

    private static GameController instance;

    private GamePage gamePage;

    private GameController(){
        gamePage = new GamePage();

        List actions = new ArrayList<>(Arrays.asList(Actions.values()));
        List chips = new ArrayList<>(Arrays.asList(Chips.values()));

        gamePage.drawUserInterface(actions, chips);

        addActionListeners();
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

    private void addActionListeners(){
        gamePage.getLeaveBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Leaving Game");
                gamePage.dispose();
                resetInstance();
                Controller.getInstance().goToHome();
            }
        });
    }
}

