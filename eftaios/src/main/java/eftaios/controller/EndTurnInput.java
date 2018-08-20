package eftaios.controller;

import java.util.ArrayList;
import java.util.List;

import eftaios.model.Model;
import eftaios.model.avatars.Player;

public class EndTurnInput extends UserInput {

    /**
     * 
     */
    private static final long serialVersionUID = 4096017132977889435L;
    private List<String> log = new ArrayList<String>();

    public EndTurnInput(List<String> log) {
        this.log = log;
    }

    /**
     * Function that calls the endTurn method for the current Player 
     * @return void
     * @param Model, currentPlayer
     */
    @Override
    public void executeCommand(Model model, Player player) {
        model.endTurn(player,log);
    }
    
    public List<String> getLog() {
        return log;
    }

}
