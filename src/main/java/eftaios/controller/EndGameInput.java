package eftaios.controller;

import java.util.ArrayList;
import java.util.List;

import eftaios.model.Model;
import eftaios.model.avatars.Player;

public class EndGameInput extends UserInput {

    /**
     * 
     */
    private static final long serialVersionUID = -4053290410544270360L;
    private List<String> log = new ArrayList<String>();

    public EndGameInput(List<String> log) {
        this.log = log;
    }
    
    /**
     * Function that calls the endGame method for the current Player
     * @return void
     * @param Model, currentPlayer
     */
    @Override
    public void executeCommand(Model model, Player player) {
        model.endGame(player,log);
    }
    
    public List<String> getLog() {
        return log;
    }

}
