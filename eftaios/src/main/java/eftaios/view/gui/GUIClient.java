package eftaios.view.gui;

import java.util.ArrayList;
import java.util.Observable;

import eftaios.model.Model;
import eftaios.model.events.ConnectedToGameEvent;
import eftaios.model.events.GameEvent;
import eftaios.network.socket.OnlineGameInfo;

public class GUIClient extends GraphicUserInterface {


    public GUIClient(OnlineGUIClientMenu onlineGUIClientMenu) {
        super(onlineGUIClientMenu);
        onlineMenu.setView(this);
        running = true;
        matches = new ArrayList<OnlineGameInfo>(0); 
    }
    
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
    
    @Override
    public void run() {
        running = true;
        onlineMenu.ShowIntro();
    }

}
