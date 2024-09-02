package controller;

import model.authentication.Authentication;
import model.global.User;
import view.pages.AuthPage;

public class AuthController {

    private AuthPage authPage;

    private static AuthController instance;

    private User user;

    private AuthController(){
        this.authPage = new AuthPage();

        addActionListeners();
    }

    public static AuthController getInstance(){
        if (instance == null){
           instance = new AuthController();
        }
        return instance;
    }

    private void resetInstance(){
        instance = null;
    }

    public User getUser(){
        return this.user;
    }

    private void addActionListeners(){
        authPage.getSubmitBtn().addActionListener(e -> {
            user =  new Authentication().loginOrRegister(authPage.getUsernameInput().getText().trim());
            if (user != null){
                Controller.setUser(user);
                authPage.dispose();
                resetInstance();
                Controller.getInstance().goToHome();
            }
        });
    }
}
