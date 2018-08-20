package eftaios.controller;

import eftaios.model.Model;
import eftaios.model.avatars.Player;

public class LogInput extends UserInput {

    /**
     * 
     */
    private static final long serialVersionUID = 7414855416431384491L;

    public LogInput() {
    }

    @Override
    public void executeCommand(Model model, Player player) {
        model.logRequest();
    }

}
