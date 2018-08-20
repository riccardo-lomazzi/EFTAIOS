package eftaios.network.rmi.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import eftaios.ExceptionLogger;
import eftaios.model.Model;
import eftaios.model.avatars.Player;
import eftaios.network.rmi.commons.OnlineCommandLineInterface;
import eftaios.network.rmi.commons.PlayerConnectionData;
import eftaios.network.rmi.commons.RemoteControllerInterface;
import eftaios.network.rmi.commons.ServerController;
import eftaios.network.rmi.commons.ServerGame;
import eftaios.network.rmi.commons.ServerModel;
import eftaios.network.rmi.commons.ServerModel.TypeOfPlayer;
import eftaios.view.cli.OnlineCLIClientMenu;


public class ServerWithRemoteImplementation extends UnicastRemoteObject implements ServerInterface {

    private static final long serialVersionUID = 814241814085659537L;
    //This is a list of the connected Clients. They are recognized by the input given.
    private List<RemoteControllerInterface> clients;
    private RemoteControllerInterface currentClient;
    private String serverName;
    private OnlineCommandLineInterface commandLineInterface;
    private boolean firstPlayer;
    private ServerModel serverModel;
    private ServerController controller;
    private List<Player> disconnectedPlayers;
    
    public ServerWithRemoteImplementation(String serverName, ServerModel serverModel) throws RemoteException{
        serverSetUp(serverName);
        this.serverModel=serverModel;
      //wait before starting
        checkReadyToStart();
  }
    
    public ServerWithRemoteImplementation(String serverName) throws RemoteException{
          serverSetUp(serverName);
    }
    
    public ServerModel getServerModel(){
        return this.serverModel;
    }
    
    private void serverSetUp(String serverName){
        this.serverName=serverName; //grabs the server declared nam
        serverModel=null; //initializes the model to null
        currentClient=null; //currentClient hasn't been chosen yet, so we'll set it to null
        clients=new ArrayList<RemoteControllerInterface>(0);  //declare the clients array as empty
        disconnectedPlayers=new ArrayList<Player>(0); //declare the disconnected clients array as empty
        firstPlayer=true;  //the first client to connect is the currentPlayer
        commandLineInterface= new OnlineCommandLineInterface(new OnlineCLIClientMenu(System.in, System.out));  //used to set up the game
        
        //Rebinding server to a name and port 7777
        try {
        Registry registry;
        registry = LocateRegistry.createRegistry(7777);  
        registry.rebind(serverName, this);
        }
        catch (RemoteException e) {
            ExceptionLogger.info(e);
        }
    }
    
    
    private void startRealGame() throws RemoteException{
      //creates a new model to be shared with clients
        serverModel=new ServerModel();
        //create the controller
        controller=new ServerController(serverModel, commandLineInterface);
        //let the model be observed by the cli of the server/host
        serverModel.addObserver(commandLineInterface);
        //let the command line be observed by the controller
        commandLineInterface.addObserver(controller);
        //start the command line thread
        Thread viewThread = new Thread(commandLineInterface);
        viewThread.start();  
        ////this snippet waits for the model to be actually initialized
        synchronized((ServerModel)controller.getModel()){
            while(!((ServerModel) controller.getModel()).isModelInitialized()){
                try{
                    ((ServerModel)controller.getModel()).wait();
                }
                catch(InterruptedException e){
                    break;
                }
            }
        }
        //copy the created model into the server
        serverModel=(ServerModel) controller.getModel();
        notifyToEveryone("Model created\n Waiting for players...");
        //wait before starting
        checkReadyToStart();
    }
    
    private void checkReadyToStart() throws RemoteException{
        synchronized(clients){
            while(!isGamersTotalNumberFullfilled()){
                try {
                    clients.wait();
                } catch (InterruptedException e) {
                    break;
                }
            }
            
        }
        dispatchMessage("Game started!");
      //now we can allow the first player to start the game
        currentClient.callPlayerMenuInCLI(); //call the menu in his console window
        currentClient.unlockActions(); //unlock its actions
        notifyToEveryone("Number of players reached. Match started!");
    }
    
    //checks if all the clients are connected BEFORE starting the game
    private synchronized boolean isGamersTotalNumberFullfilled(){
        return clients.size() == ((ServerGame)serverModel.getGame()).getListOfPlayers().size();
    }
    
    /**
     * Remote method invoked by the user when the connection has estabilished that checks
     *  if the total number of players has been reached.
     *  If yes, then wakes up the server thread.
     * @param nothing
     * @return void 
     * */
    public synchronized void readyToPlay() throws RemoteException{
     //then check if a player has been added. If so, wake up the server thread
            
            if(isGamersTotalNumberFullfilled()){
                synchronized(clients){
                    clients.notifyAll();
                }
            }
        
    }
    
    
    /**
     * Remote method invoked by the user when he wants to connect.
     * adds a RemoteControllerInterface Client to the server and returns connection data to the connected client.
     * If the client is the first to connect, let him receive the data with the currentPlayer, 
     * otherwise the next player in the array.
     * @param RemoteControllerInterface client that wants to connect: actually a stub of the RemoteController
     * @return PlayerConnectionData 
     * */
    @Override
    public synchronized PlayerConnectionData addClient(RemoteControllerInterface client) throws RemoteException {
        
        PlayerConnectionData temp;  //will be used to store data
        ServerModel newServerModel=serverModel; //cloning the model supposedly writes a new object
        //add a client
        clients.add(client); 
       
            //else notify to the client that other players must connect first
            client.dispatchMessage("Connected! Waiting for other players...");
       
//        and pass the current state of the model to the client
//        if it's the first player, then he's the currentPlayer
        if(firstPlayer) {
            firstPlayer=false;
            currentClient=client;
            temp= new PlayerConnectionData(newServerModel, this, newServerModel.getNextPlayer(TypeOfPlayer.CURRENT));
        }
        else
        //if he's another player, then return other data
            temp=new PlayerConnectionData(newServerModel, this, newServerModel.getNextPlayer(TypeOfPlayer.OTHER));
        //communicate to everyone if a player has been added
        notifyToEveryone(temp.getPlayerRemoteID() +" has joined the game");
        return temp;
    }
  

    /**
     * Remote method invoked by the user when he notify something to the server.
     * @param String message to write
     * @return void
     * */
    @Override
    public synchronized void notifyToEveryone(String message) throws RemoteException {
        commandLineInterface.writeMessage(message, true);
    }
    
    /**
     * Remote method invoked by the server to write on the clients console by invoking a dispatch message function on their stub
     * @param String message to write on client console
     * @return void
     * @throws RemoteException
     * */
    @Override
    public void dispatchMessage(String message) throws RemoteException{
        for(RemoteControllerInterface client:clients){
            client.dispatchMessage(message);
        }
    }

    /**
     * Remote method invoked by the client when he disconnects. His stub is removed by the clients array,
     * the model he sends gets the list of the players redone without him,
     * the currentmodel is replaced by his, and his ID is added to the list of the disconnected
     * @param RemoteControllerInterface client that disconnects, his model
     * @return void
     * @throws RemoteException
     * */
    @Override
    public synchronized void removeClient(RemoteControllerInterface client, Model clientModel)  throws RemoteException{
        Player remotePlayer=client.getPlayerConnectionData().getRemotePlayer();
        //remove player from clients connected array
        clients.remove(client);
        //remove the client from the model
        ((ServerGame)serverModel.getGame()).getListOfPlayers().remove(remotePlayer);
        //add the player to the disconnected ones.
        disconnectedPlayers.add(remotePlayer);
        //write it on server
        commandLineInterface.writeMessage(remotePlayer.getPlayerID() + " has left the game", true);
      //update the model with the client one
        updateModel(clientModel);
    }

    /**
     * Method that gets the server name
     * @param nothing
     * @return String name of the server
     * */
    public String getServerName() {
        return serverName;
    }
    
    /**
     * Method that sets the current Model
     * @param nothing
     * @return void
     * */
    private void setModel(Model model) {
        this.serverModel=(ServerModel) model;
      }

    /**
     * Remote method invoked by the client when his turn has ended, and the model on the server must be overwritten.
     * First, a checkup of the number of total players is done: if the currentPlayer is disconnected, gets a new currentPlayer.
     * Then the server's model is overwritten, and is passed to everyone.
     * Then the control is passed to the currentPlayer, but only if the match hasn't ended. If it is, the match ends.
     * @param Model model
     * @return void
     * @throws RemoteException
     * */
    @Override
    public void updateModel(Model model)  throws RemoteException{
        boolean matchEnded=checkPlayersNumber(model);
        //set current game model
            setModel(model);
            //pass it to everyone
            passModelToEveryone(serverModel);
            //block the other players
        if (!matchEnded){
            passControlToThePlayer(model.getGame().getCurrentPlayer());
        }
        else{
            Registry registry=LocateRegistry.getRegistry(7777);
            try {
                registry.unbind("EFTAIOS_server");
            } catch (NotBoundException e) {
                ExceptionLogger.info(e);
            }
        }
    }
    
    
    //true if match has ended, false if otherwise.
    private boolean checkPlayersNumber(Model model) {
            //we must  check that the currentPlayer isn't disconnected
            if(isPlayerInArray(model.getGame().getCurrentPlayer(), disconnectedPlayers)){
              //if currentPlayer is disconnected, get a new currentPlayer
                model.getGame().endPlayerTurn(model.getGame().getCurrentPlayer(), null);
            }
            disconnectedPlayers.clear(); //clear the list of disconnected players
       //if one player remains, the match ends.
        if(clients.size()==1){
            try {
                String message="One player remained. Match ended";
                dispatchMessage(message); //notify on client
                notifyToEveryone(message); //notify on the server
                return true;
            } catch (RemoteException e) {
                ExceptionLogger.info(e);
            }
        }
        return false;
    }

    private boolean isPlayerInArray(Player currentPlayer,
            List<Player> disconnectedPlayers) {
            for(Player disconnectedPlayer:disconnectedPlayers)
            {
                if(currentPlayer.equals(disconnectedPlayer))
                    return true;
            }
            return false;
    }

    //gives control to the next playerWithPermissions client and block the other players
    private void passControlToThePlayer(Player playerWithPermissions)  throws RemoteException{
    
                for(RemoteControllerInterface client:clients){
                    //if it's not the next player, then block him
                    if(!client.getPlayerConnectionData().getRemotePlayer().equals(playerWithPermissions)){
                        client.blockActions();
                    }
                    else{ //else
                        currentClient=client;
                    }
                }
              //unblock him
                currentClient.unlockClient();
        
    }

   
   //function that passes the model to every player connected.
    private void passModelToEveryone(Model model)  throws RemoteException{
        for(RemoteControllerInterface client: clients){
            client.setModel((ServerModel)(((ServerModel)model).clone()));
        }
    }

    /**
     * Starts the current thread.
     * @param Model model
     * @return void
     * @throws RemoteException
     * */
    public void startGame(){
        try {
            startRealGame();
        } catch (RemoteException e) {
            ExceptionLogger.info(e);
        }
    }

    @Override
    public void saveGame(PlayerConnectionData pcd) throws IOException, RemoteException {
        File saveFile= null;
        ObjectOutputStream out = null;
        FileOutputStream fileOut= null;
        try {
            saveFile=new File( "C:/eftaios/serversaves/rmisaves/match" + System.currentTimeMillis()
                    + ".pcd");
            saveFile.mkdirs();
            fileOut = new FileOutputStream(saveFile);
            out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            ExceptionLogger.info(e);
        }finally {
            saveFile=null;
            out.close();
            fileOut.close();
    } 
    }


}
