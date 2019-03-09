package eftaios.model.board;

import eftaios.model.events.GameEvent;
import eftaios.model.events.SuccessfulMoveOnStartingSectorEvent;

public class AlienStartingSector extends Sector {

    /**
     * 
     */
    private static final long serialVersionUID = -7602506283185972761L;

    /**
     * Create a new sector with the given identifier
     * @param cId the character identifier
     * @param iId the integer identifier
     */
    public AlienStartingSector(Character cId,Integer iId) {
    super(cId, iId,false);
    }

    @Override
    public String getDescription() {
    return "Alien Starting Sector";
    }

    @Override
    public GameEvent getEvent() {
        return new SuccessfulMoveOnStartingSectorEvent("Your game as an Alien start here ["+this.getCompleteId()+"]");
    }

}
