package eftaios.network.rmi.commons;

import java.rmi.Remote;
import java.rmi.RemoteException;

import eftaios.model.Model;
import eftaios.network.rmi.server.ServerInterface;

public interface RemoteControllerInterface extends Remote {
    public void setModel(Model model) throws RemoteException;
    public void setServer(ServerInterface server) throws RemoteException;
    public PlayerConnectionData getPlayerConnectionData() throws RemoteException;
    public void executeConnectCommand() throws RemoteException;
    public void dispatchMessage(String message) throws RemoteException;
    public void blockActions() throws RemoteException;
    public void unlockActions() throws RemoteException;
    public void callPlayerMenuInCLI() throws RemoteException;
    public void unlockClient() throws RemoteException;
}
