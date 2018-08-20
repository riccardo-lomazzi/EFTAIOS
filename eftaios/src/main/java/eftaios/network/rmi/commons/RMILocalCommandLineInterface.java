package eftaios.network.rmi.commons;

import java.util.Observable;

import eftaios.model.events.GameEvent;
import eftaios.view.ClientMenu;
import eftaios.view.cli.CommandLineInterface;

public class RMILocalCommandLineInterface extends CommandLineInterface {

    public RMILocalCommandLineInterface(ClientMenu menu) {
        super(menu);
    }
    
    public void callPlayerMenu(){
        showPlayerMenu();
    }
    
    @Override
    public void update(Observable obs, Object arg){
        //saves the game locally
        model=(ServerModel)obs;
        game=model.getGame();
        //saves the currentPlayer
        connectedPlayer=game.getCurrentPlayer();
        //do something with a specific event
        if(arg!=null)
        ((GameEvent)arg).acceptVisit(this);
    }
    
    @Override
    public void writeMessage(String message, boolean writeToEveryone){
        menu.writeMessage(message, writeToEveryone);
    }
    
    @Override
    protected void showEndMenu() {
        menu.writeMessage("***Game Ended***", false);
    }

}
