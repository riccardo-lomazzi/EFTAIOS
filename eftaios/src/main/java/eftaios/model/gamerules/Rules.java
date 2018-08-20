package eftaios.model.gamerules;

import java.io.Serializable;


public abstract class Rules implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -8167826551056254590L;
    private static final int MAXHUMANMOVE=1;
    private static final int MAXALIENMOVE=2;
    private final boolean ITEMSAREUSABLE;
    private final boolean ALIENCANINCREASESPEED;
    
    protected Rules (boolean itemsareusable,boolean aliencanincreasespeed) {
    ITEMSAREUSABLE=itemsareusable;
    ALIENCANINCREASESPEED=aliencanincreasespeed;
    }

    public int getMaxHumanMove() {
    return MAXHUMANMOVE;
    }
    
    public int getMaxAlienMove() {
    return MAXALIENMOVE;
    }

    public boolean areItemsUsable() {
    return ITEMSAREUSABLE;
    }

    public boolean canAlienIncreaseSpeed() {
    return ALIENCANINCREASESPEED;
    }

}
