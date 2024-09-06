package view.components.profile;
import model.db.Database;
import model.global.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static controller.Constants.BG_COLOR;


public class LastGames extends JPanel {
    private Box list;

    public LastGames(User user) {
        setBackground(null);

        list = Box.createVerticalBox();
        list.setBackground(BG_COLOR);

        List<String> games = Database.getInstance().getUserGames(user.getUsername());

        if (games.isEmpty()) {
            return;
        }

        games.forEach(game -> {
            list.add(new GameListItem(game));  // Adding game item
        });

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBackground(BG_COLOR);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setBlockIncrement(50);

        add(scrollPane, BorderLayout.CENTER);
    }
}

