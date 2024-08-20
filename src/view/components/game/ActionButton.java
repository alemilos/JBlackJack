package view.components.game;

import model.game.enums.Actions;
import view.ui.IconButton;

import javax.swing.*;

public class ActionButton {

    private IconButton iconButton;

    private Actions action;

    public ActionButton(Actions action){
        this.action = action;
        iconButton = new IconButton(new ImageIcon("./assets/buttons/actions/" + action.name().toLowerCase() + ".png"));
        iconButton.setEnabled(false);
    }

    public IconButton getIconButton() {
        return iconButton;
    }

    public Actions getAction() {
        return action;
    }
}
