package eftaios.controller;

import eftaios.model.Model;
import eftaios.model.avatars.Player;

public class SocketConnectToGameInput extends UserInput{

    /**
     * 
     */
    private static final long serialVersionUID = 5248965957425302192L;
    private int selectedGame;

    public SocketConnectToGameInput(int selectedGame) {
        this.selectedGame=selectedGame;
    }

    @Override
    public void executeCommand(Model model, Player player) {
        /*
         * this user input does not modify the model
         * it only connects the client to the server 
         */
    }

    public int getSelectedGame() {
        return selectedGame;
    }

}
