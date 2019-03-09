package eftaios.controller;

import eftaios.model.Model;
import eftaios.model.avatars.Player;
import eftaios.network.socket.OnlineGameInfo;

public class OnlineCreateGameInput extends CreateGameInput {

    /**
     * 
     */
    private static final long serialVersionUID = -6079946278272453553L;

    public OnlineCreateGameInput(OnlineGameInfo gameinfo) {
        super(gameinfo);
    }

    @Override
    public void executeCommand(Model model, Player player) {
        model.createGame(gameinfo.getNumberOfPlayers(), gameinfo.getRules(), gameinfo.getMapPath(),((OnlineGameInfo)gameinfo).getGameID());
    }
}
