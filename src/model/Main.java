package model;

import model.authentication.Authentication;
import model.db.Database;
import model.global.User;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Init the Database
        Database db = Database.getInstance();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Type a username: ");
        String username = scanner.nextLine();

        Authentication auth = new Authentication();
        auth.loginOrRegister(username);
    }
}
