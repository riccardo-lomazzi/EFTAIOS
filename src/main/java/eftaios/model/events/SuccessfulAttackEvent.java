package eftaios.model.events;


import java.util.List;

import eftaios.model.avatars.Player;
import eftaios.view.EventVisitor;

public class SuccessfulAttackEvent extends GameEvent {

    /**
     * 
     */
    private static final long serialVersionUID = -7088001662396929370L;
    private List<Player> eliminatedPlayers;
    
    public SuccessfulAttackEvent(String message, List<Player> eliminatedPlayers) {
        super(message);
        this.eliminatedPlayers=eliminatedPlayers;
    }
    /**
     * Function that gets the list of the eliminated players
     * @return List of elimiated Players
     * @param /
     */
    public List<Player> getEliminatedPlayersList(){
        return eliminatedPlayers;
    }
    
    /**
     * Built with the visitor pattern in mind, it's a function 
     * that accepts a Visit from an EventVistor instance and calls the visitEvent method
     * of the visitor, by passing an instance of itself, the visited object.
     * The visitor contains overloaded methods for every GameEvent 
     * @return void
     * @param the visitor of the event (EventVisitor)
     */
    @Override
    public void acceptVisit(EventVisitor visitor) {
        visitor.visitEvent(this);

    }

}
