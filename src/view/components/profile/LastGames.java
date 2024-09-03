package view.components.profile;
import model.db.Database;
import model.global.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static misc.Constants.BG_COLOR;


public class LastGames extends JPanel {
    private Box list;  // Assuming you're using a Box to hold the list items

    public LastGames(User user) {
        setBackground(null);

        // Initialize the list container
        list = Box.createVerticalBox();  // Use a vertical BoxLayout to stack items
        list.setBackground(BG_COLOR);

        // Retrieve the games for the user
        List<String> games = Database.getInstance().getUserGames(user.getUsername());

        if (games.isEmpty()) {
            return;
        }

        // Add each game to the list
        games.forEach(game -> {
            list.add(new GameListItem(game));  // Adding game item
        });

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBackground(BG_COLOR);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(800, 400)); // Set preferred size as needed
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setBlockIncrement(50);

        // Add the scroll pane to this panel
        add(scrollPane, BorderLayout.CENTER);  // Add scroll pane to the center
    }
}

