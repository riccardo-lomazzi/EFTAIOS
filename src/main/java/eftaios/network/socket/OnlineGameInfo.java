package eftaios.network.socket;

import java.math.BigInteger;

import eftaios.view.GameInfo;

public class OnlineGameInfo extends GameInfo {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1164841544085036844L;
    private BigInteger gameID;

    public OnlineGameInfo() {
        super();
    }

    /**
     * create an online game info from an
     * already existing game info 
     * @param gameinfo an existing game info
     */
    public OnlineGameInfo(GameInfo gameinfo) {
        setMapPath(gameinfo.getMapPath());
        setNumberOfPlayers(gameinfo.getNumberOfPlayers());
        setRules(gameinfo.getRules());
    }

    public BigInteger getGameID() {
        return gameID;
    }

    public void setGameID(BigInteger gameID) {
        this.gameID = gameID;
    }
}
