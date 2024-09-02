import controller.Controller;
import model.db.Database;

public class JBlackJack {
    public static void main(String[] args) {
        Database.getInstance();
        Controller.getInstance();
    }
}
