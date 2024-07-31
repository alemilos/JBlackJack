package controller;

import model.game.Game;
import view.pages.GamePage;
import view.pages.ProfilePage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {

    private static GameController instance;

    private GamePage gamePage;

    private GameController(){
        gamePage = new GamePage();

        // TODO: retrieve chips / actions from model and call gamePage.drawUserInterface(actions, chips);

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

