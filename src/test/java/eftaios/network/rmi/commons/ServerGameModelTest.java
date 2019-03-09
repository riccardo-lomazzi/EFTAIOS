package eftaios.network.rmi.commons;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import eftaios.model.events.EndOfTurnEvent;
import eftaios.model.gamerules.AdvancedGameRules;
import eftaios.network.rmi.commons.ServerModel.TypeOfPlayer;

public class ServerGameModelTest {

    ServerModel modelAdvanced;
    ServerGame gameAdvanced;
    
    @Before
    public void setUp() throws Exception{
        modelAdvanced=new ServerModel();
        gameAdvanced=new ServerGame(2, new AdvancedGameRules(), "Galilei");
        modelAdvanced.createGame(2, new AdvancedGameRules(), "Galilei");
        gameAdvanced.firstGameTurn();
    }
    
    @Test
    public void testGameInitialized(){
        assertNotNull(gameAdvanced.getListOfPlayers());
        assertTrue(gameAdvanced.isGameInitialized());
        assertTrue(modelAdvanced.isModelInitialized());
    }
    
    @Test
    public void testGetters(){
        assertNotNull(modelAdvanced.getNextPlayer(TypeOfPlayer.CURRENT));
        assertNotNull(modelAdvanced.getNextPlayer(TypeOfPlayer.OTHER));
        
    }
    
    @Test
    public void testOtherFunctions(){
        modelAdvanced.endTurn(modelAdvanced.getNextPlayer(TypeOfPlayer.OTHER), new ArrayList <String>());
        
    }
    
    @Test
    public void testNotify(){
        modelAdvanced.notifyViewObserver(new EndOfTurnEvent("Ciao"));
    }
    
    @Test
    public void testClone(){
       assertNotNull( modelAdvanced.clone());
    }

}
