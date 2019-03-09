package eftaios.model.avatars;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eftaios.model.Model;
import eftaios.model.decks.drawables.Item;
import eftaios.model.gamerules.AdvancedGameRules;

public class PlayersTest {

    Model model;
    Player player1;
    Player player2;
    List<String> raceArray;
    
    @Before
    public void setUp() throws Exception{
        model=new Model();
        raceArray=new ArrayList<String>(2);
        model.createGame(2, new AdvancedGameRules(), "Galilei");
        player1=model.getGame().getPlayers().get(0);
        player2=model.getGame().getPlayers().get(1);
        raceArray.add("Alien");
        raceArray.add("Human");
    }
    
    @Test
    public void testPlayerAfterModelCreation() {
        assertFalse(player1.hasAlreadyDrawed());
        assertFalse(player1.hasAlreadyMoved());
        assertFalse(player1.hasAnyItem());
        assertFalse(player1.hasAttackItem());
        assertFalse(player1.hasDefenseItem());
        assertTrue(player1.canIMove());
        assertTrue(searchIntoArray(player1.getRaceToString()));
        assertTrue(searchIntoArray(player2.getRaceToString()));
        assertNotNull(player1.getPlayerID());
    }
    
    public boolean searchIntoArray(String toBeSearched){
        for(String race:raceArray){
            if(race.equals(toBeSearched))
                return true;
        }
        return false;
    }
    
    @Test
    public void testPlayerAfterGameCreation(){
        model.getGame().firstGameTurn();
        assertNotNull(player1.getPosition());
        assertNotNull(player1.getTurnStartingPosition());
        assertNotNull(player1.getInfo());
        assertNotNull(player1.getAdjacentSectors());
        assertNotNull(player1.showAdjacentSectors());
        
    }
    
    @Test
    public void testGiveItems(){
        player1.giveItems(new ArrayList<Item>(4));
    }
    
    @Test
    public void testHashCode(){
        player1.incrementMovesThisTurn();
        assertEquals(player1.hashCode(),player1.getPlayerID().hashCode()+31);
        assertTrue(player1.equals(model.getGame().getPlayers().get(0)));
    }
    
    
    
    
}
