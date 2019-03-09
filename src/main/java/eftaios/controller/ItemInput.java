package eftaios.controller;

import eftaios.model.Model;
import eftaios.model.avatars.Player;
import eftaios.model.decks.drawables.Item;

public class ItemInput extends UserInput {

    /**
     * 
     */
    private static final long serialVersionUID = -5269482122272646170L;
    private Item itemToBeUsed;
    
    public ItemInput(Item itemToBeUsed) {
        this.itemToBeUsed=itemToBeUsed;
    }

    /**
     * Function that calls the itemRequest method for the current Player, by passing the requested Item 
     * @return void
     * @param Model, currentPlayer
     */
    @Override
    public void executeCommand(Model model, Player player) {
        model.itemRequest(player, itemToBeUsed);
    }

}
