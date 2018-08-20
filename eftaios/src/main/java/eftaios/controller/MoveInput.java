package eftaios.controller;

import eftaios.model.Model;
import eftaios.model.avatars.Player;

public class MoveInput extends UserInput {

    /**
     * 
     */
    private static final long serialVersionUID = -3437030475541720042L;
    private String destination;
    
    public MoveInput(String destination) {
        this.destination = destination;
    }

    /**
     * Function that calls the moveRequest method for the current Player, by passing the destination 
     * @return void
     * @param Model, currentPlayer
     */
    @Override
    public void executeCommand(Model model,Player player){
        model.moveRequest(player, destination);
    }

}
