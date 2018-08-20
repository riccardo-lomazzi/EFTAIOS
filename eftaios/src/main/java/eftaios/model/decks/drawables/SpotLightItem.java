package eftaios.model.decks.drawables;

import java.util.List;

import eftaios.model.avatars.Player;
import eftaios.model.board.Sector;
import eftaios.model.events.GameEvent;
import eftaios.model.events.SuccessfulUseOfSpotLightItemEvent;
import eftaios.model.managers.GameBoardManager;
import eftaios.model.managers.PlayerManager;

public class SpotLightItem extends Item {

    /**
     * 
     */
    private static final long serialVersionUID = -8076046208370415250L;
    String sector;
    
    public SpotLightItem(){
        
    }
    
    public void setSector(String sector) {
        this.sector=sector;
    }

    @Override
    public GameEvent dispatchEffect(Player player,PlayerManager playerManager,GameBoardManager gameBoardManager) {
        return new SuccessfulUseOfSpotLightItemEvent("Revealed Players",getReveledPlayers(playerManager,gameBoardManager));
    }

    private List <Player> getReveledPlayers(PlayerManager playerManager, GameBoardManager gameBoardManager) {
        Sector realSector = gameBoardManager.getMap().getSector(sector);
        return playerManager.getPlayersInPositionAndAdjacent(realSector);
    }

}
