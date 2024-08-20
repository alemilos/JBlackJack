package view.components.game;

import model.game.enums.Chips;
import view.ui.IconButton;

import javax.swing.*;

public class ChipButton {

    private IconButton iconBtn ;
    private Chips chip;

    public ChipButton(Chips chip){
        iconBtn = new IconButton(new ImageIcon("./assets/icons/chips/" + chip.name().toLowerCase() + ".png"));
        iconBtn.setEnabled(false);

        this.chip = chip;
    }

    public IconButton getIconBtn() {
        return iconBtn;
    }

    public Chips getChip() {
        return chip;
    }
}
