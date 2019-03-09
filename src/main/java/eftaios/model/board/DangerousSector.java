package eftaios.model.board;

import eftaios.model.events.GameEvent;
import eftaios.model.events.SuccessfulMoveOnDangerousSectorEvent;

public class DangerousSector extends Sector {

    /**
     * 
     */
    private static final long serialVersionUID = -2818741687544296953L;

    /**
     * Create a new sector with the given identifier
     * @param cId the character identifier
     * @param iId the integer identifier
     */
    public DangerousSector(Character cId,Integer iId) {
    super(cId, iId,true);
    }

    @Override
    public String getDescription() {
    return "Dangerous Sector";
    }

    @Override
    public GameEvent getEvent() {
        return new SuccessfulMoveOnDangerousSectorEvent("You ended on a dangerous sector ["+this.getCompleteId()+"]");
    }

}
