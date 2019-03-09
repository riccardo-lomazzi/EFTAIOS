package eftaios.model.decks.drawables;

import eftaios.model.avatars.Player;
import eftaios.model.events.GameEvent;
import eftaios.model.events.SuccessfulUseOfItemEvent;
import eftaios.model.managers.GameBoardManager;
import eftaios.model.managers.PlayerManager;

public class TeleportItem extends Item {

    /**
     * 
     */
    private static final long serialVersionUID = 7569632483524460955L;

    public TeleportItem() {
    }

    @Override
    public GameEvent dispatchEffect(Player player,PlayerManager playerManager,GameBoardManager gameBoardManager) {
        player.setPosition(gameBoardManager.getHumanStartingSector());
        return new SuccessfulUseOfItemEvent("Teleported to ["+player.getPosition()+"]");
    }
}
