package eftaios.model.events;

import java.util.List;

import eftaios.model.avatars.Player;

public class SuccessfulUseOfSpotLightItemEvent extends GameEvent {

    /**
     * 
     */
    private static final long serialVersionUID = 5330038553563787334L;
    private List<Player> revealedPlayers;
    
    public SuccessfulUseOfSpotLightItemEvent(String message,List<Player> revealedPlayers) {
        super(message);
        this.revealedPlayers = revealedPlayers;
    }

    /**
     * Function that returns a list of revealed players using the spotlight item 
     * @return List of revealed Players
     * @param /
     */
    public List<Player> getRevealedPlayers() {
        return revealedPlayers;
    }

}
