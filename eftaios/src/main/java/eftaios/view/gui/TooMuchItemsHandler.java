package eftaios.view.gui;

import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import eftaios.controller.ItemInput;
import eftaios.model.decks.drawables.Item;
import eftaios.model.decks.drawables.SpotLightItem;
import eftaios.view.ClientMenu;
import eftaios.view.View;

public class TooMuchItemsHandler extends FrameEventHandler {

    private JRadioButton useBox;
    private JRadioButton discardBox;
    private List<Item> items;
    private boolean valid;
    private GameFrame frame;

    public TooMuchItemsHandler(View view, List<Item> items, JRadioButton useBox, JRadioButton discardBox ,GameFrame frame) {
        super(view);
        this.items = items;
        this.useBox = useBox;
        this.discardBox = discardBox;
        valid = true;
        this.frame = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String componentName = ((JComponent) e.getSource()).getName();
        switch (componentName) {
            case "1":
                itemIntput(Integer.parseInt(componentName));
                break;
            case "2":
                itemIntput(Integer.parseInt(componentName));
                break;
            case "3":
                itemIntput(Integer.parseInt(componentName));
                break;
            case "4":
                itemIntput(Integer.parseInt(componentName));
                break;
            default:
                break;
        }
    }
        private void itemIntput(int parseInt) {
            Item itemToBeUsed = items.get(parseInt-1);
            if(useBox.isSelected() && valid) {
                if (itemToBeUsed instanceof SpotLightItem) {
                    String inputValue;
                    do {
                    inputValue = JOptionPane.showInputDialog("Spotlight:\nPlease insert a valid sector id:");
                    }while(!ClientMenu.isValidID(inputValue.toUpperCase()));
                    ((SpotLightItem) itemToBeUsed).setSector(inputValue);
                } else
                    view.ChangeAndNotify(new ItemInput(itemToBeUsed));
            }else{
                if(discardBox.isSelected()) {
                    items.remove(parseInt-1);
                }
            }
            valid = false;
            frame.resetItemsPanels();
        }
   
}