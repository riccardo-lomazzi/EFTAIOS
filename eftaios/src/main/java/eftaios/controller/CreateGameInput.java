package eftaios.controller;

import eftaios.model.Model;
import eftaios.model.avatars.Player;
import eftaios.view.GameInfo;

public class CreateGameInput extends UserInput {

    /**
     * 
     */
    private static final long serialVersionUID = -4255644375514968879L;
    protected GameInfo gameinfo;
    
    public CreateGameInput(GameInfo gameinfo) {
        this.gameinfo=gameinfo;
    }

    /**
     * Function that calls the createGame method for the current Player, by using the gameinfo got in the menu
     * @return void
     * @param Model, currentPlayer
     */
    @Override
    public void executeCommand(Model model, Player player) {
        model.createGame(gameinfo.getNumberOfPlayers(), gameinfo.getRules(), gameinfo.getMapPath());
    }

    public GameInfo getGameinfo() {
        return gameinfo;
    }
}
