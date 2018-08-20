package eftaios.network.socket.server;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eftaios.model.Model;
import eftaios.model.avatars.Player;
import eftaios.model.events.ConnectedToGameEvent;
import eftaios.model.events.GameEvent;
import eftaios.model.events.IllegalActionEvent;

public class OnlineGame {
    
    private Model model;
    private boolean ready;
    private boolean playing;
    private Map<InetAddress,Player> connectedPlayers = new HashMap<InetAddress,Player>();
    private List<InetAddress> connectedClients = new ArrayList<InetAddress>();
    private boolean alreadyAssigned;
    private boolean starting;
    
    /**
     * Create an online game holding the given model
     * @param model the model attached to this online game
     */
    public OnlineGame(Model model) {
        this.setModel(model);
        alreadyAssigned=false;
        ready=false;
        playing=false;
        starting = false;
    }
    
    public void setModel(Model model) {
        this.model=model;
    }
    
    public Model getModel() {
        return model;
    }
    
    /**
     * Add a client to this online game and assign him
     * a player in the game
     * and check if this game is ready for being start
     * if there are two or more players this game
     * is labeled as ready
     * @param clientID the client identifier of the added client
     * @return An event representing the result of the add
     */
    public GameEvent addClientID(InetAddress clientID) {
        List<Player> temp = model.getGame().getPlayers();
        if(connectedPlayers.size()<temp.size()&&!this.playing) {
            connectedPlayers.put(clientID,temp.get(connectedPlayers.size()));
            connectedClients.add(clientID);
            if(connectedPlayers.size()>1) {
                ready=true;
            }
            return new ConnectedToGameEvent("Connected to the game");
        }
        else {
            return new IllegalActionEvent("Impossible to connect to that game");
        }
    }
    
    /**
     * Get the corresponding player to a client
     * inside this game 
     * @param clientID the client 
     * @return the player associated with the given client
     */
    public Player getPlayer(InetAddress clientID) {
        return connectedPlayers.get(clientID);
    }

    /**
     * Remove a client from this game
     * @param client the client to be removed
     */
    public void removeClient(InetAddress client) {
        connectedClients.remove(client);
        connectedPlayers.remove(client);
    }
    
    /**
     * @return true if the game is ready to start
     */
    public boolean isReady() {
        return ready;
    }

    /**
     * @return true is the game is already being played
     */
    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
   
    public int getNumberOfConnectedPlayers() {
        return connectedPlayers.size();
    }
 
    public Map<InetAddress,Player> getConnectedPlayers() {
        return connectedPlayers;
    }

    public List<InetAddress> getConnectedClients() {
        return connectedClients;
    }

    /**
     * this method will reassign the connected client to the players of the game
     * so that each client connected as a player inside  the game and vice versa
     * before starting the game it is possible that the number of players 
     * inside the game is different
     * to the number of clients connected to the game
     */
    public synchronized void reAssignClientsToPlayes() {
        if(!alreadyAssigned) {
            List<Player> temp = model.getGame().getPlayers();
            connectedPlayers = new HashMap<InetAddress,Player>();
            for (InetAddress client : connectedClients) {
                if(connectedPlayers.size()<temp.size())
                    connectedPlayers.put(client,temp.get(connectedPlayers.size()));
            }
        }
        /*
         * this methods must be done one time only by the first thread that
         * reassign the players
         */
        alreadyAssigned=true;
    }

    

    
    /**
     * @param starting set if this object is being
     * started by an object 
     */
    public void setStarting(Boolean starting) {
        this.starting = starting;
    }
    
    /**
     * @return true if this game is already being
     * started by someone only one object can start a game at a time
     */
    public boolean isStarting() {
        return starting;
    }
}
