package eftaios.model.decks.drawables;

import java.io.Serializable;

import eftaios.model.avatars.Player;
import eftaios.model.events.GameEvent;
import eftaios.model.managers.GameBoardManager;
import eftaios.model.managers.PlayerManager;

public abstract class Item implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1951754516521881953L;

    public Item(){
    }

    /**
     *Dispatch the effect relative to this item
     *@param player the owner of the item
     *@param playerManager the manager that handles player
     *@param gameBoardManager the manager that handles the game board 
     */
    public abstract GameEvent dispatchEffect(Player player,PlayerManager playerManager,GameBoardManager gameBoardManager);
    
    public String getType(){
        return getClass().getSimpleName();
    }
}
