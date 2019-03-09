package eftaios.model.gamerules;

import java.io.Serializable;

public abstract class Turn implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -4341172609510002603L;
    private static final int MAXTURNS=39;
    
    protected Turn() {
    }

    /**
     * Check if current turn is the last
     * @param currentTurn the turn to be checked
     */
    public static boolean isGameLastTurn(int currentTurn) {
        return currentTurn>=MAXTURNS;
    }

    /**
     * Check if current turn is the first
     * @param currentTurn the turn to be checked
     */
    public static boolean isGameFirstTurn(int currentTurn){
        return currentTurn==0;
    }
}
