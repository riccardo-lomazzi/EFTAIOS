package eftaios.controller;

import eftaios.model.Model;
import eftaios.model.avatars.Player;

public class DrawInput extends UserInput {
    
    /**
     * 
     */
    private static final long serialVersionUID = -4042216441295525656L;


    public DrawInput() {
    }

    /**
     * Function that calls the drawCardRequest method for the current Player
     * @return void
     * @param Model, currentPlayer
     */
    @Override
    public void executeCommand(Model model, Player player) {
        model.drawCardRequest(player);
    }

}
