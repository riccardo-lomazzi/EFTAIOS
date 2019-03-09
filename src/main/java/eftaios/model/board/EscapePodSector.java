package eftaios.model.board;

import eftaios.model.events.GameEvent;
import eftaios.model.events.SuccessfulMoveOnEscapePodSectorEvent;

public class EscapePodSector extends Sector {

    /**
     * 
     */
    private static final long serialVersionUID = -4426805877704210662L;

    /**
     * Create a new sector with the given identifier
     * @param cId the character identifier
     * @param iId the integer identifier
     */
    public EscapePodSector(Character cId,Integer iId) {
    super(cId, iId,true);
    }

    @Override
    public String getDescription() {
    return "Escape Pod";
    }

    @Override
    public GameEvent getEvent() {
        return new SuccessfulMoveOnEscapePodSectorEvent("You entered an Escape Pod Sector ["+this.getCompleteId()+"]");
    }

}
