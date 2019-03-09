package eftaios.model.decks.drawables;

import eftaios.model.avatars.Player;
import eftaios.model.events.GameEvent;
import eftaios.model.managers.GameBoardManager;
import eftaios.model.managers.PlayerManager;

public class AttackItem extends Item {

    /**
     * 
     */
    private static final long serialVersionUID = -5476853960820842L;

    public AttackItem() {
    }

    @Override
    public GameEvent dispatchEffect(Player player,PlayerManager playerManager,GameBoardManager gameBoardManager) {
        /*
         * the boolean filed in attack her is useless
         * as it checks if an alien can increase his speed or not
         * and only human can use items
         */
        return playerManager.attack(player,false);
    }

}
