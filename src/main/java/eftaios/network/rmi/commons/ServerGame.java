package eftaios.network.rmi.commons;

import java.io.FileNotFoundException;
import java.util.List;

import eftaios.model.avatars.Player;
import eftaios.model.gamerules.Rules;
import eftaios.model.match.Game;


//a class to get the list of players
public class ServerGame extends Game {

    /**
     * Class that contains two new methods, one to return the array of players, the other to check if the game is ready to start.
     */
    private static final long serialVersionUID = -2481616472624286876L;


    public ServerGame(int numberOfPlayers, Rules rules, String mapPath)
            throws FileNotFoundException {
        super(numberOfPlayers, rules, mapPath);
    }
    
    
    public List<Player> getListOfPlayers(){
        return playerManager.getPlayersArray();
    }
    
    /**
     * Function that is called when the user on the server is configuring the match. A thread checks if the match has been
     * configured by using this function. The match has been configured only if the players have been set,
     * the rules have been inserted and the hexmap exists and has been loaded correctly.
     * @return true if configured, false if otherwise.
     * @param nothing
     */
    public boolean isGameInitialized(){
        return !playerManager.getPlayersArray().isEmpty() && rulesManager.getRules()!=null && gameboardManager.doesMapExists();
    }
}
