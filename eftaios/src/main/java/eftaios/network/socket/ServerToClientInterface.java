package eftaios.network.socket;

import java.util.List;

import eftaios.model.Model;
import eftaios.model.avatars.Player;
import eftaios.model.events.GameEvent;

public interface ServerToClientInterface {
    public Model getModel();
    public GameEvent getEvent();
    public Player getPlayer();
    public List<OnlineGameInfo> getAvailableMatches();
    public void setModel(Model model);
    public void setEvent(GameEvent event);
    public void setPlayer(Player player);
    public void setMatches(List<OnlineGameInfo> availableMatches);
}
