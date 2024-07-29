package model;

import controller.AuthController;
import model.authentication.Authentication;
import model.db.Database;
import model.global.User;
import view.AuthPage;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Init the Database
        Database db = Database.getInstance();

        // Manage the Authentication flow
        AuthController authController = AuthController.getInstance();

    }
}
