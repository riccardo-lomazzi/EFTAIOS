package eftaios.model.match;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import eftaios.model.avatars.AlienPlayer;
import eftaios.model.avatars.HumanPlayer;
import eftaios.model.avatars.Player;
import eftaios.model.decks.drawables.Item;
import eftaios.model.decks.drawables.SedativeItem;
import eftaios.model.events.GameEvent;
import eftaios.model.events.GreenEscapePodEvent;
import eftaios.model.events.IllegalActionEvent;
import eftaios.model.events.NoiseInAnySectorEvent;
import eftaios.model.events.NoiseInYourSectorEvent;
import eftaios.model.events.SilenceEvent;
import eftaios.model.events.SuccessfulAttackEvent;
import eftaios.model.events.SuccessfulUseOfItemEvent;
import eftaios.model.gamerules.AdvancedGameRules;
import eftaios.model.gamerules.Rules;

public class GameTest {

    Game game;
    Player player;
    GameEvent event;
    BigInteger gameID;
    
    @Before
    public void setUp() throws Exception {
        Rules rules = new AdvancedGameRules();
        game = new Game(8, rules , "Galilei");
        game = new Game(5, rules , "Galilei");
        gameID=new BigInteger(0, new Random());
        game=new Game(5, rules, "Galilei", gameID);
        game.firstGameTurn();
        player = game.getCurrentPlayer();
        //reset player items
        player.giveItems(new ArrayList<Item>(3));
    }

    @Test
    public void testAttackWithNoItems() {
       
       event = game.attack(player);
       
       if(player instanceof AlienPlayer) {
           assertTrue( event instanceof SuccessfulAttackEvent );
       }
       else {
           assertTrue( game.attack(player) instanceof IllegalActionEvent );
       }
       

       //reset player items
       player.giveItems(new ArrayList<Item>(3));
    }

    @Test
    public void testUseItem() {
       
        List<Item> itemsList = new ArrayList<Item>(3);
        Item sedativeItem = new SedativeItem();
        itemsList.add(sedativeItem);
        
        player.giveItems(itemsList);
        
        event = game.useItem(player, sedativeItem);
        
        if(player instanceof AlienPlayer)
            assertTrue( event instanceof IllegalActionEvent );
        else 
            assertTrue( event instanceof SuccessfulUseOfItemEvent);
        
        //reset player items
        player.giveItems(new ArrayList<Item>(3));
    }

    @Test
    public void testDrawCardWithMovToValidPosistion() {
        
        if(player instanceof AlienPlayer) {
            event = game.movePlayer(player, "L5");
            event = game.drawCard(player);
            assertTrue( event instanceof NoiseInAnySectorEvent || event instanceof NoiseInYourSectorEvent || event instanceof SilenceEvent);
        }
        else {
            event = game.movePlayer(player, "K9");
            event = game.drawCard(player);
            assertTrue( event instanceof IllegalActionEvent );
        }
        //reset player items
        player.giveItems(new ArrayList<Item>(3));
    }

    @Test  
    public void testDrawCardWithMovToInValidPosistion() {

        if(player instanceof AlienPlayer) {
            event = game.drawCard(player);
            assertTrue( event instanceof IllegalActionEvent);
        }
        else {
            event = game.movePlayer(player, "L7");
            event = game.drawCard(player);
            assertTrue( event instanceof IllegalActionEvent );
        }
        //reset player items
        player.giveItems(new ArrayList<Item>(3));
    }

    public void testDrawCard() throws Exception{
        Game game2=new Game(2, new AdvancedGameRules(), "Galilei");
        game2.firstGameTurn();
        char alphabetStart='N';
        int index= 8;
        Player player2=searchHumanIntoArray(game2.getPlayers()); //search for the alien
        GameEvent event2 = game.movePlayer(player2, "M"+index);
        if(player2.equals(game.getCurrentPlayer())){
            event2=game.drawCard(player2);
            player2.setMovesThisTurn(400);
            while(!(event2 instanceof GreenEscapePodEvent) && !(event2 instanceof IllegalActionEvent)){
                if(alphabetStart=='Q') {
                    alphabetStart='M';
                }
                event2 = game.movePlayer(player2, new String(alphabetStart+"8"));
                alphabetStart++;
            }
        }
    }
    
    public Player searchHumanIntoArray(List<Player> playersArray){
        for(Player player:playersArray){
            if(player instanceof HumanPlayer)
                return player;
        }
        return null;
    }
    
    public Player searchAlienIntoArray(List<Player> playersArray){
        for(Player player:playersArray){
            if(player instanceof AlienPlayer)
                return player;
        }
        return null;
    }
    
    @Test
    public void testPlayersArraySizeAfterPlayerRemoval(){
        int old_size=game.getPlayers().size();
        game.removePlayer(player);
        int new_size=game.getPlayers().size();
        assertEquals(old_size,new_size+1);
    }
    
    @Test
    public void testGetters(){
        assertNotNull(game.getGameID());
        assertNotNull(game.getLog());
        assertNotNull(game.getMap());
        game.getCurrentTurn();
        
    }

}
