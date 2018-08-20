package eftaios.network.rmi.server;

import java.rmi.RemoteException;
import java.util.NoSuchElementException;

import eftaios.ExceptionLogger;

public class ServerMain {
    private static ServerWithRemoteImplementation swri;
    
     public static void main(String[] args) {
         
         
         try{
             swri=new ServerWithRemoteImplementation("EFTAIOS_server");
            
             swri.startGame();
         }
         catch (RemoteException | NoSuchElementException e) {
          ExceptionLogger.info(e);
       } 
         
    }
     
     
    
}
