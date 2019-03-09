package eftaios.network.rmi.client;

import java.rmi.RemoteException;

import eftaios.ExceptionLogger;
import eftaios.controller.RMIConnectToGameInput;
import eftaios.network.rmi.commons.RMILocalCommandLineInterface;
import eftaios.network.rmi.commons.RemoteController;
import eftaios.view.cli.LocalCLIClientMenu;


public class ClientWithRemoteImplementation implements Runnable{

    private RMILocalCommandLineInterface cli;
    private RemoteController cwr;
    private String serverDomain;
    
    public ClientWithRemoteImplementation(String serverDomain) {
        this.serverDomain=serverDomain;

    }

    /**
     * Function that handles the connection to the server, by creating a RemoteController
     * with a LocalCommandLineInterface 
     * @return void
     * @param nothing
     */
    private void connectToGame() {
        //console creation for the player
        cli=new RMILocalCommandLineInterface(new LocalCLIClientMenu(System.in, System.out));
        //controller creation for connecting to the serverGame
        cwr=new RemoteController(cli, new RMIConnectToGameInput(serverDomain));
        //executing the connect command returns the model to the this client/player's remote controller.
        //everytime the server modifies its model, it will transfer the model to every player
        try {
            cwr.executeConnectCommand();
        } catch (RemoteException e) {
            ExceptionLogger.info(e);
        }
    }
    
    /**
     * Function that returns the RemoteController
     * @return void
     * @param RemoteController
     */
    public RemoteController getRemoteController(){
        return cwr;
    }

    /**
     * Function that starts the client thread
     * @return void
     * @param nothing
     */
    @Override
    public void run() {
        connectToGame();
    }
    
   
    
   
}
