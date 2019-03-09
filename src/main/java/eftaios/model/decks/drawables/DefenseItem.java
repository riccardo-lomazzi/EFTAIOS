package eftaios.model.decks.drawables;

import eftaios.model.avatars.Player;
import eftaios.model.events.GameEvent;
import eftaios.model.managers.GameBoardManager;
import eftaios.model.managers.PlayerManager;

public class DefenseItem extends Item {

    /**
     * 
     */
    private static final long serialVersionUID = -2770987098156168996L;

    public DefenseItem() {
    }

    @Override
    public GameEvent dispatchEffect(Player player,PlayerManager playerManager,GameBoardManager gameBoardManager) {
        /*
         * this item has no attached effect and
         * this method in this item 
         * should never be called 
         * null value must be handled by the caller 
         */
        return null;
    }

}
