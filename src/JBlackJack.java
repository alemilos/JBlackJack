import controller.Controller;
import model.db.Database;

public class JBlackJack {
    public static void main(String[] args) {
        // Initialize the database
        Database.getInstance();

        // Initialize the controller
        Controller.getInstance();
    }
}
