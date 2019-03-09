package eftaios.model.board;

import eftaios.model.events.GameEvent;
import eftaios.model.events.SuccessfulMoveOnStartingSectorEvent;

public class HumanStartingSector extends Sector {

    /**
     * 
     */
    private static final long serialVersionUID = -8170040151421630874L;

    /**
     * Create a new sector with the given identifier
     * @param cId the character identifier
     * @param iId the integer identifier
     */
    public HumanStartingSector(Character cId,Integer iId) {
    super(cId, iId,false);
    }

    @Override
    public String getDescription() {
    return "Human Starting Sector";
    }

    @Override
    public GameEvent getEvent() {
        return new SuccessfulMoveOnStartingSectorEvent("Your game as a Human starts here ["+this.getCompleteId()+"]");
    }

}
