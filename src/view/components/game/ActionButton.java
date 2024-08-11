package view.components.game;

import view.ui.IconButton;

import javax.swing.*;

public class ActionButton {

    private IconButton iconButton;
    private String actionName;

    public ActionButton(String actionName){
        this.actionName = actionName;
        iconButton = new IconButton(new ImageIcon("./assets/buttons/actions/" +actionName.toLowerCase() + ".png"));
        iconButton.setEnabled(false);
    }

    public String getActionName() {
        return actionName;
    }

    public IconButton getIconButton() {
        return iconButton;
    }
}
