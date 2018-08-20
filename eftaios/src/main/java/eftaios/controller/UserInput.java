package eftaios.controller;

import java.io.Serializable;

import eftaios.model.Model;
import eftaios.model.avatars.Player;

public abstract class UserInput implements Serializable{

    
    
    private static final long serialVersionUID = -969089267855447194L;
   
    public UserInput(){
        
    }
    
    /**
     * Function that executes a Model command by passing the current Player
     * @return void
     * @param model, currentPlayer
     */
    public abstract void executeCommand(Model model,Player player);

}
