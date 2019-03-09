package eftaios.network.rmi.commons;

import eftaios.controller.RMICreateGameInput;
import eftaios.view.ClientMenu;
import eftaios.view.GameInfo;
import eftaios.view.cli.CommandLineInterface;

public class OnlineCommandLineInterface extends CommandLineInterface {
    
    /**
     * Class created only for overwritting the handleStartMenu to pass a RMICreateGameInput to the controller
     */

    public OnlineCommandLineInterface(ClientMenu menu) {
        super(menu);
    }
    
    @Override
    protected synchronized void handleStartMenu() {
        GameInfo gameinfo=menu.ShowStartMenu();
        if(gameinfo==null) {
            menu.writeMessage("fatal error loading game... retry",false);
            gameinfo=menu.ShowStartMenu();
        }
        setChanged();
        notifyObservers(new RMICreateGameInput(gameinfo));
    }
    
    /**
     * Function that output message on the console and the matchlog.txt file
     * @return void
     * @param message to be written as String, boolean flag to write on matchlog.txt
     */
    @Override
    public void writeMessage(String message, boolean writeToEveryone){
        menu.writeMessage(message, writeToEveryone);
    }
    
    /**
     * Function that starts the CLi thread
     * @return void
     * @param nothing
     */
    @Override
    public void run() {
        menu.ShowIntro();
       if(menu.PlayStart())
        handleStartMenu();
    }

}
