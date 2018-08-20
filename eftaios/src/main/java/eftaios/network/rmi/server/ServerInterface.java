package eftaios.network.rmi.server;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import eftaios.model.Model;
import eftaios.network.rmi.commons.PlayerConnectionData;
import eftaios.network.rmi.commons.RemoteControllerInterface;

public interface ServerInterface extends Remote{
     
        public PlayerConnectionData addClient(RemoteControllerInterface client) throws RemoteException;
        
        public void readyToPlay() throws RemoteException;
        
        public void removeClient(RemoteControllerInterface client, Model clientModel) throws RemoteException;

        public void notifyToEveryone(String message) throws RemoteException;
        
        public void updateModel(Model model) throws RemoteException;
        
        public void dispatchMessage(String message) throws RemoteException;

        public void saveGame(PlayerConnectionData pcd) throws RemoteException, IOException;
    
}
