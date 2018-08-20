package eftaios.model.decks.drawables;

import eftaios.model.avatars.Player;
import eftaios.model.events.GameEvent;
import eftaios.model.events.SuccessfulUseOfItemEvent;
import eftaios.model.managers.GameBoardManager;
import eftaios.model.managers.PlayerManager;

public class SedativeItem extends Item {

    /**
     * 
     */
    private static final long serialVersionUID = -7192683955689633693L;

    public SedativeItem() {
    }

    @Override
    public GameEvent dispatchEffect(Player player,PlayerManager playerManager,GameBoardManager gameBoardManager) {
        player.setSedated(true);
        return new SuccessfulUseOfItemEvent("Used a Sedative");
    }

}
