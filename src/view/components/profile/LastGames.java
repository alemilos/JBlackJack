package view.components.profile;
import model.db.Database;
import model.global.User;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LastGames extends JPanel {

    public LastGames(User user){
        setBackground(null);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        List<String> games = Database.getInstance().getUserGames(user.getUsername());

        if (games.isEmpty()){
            System.out.println("No games for given user");
        }

        games.forEach(game -> {
            container.add(new GameListItem(game));
            container.add(Box.createRigidArea(new Dimension(0,10)));
        });

        add(container);
    }
}
