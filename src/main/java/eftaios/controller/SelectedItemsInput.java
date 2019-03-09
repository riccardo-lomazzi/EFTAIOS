package eftaios.controller;

import java.util.List;

import eftaios.model.Model;
import eftaios.model.avatars.Player;
import eftaios.model.decks.drawables.Item;

public class SelectedItemsInput extends UserInput {

    
    /**
     * 
     */
    private static final long serialVersionUID = 2207919900518887879L;
    private List<Item> itemsList;
    
    public SelectedItemsInput(List<Item> itemsList) {
        this.itemsList=itemsList;
    }

    /**
     * Function that calls the setItemRequest method for the current Player, by passing the itemsList
     * @return void
     * @param Model, currentPlayer
     */
    @Override
    public void executeCommand(Model model, Player player) {
        model.setItemRequest(player, itemsList);
    }

}
