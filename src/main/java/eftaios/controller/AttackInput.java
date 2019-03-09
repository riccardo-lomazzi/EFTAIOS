package eftaios.controller;

import eftaios.model.Model;
import eftaios.model.avatars.Player;

public class AttackInput extends UserInput {

    /**
     * 
     */
    private static final long serialVersionUID = 6493479800950436438L;

    public AttackInput() {
        
    }
    
    /**
     * Function that calls the attackRequest method for the current Player
     * @return void
     * @param Model, currentPlayer
     */
    @Override
    public void executeCommand(Model model, Player player) {
        model.attackRequest(player);
    }

}
