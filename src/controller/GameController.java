package controller;

import model.game.Game;
import view.pages.GamePage;
import view.pages.ProfilePage;

public class GameController {

    private static GameController instance;

    private GamePage gamePage;

    private GameController(){
        gamePage = new GamePage();
    }

    public static GameController getInstance() {
        if (instance == null){
            instance = new GameController();
        }
        return instance;
    }
}
