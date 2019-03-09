package eftaios.model.board;

import java.io.Serializable;

import eftaios.model.events.GameEvent;

public abstract class Sector implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 5549998601362738536L;
    private final Character charId;
    private final Integer intId;
    private final boolean walkable;

    protected Sector(Character cId,Integer iId,boolean walkable) {
    this.intId = iId;
    this.charId = cId;
    this.walkable = walkable;
    }

    /**
     * Get the description of this object
     */
    public abstract String getDescription();

    public Character getCharId() {
    return charId;
    }

    public Integer getIntId() {
    return intId;
    }

    /**
     * Get a string representing the complete identifier
     */
    public String getCompleteId() {
    return charId+""+intId;
    }
    
    public boolean isWalkable(){
        return walkable;
    }
    
    /**
     * Check if two sectors are near
     * NOTE: a near b is equal to b near a
     * @return true if sector a is near to sector b
     */
    public static boolean areNear(Sector a,Sector b) {
        /*
         * The algorithm is different if the char is even or odd
         * due to the sector positioning inside the game map
         */
        if(isCharEven(a.getCharId())){
            return evenAreNear(a,b);
        }else{
            return oddAreNear(a,b);
        }
    }

    private static boolean evenAreNear(Sector a, Sector b) {
        if(a.getCharId().equals(b.getCharId())&&isIntIdDeltaBetween(a,b,-1,1))
            return true;
        else
            return areCharNearButDifferent(a,b)&&isIntIdDeltaBetween(a,b,-1,0);
    }

    private static boolean oddAreNear(Sector a, Sector b) {
        if(a.getCharId().equals(b.getCharId())&&isIntIdDeltaBetween(a,b,-1,1))
            return true;
        else
            return areCharNearButDifferent(a,b)&&isIntIdDeltaBetween(a,b,0,1);
    }

    private static boolean isIntIdDeltaBetween(Sector a, Sector b, int min, int max) {
        return a.getIntId()-b.getIntId()>=min&&a.getIntId()-b.getIntId()<=max;
    }

    private static boolean areCharNearButDifferent(Sector a, Sector b) {
        return a.getCharId()-b.getCharId()<=1&&a.getCharId()-b.getCharId()>=-1&&!(a.getCharId().equals(b.getCharId()));
    }
    
    public static boolean isCharEven(Character charID) {
        return charID.charValue()%2==0;
    }
    
    /**
     * @return returns the event associated with this sector
     */
    public abstract GameEvent getEvent();

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((charId == null) ? 0 : charId.hashCode());
        result = prime * result + ((intId == null) ? 0 : intId.hashCode());
        result = prime * result + (walkable ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sector other = (Sector) obj;
        if (charId == null) {
            if (other.charId != null)
                return false;
        } else if (!charId.equals(other.charId))
            return false;
        if (intId == null) {
            if (other.intId != null)
                return false;
        } else if (!intId.equals(other.intId))
            return false;
        if (walkable != other.walkable)
            return false;
        return true;
    }
}
