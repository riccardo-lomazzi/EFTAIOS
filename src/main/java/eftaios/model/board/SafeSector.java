package eftaios.model.board;

import eftaios.model.events.GameEvent;
import eftaios.model.events.SuccessfulMoveOnSafeSectorEvent;

public class SafeSector extends Sector {

    /**
     * 
     */
    private static final long serialVersionUID = 7911830897220753646L;

    /**
     * Create a new sector with the given identifier
     * @param cId the character identifier
     * @param iId the integer identifier
     */
    public SafeSector(Character cId,Integer iId) {
    super(cId, iId,true);
    }

    @Override
    public String getDescription() {
    return "Safe Sector";
    }

    @Override
    public GameEvent getEvent() {
        return new SuccessfulMoveOnSafeSectorEvent("You end up in a safe sector ["+this.getCompleteId()+"]");
    }

}
