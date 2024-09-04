package view.components.game;

import model.game.models.Game;
import model.game.enums.Actions;
import model.game.enums.Chips;
import model.game.models.player.HumanPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static misc.Constants.BG_COLOR;
import static misc.Updates.*;

/**
 * The UserInterfacePanel extends JPanel and listens to changes made by observables.
 * It manages the updates of bets and actions.
 * When an action can be performed the corresponding button is lightened, and the same works for the bets.
 */
public class UserInterfacePanel extends JPanel implements Observer {

    private JPanel actionsContainer;
    private JPanel chipsContainer;

    private List<ActionButton> actionButtons;
    private List<ChipButton> chipButtons;

    private JButton undoBtn;
    private JButton deleteBetBtn;


    public UserInterfacePanel(){
        setLayout(new BorderLayout());

        actionsContainer = new JPanel(new BorderLayout());
        chipsContainer = new JPanel(new BorderLayout());

        actionButtons = new ArrayList<>();
        chipButtons = new ArrayList<>();

        drawUserActions();
        drawUserChips();

        add(actionsContainer, BorderLayout.CENTER);
        add(chipsContainer, BorderLayout.SOUTH);
    }

    private void drawUserActions(){
        JPanel container = new JPanel(new FlowLayout());
        container.setBackground(BG_COLOR);

        List<Actions> actions = new ArrayList<>(List.of(Actions.values()));

        actions.forEach( action -> {
            ActionButton actionBtn = new ActionButton(action);

            actionButtons.add(actionBtn);
            container.add(actionBtn.getIconButton());
        });

        actionsContainer.add(container, BorderLayout.CENTER);
    }

    private void drawUserChips(){
        JPanel container = new JPanel(new FlowLayout());
        container.setBackground(BG_COLOR);

        List<Chips> chips = new ArrayList<>(List.of(Chips.values()));

        chips.forEach(chip -> {
            ChipButton chipBtn = new ChipButton(chip);

            chipButtons.add(chipBtn);
            container.add(chipBtn.getIconBtn());
        });

        // Draw Undo Button
        undoBtn = new JButton();
        undoBtn.setIcon(new ImageIcon(new ImageIcon("./assets/icons/undo.png").getImage().getScaledInstance(24,24,Image.SCALE_SMOOTH)));

        // Draw Delete Bet button
        deleteBetBtn = new JButton();
        deleteBetBtn.setIcon(new ImageIcon(new ImageIcon("./assets/icons/bin.png").getImage().getScaledInstance(24,24,Image.SCALE_SMOOTH)));

        container.add(undoBtn);
        container.add(deleteBetBtn);

        chipsContainer.add(container, BorderLayout.CENTER);
    }

    public JButton getUndoBtn() {
        return undoBtn;
    }

    public JButton getDeleteBetBtn() {
        return deleteBetBtn;
    }

    /** Enable all action buttons that are in given list. */
    private void enableActionButtons(){
        HumanPlayer player = Game.getInstance().getHumanPlayer();
        List<Actions> actions = player.getAvailableActions();

        actionButtons.forEach(actionBtn -> {
            if (actions.contains(actionBtn.getAction())){
                actionBtn.getIconButton().setEnabled(true);
            }else{
                actionBtn.getIconButton().setEnabled(false);
            }
        });
    }

    private void disableActionButtons(){
        actionButtons.forEach(actionBtn -> actionBtn.getIconButton().setEnabled(false));
    }

    public List<ChipButton> getChipButtons() {
        return chipButtons;
    }

    public List<ActionButton> getActionButtons() {
        return actionButtons;
    }

    private void disableBetButtons(){
        chipButtons.forEach(chipBtn -> chipBtn.getIconBtn().setEnabled(false));
        deleteBetBtn.setEnabled(false);
        undoBtn.setEnabled(false);
    }

    private void enableBetButtons(){
        HumanPlayer player = Game.getInstance().getHumanPlayer();

        if(Game.getInstance().isBetPhase()) {
            chipButtons.forEach(chipBtn -> {
                chipBtn.getIconBtn().setEnabled(player.canBet(chipBtn.getChip()));
            });

            deleteBetBtn.setEnabled(!player.getBet().isEmpty());
            undoBtn.setEnabled(!player.getBet().isChipAddedQueueEmpty());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == BET_START || arg == BET_UPDATE) enableBetButtons();

        if (arg == BET_FINISH) disableBetButtons();

        if (arg == TURN_START || arg == TURN_UPDATE) {
            enableActionButtons();
        }
        if (arg == TURN_FINISH) {
            disableActionButtons();
        }

    }
}
