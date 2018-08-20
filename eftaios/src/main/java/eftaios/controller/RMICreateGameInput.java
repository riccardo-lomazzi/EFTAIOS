package eftaios.controller;

import eftaios.model.Model;
import eftaios.model.avatars.Player;
import eftaios.view.GameInfo;

public class RMICreateGameInput extends CreateGameInput {

    /**
     * 
     */
    private static final long serialVersionUID = -5537560976657013711L;

    public RMICreateGameInput(GameInfo gameinfo) {
        super(gameinfo);
    }
    
    /**
     * Function that calls the super executeCommand method for the current Player
     * to create a game, and then notifies the server thread that is has been created
     * @return void
     * @param Model, currentPlayer
     */
    @Override
    public void executeCommand(Model model, Player player){
        super.executeCommand(model, player);
        synchronized(model){
            model.notifyAll();
        }
    }

}
