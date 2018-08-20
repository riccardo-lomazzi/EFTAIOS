package eftaios.model.avatars;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class PlayersFactoryTest {

    List <Player> players;
    
    @Before
    public void setUp() throws Exception{
        players=PlayersFactory.createPlayers(2);
        
    }
    
    @Test
    public void testPlayersArrayCreated(){
        assertNotNull(players);
    }

}
