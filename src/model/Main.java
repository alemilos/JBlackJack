package model;

import controller.Controller;
import model.db.Database;

public class Main {
    public static void main(String[] args) {
        // Init the Database
        Database.getInstance();
        // Init Controller
        Controller.getInstance();
    }
}
