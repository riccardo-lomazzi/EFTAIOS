package eftaios.model.board;

import eftaios.model.events.GameEvent;
import eftaios.model.events.SuccessfulMoveOnWallSectorEvent;

public class WallSector extends Sector {

    /**
     * 
     */
    private static final long serialVersionUID = 7121377308079528876L;

    /**
     * Create a new sector with the given identifier
     * @param cId the character identifier
     * @param iId the integer identifier
     */
    public WallSector(Character cId,Integer iId) {
    super(cId, iId,false);
    }

    @Override
    public String getDescription() {
    return "Wall";
    }

    @Override
    public GameEvent getEvent() {
        return new SuccessfulMoveOnWallSectorEvent("Are you Spiderman? Climbing walls? seriously you should not be here ["+this.getCompleteId()+"]");
    }

}
