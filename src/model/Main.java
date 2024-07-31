package model;

import controller.Controller;
import model.db.Database;
import view.pages.GamePage;

public class Main {
    public static void main(String[] args) {
        // Init the Database
        Database db = Database.getInstance();

        // Init Controller
        Controller controller = Controller.getInstance();


    }
}
