package eftaios.model.decks.drawables;

import eftaios.model.avatars.Player;
import eftaios.model.events.GameEvent;
import eftaios.model.events.SuccessfulUseOfItemEvent;
import eftaios.model.managers.GameBoardManager;
import eftaios.model.managers.PlayerManager;

public class AdrenalineItem extends Item {

    /**
     * 
     */
    private static final long serialVersionUID = -6273739171345856528L;

    public AdrenalineItem() {
    }
    
    @Override
    public GameEvent dispatchEffect(Player player,PlayerManager playerManager,GameBoardManager gameBoardManager) {
        player.decrementMovesThisTurn();
        return new SuccessfulUseOfItemEvent("You used Adrenaline");
    }

}
