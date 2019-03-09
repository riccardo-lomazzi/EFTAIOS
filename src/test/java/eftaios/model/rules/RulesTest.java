package eftaios.model.rules;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eftaios.model.Model;
import eftaios.model.avatars.Player;
import eftaios.model.board.Sector;
import eftaios.model.events.GameEvent;
import eftaios.model.events.IllegalActionEvent;
import eftaios.model.gamerules.AdvancedGameRules;
import eftaios.model.gamerules.BasicGameRules;

public class RulesTest{
    
    Model modelBasic;
    Model modelAdvanced;
    Player player1;
    Player player2;
    List <Sector> player1adjacent;
    List <Sector> player2adjacent;
    static boolean setUpDone=false;
    
    @Before
    public void setUp() throws Exception {
        modelBasic=new Model();
        modelBasic.createGame(2,new BasicGameRules(),"Galilei");
        modelBasic.getGame().firstGameTurn();
        modelAdvanced=new Model();
        modelAdvanced.createGame(2, new AdvancedGameRules(), "Galilei");
        player1=modelBasic.getCurrentPlayer();
        player2=modelAdvanced.getCurrentPlayer();
        player1adjacent=player1.getAdjacentSectors();
        player2adjacent=player2.getAdjacentSectors();
    }
    
    @Test
    public void testIfPlayerCanMoveInStartingSector(){
        GameEvent event=modelBasic.getGame().movePlayer(player1, player1.getPosition().getCompleteId());
        assertTrue(event instanceof IllegalActionEvent);
        event=modelAdvanced.getGame().movePlayer(player2, player2.getPosition().getCompleteId());
        assertTrue(event instanceof IllegalActionEvent);
    }
    /*
    @Test
    public void testIfPlayerCanMoveTwiceWithBasicRules(){
        GameEvent event=null;
        int i=0;
        while(true){
            if(!(player1adjacent.get(i) instanceof WallSector)){
                event=modelBasic.getGame().movePlayer(player1, player1adjacent.get(i).getCompleteId());
                i++;
            }
        if(i>=2){ 
            break;
            }
        }
        assertTrue(event instanceof IllegalActionEvent);
    }*/
}

