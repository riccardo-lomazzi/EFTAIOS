package eftaios.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import eftaios.ExceptionLogger;
import eftaios.model.Model;
import eftaios.model.avatars.Player;
import eftaios.network.rmi.commons.PlayerConnectionData;
import eftaios.network.rmi.commons.RemoteController;
import eftaios.network.rmi.commons.RemoteControllerInterface;
import eftaios.network.rmi.server.ServerInterface;

public class RMIConnectToGameInput extends UserInput {

    /**
     * 
     */
    private static final long serialVersionUID = 4707446072406798370L;
    private String serverDomain;
    private ServerInterface server;
    
    public RMIConnectToGameInput(String serverDomain) {
        this.serverDomain=serverDomain;
    }

    /**
     * Function that executes the connection method for a RemoteController client
     * by searching for the server port (getRegistry) and 
     * registering into the clients array of the server (server.addClient)
     * @return PlayerConnectionData with connection info (server name, player id and updated model)
     * @param a client identified by a class that implements a Remote child (RemoteControllerInterface)
     */
    public PlayerConnectionData executeCommand(RemoteController client) {
        try {
            Registry registry;
            registry = LocateRegistry.getRegistry(7777);
            
            server = (ServerInterface) registry.lookup(serverDomain);
           
            return server.addClient((RemoteControllerInterface)UnicastRemoteObject.exportObject(client,0));
            
        } catch (NotBoundException| RemoteException e) {
            ExceptionLogger.info(e);
        }
        return null;
    }



    @Override
    public void executeCommand(Model model, Player player) {
        //this method is just a stub
    }    

}
