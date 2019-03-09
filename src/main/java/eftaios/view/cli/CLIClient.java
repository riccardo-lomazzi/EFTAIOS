package eftaios.view.cli;

import java.util.ArrayList;
import java.util.Observable;

import eftaios.controller.SocketConnectToGameInput;
import eftaios.controller.UserInput;
import eftaios.model.Model;
import eftaios.model.events.ConnectedToGameEvent;
import eftaios.model.events.GameEvent;
import eftaios.network.socket.OnlineGameInfo;

public class CLIClient extends CommandLineInterface {

    private UserInput input;
    private OnlineCLIClientMenu onlineMenu;

    public CLIClient( OnlineCLIClientMenu clientOnlineMenu) {
        super(clientOnlineMenu);
        onlineMenu = clientOnlineMenu;
        running = true;
        matches = new ArrayList<OnlineGameInfo>(0); 
    }
    
    /**
     * Function that everytime the model is updated, the view captures the model and stores it
     * also stores the game and the currentPlayer.
     * After that, it executes the command by being the EventVisitor of the GameEvent Visitor Accepter sent as Object
     * @param Observable obs (Model), Object arg (GameEvent)
     * @return void
     */
    @Override
    public void update(Observable obs, Object arg){
        //saves the game locally
        model=(Model)obs;
        
        game=model.getGame();
        //do something with a specific event
        if(isAcceptableInput(arg))
            ((GameEvent)arg).acceptVisit(this);
    }

    private boolean isAcceptableInput(Object arg) {
        return isGameVisibleToThisPlayer()||(arg instanceof ConnectedToGameEvent);
    }

    private boolean isGameVisibleToThisPlayer() {
        return model!=null&&game!=null&&connectedPlayer!=null&&connectedPlayer.equals(model.getGame().getCurrentPlayer());
    }
    
    /**
     * Function starts the CLIClient thread. Shows the intro and if the user can play, start the intro menu
     * @param nothing
     * @return void
     */
    @Override
    public void run() {
        setChanged();
        /*
         * connecting to the game with a negative number 
         * will refresh the match list without adding the client to a game
         */
        notifyObservers(new SocketConnectToGameInput(-1));
        onlineMenu.ShowIntro();
        if(onlineMenu.PlayStart()) {
            if(onlineMenu.ChooseGame())
                handleStartOnlineMenu();
            else {
            setChanged();
            notifyObservers(new SocketConnectToGameInput(0));
            }
        }
        else
            running = false;
    }

    protected void handleStartOnlineMenu() {
            input = onlineMenu.ShowOnlineStartMenu(matches);
            while(input==null) {
                input = onlineMenu.ShowOnlineStartMenu(matches);
            }
            setChanged();
            notifyObservers(input);
    }

    protected void showEndOnlineMenu() {
        onlineMenu.writeMessage("***Game Ended***", false);
        run();
    }
    
    @Override
    public void visitEvent(ConnectedToGameEvent connectedToGameEvent) {
        onlineMenu.writeMessage(connectedToGameEvent.getMessage(), false);
    }
    
}
