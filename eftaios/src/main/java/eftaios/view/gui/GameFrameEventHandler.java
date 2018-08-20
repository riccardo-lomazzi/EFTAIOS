package eftaios.view.gui;

import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import eftaios.controller.AttackInput;
import eftaios.controller.DrawInput;
import eftaios.controller.EndTurnInput;
import eftaios.controller.ItemInput;
import eftaios.controller.LogInput;
import eftaios.controller.MoveInput;
import eftaios.model.avatars.AlienPlayer;
import eftaios.model.avatars.Player;
import eftaios.model.board.SafeSector;
import eftaios.model.decks.drawables.Item;
import eftaios.model.decks.drawables.SpotLightItem;
import eftaios.view.ClientMenu;
import eftaios.view.View;

public class GameFrameEventHandler extends FrameEventHandler {


    public GameFrameEventHandler(GameFrame frame, View view) {
        super(view);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String componentName = ((JComponent) e.getSource()).getName();
        executeCase(componentName);
    }

    protected void executeCase(String componentName) {
        switch (componentName) {
            case "attack":
                attackCheck(view.getCurrentPlayer());
                break;
            case "log":
                view.ChangeAndNotify(new LogInput());
                break;
            case "draw":
                drawCheck(view.getCurrentPlayer());
                break;
            case "end":
                endCheck(view.getCurrentPlayer());
                break;
            case "1":
                itemIntput(Integer.parseInt(componentName));
                break;
            case "2":
                itemIntput(Integer.parseInt(componentName));
                break;
            case "3":
                itemIntput(Integer.parseInt(componentName));
                break;
            default:
                if (ClientMenu.isValidID(componentName))
                    checkMove(componentName);
                break;
        }
    }

    private void itemIntput(int parseInt) {
        Item itemToBeUsed = view.getCurrentPlayer().getItems().get(parseInt-1);
        if (itemToBeUsed instanceof SpotLightItem) {
            String inputValue;
            do {
            inputValue = JOptionPane.showInputDialog("Spotlight:\nPlease insert a valid sector id:");
            }while(!ClientMenu.isValidID(inputValue.toUpperCase()));
            ((SpotLightItem) itemToBeUsed).setSector(inputValue);
        } else
            view.ChangeAndNotify(new ItemInput(itemToBeUsed));
    }

    protected void attackCheck(Player player) {
        if (player.hasAlreadyMoved() && !player.alreadyAttacked() && !player.hasAlreadyDrawed()) {
            if(player instanceof AlienPlayer)
            view.ChangeAndNotify(new AttackInput());
        }
    }

    protected void drawCheck(Player player) {
        if (player.hasAlreadyMoved() && !player.alreadyAttacked() && !player.hasAlreadyDrawed() || player.getPosition() instanceof SafeSector)
            view.ChangeAndNotify(new DrawInput());
    }

    private void checkMove(String componentName) {
        view.ChangeAndNotify(new MoveInput(componentName));
    }

    private void endCheck(Player player) {
        if (player.hasAlreadyMoved() && (player.hasAlreadyDrawed() || player.alreadyAttacked() || player.getPosition() instanceof SafeSector || player.isSedated()))
            view.ChangeAndNotify(new EndTurnInput(view.getMenu().getLogToString()));
    }
}
