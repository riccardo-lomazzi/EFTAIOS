package eftaios.model.avatars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayersFactory {
    
    private PlayersFactory() {
    }
    
    /**
     * Function that creates a List of players based on a initial size
     * and shuffles tha list
     * @return Generated List of Player
     * @param initial size of list (playerSize)
     */
    public static List<Player> createPlayers(int playersSize){
        List <Player> players = new ArrayList <Player> (playersSize);
        initializePlayerArray(players,playersSize);
        Collections.shuffle(players);
        return players;
    }

    private static void initializePlayerArray(List<Player> players,int playersSize){ 
        int i=0;
        for (; i < playersSize/2; i++) {
            players.add(new HumanPlayer("Player"+i));
        }
        for(; i<playersSize;i++){
            players.add(new AlienPlayer("Player"+i));
        }
    }
    
}
