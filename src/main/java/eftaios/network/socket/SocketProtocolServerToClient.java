package eftaios.network.socket;

import java.io.Serializable;
import java.util.List;

import eftaios.model.Model;
import eftaios.model.avatars.Player;
import eftaios.model.events.GameEvent;

public class SocketProtocolServerToClient implements ServerToClientInterface ,Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 2921554338162623593L;
    private Model model;
    private GameEvent event;
    private List<OnlineGameInfo> availableMatches;
    private Player player;

    /**
     * Create a new protocol to send data from
     * the server to the client
     */
    public SocketProtocolServerToClient() {
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public GameEvent getEvent() {
        return event;
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public void setEvent(GameEvent event) {
        this.event = event;
    }

    @Override
    public void setMatches(List<OnlineGameInfo> availableMatches) {
        this.availableMatches = availableMatches;
    }
    
    @Override
    public List<OnlineGameInfo> getAvailableMatches() {
        return availableMatches;
    }

    @Override
    public void setPlayer(Player player) {
        this.player=player;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
