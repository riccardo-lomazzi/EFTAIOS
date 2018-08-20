package eftaios.model;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import eftaios.model.avatars.AlienPlayer;
import eftaios.model.avatars.Player;
import eftaios.model.events.IllegalActionEvent;
import eftaios.model.events.SuccessfulMoveOnDangerousSectorEvent;
import eftaios.model.events.SuccessfulMoveOnSafeSectorEvent;
import eftaios.model.gamerules.AdvancedGameRules;
import eftaios.model.gamerules.Rules;

public class ModelTest {

    Model model = new Model();
    Player currentPlayer;

    @Before
    public void CreateGame() {
        Rules rules = new AdvancedGameRules();
        model.createGame(3, rules, "Galilei");
        currentPlayer = model.getGame().getCurrentPlayer();
    }

    @Test
    public void attackTest() {
        model.getGame().attack(currentPlayer);
    }

    /*
     * Testing movement and cycle of players with X players X+1 test to complete
     * a cycle
     */

    @Test
    public void testMoveRequest0() {
        if (currentPlayer instanceof AlienPlayer)
            assertTrue(model.getGame().movePlayer(currentPlayer, "L5") instanceof SuccessfulMoveOnDangerousSectorEvent);
        else
            assertTrue(model.getGame().movePlayer(currentPlayer, "K9") instanceof SuccessfulMoveOnSafeSectorEvent);
        model.endTurn(currentPlayer, null);
    }

    @Test
    public void testMoveRequest1() {
        if (currentPlayer instanceof AlienPlayer)
            assertTrue(model.getGame().movePlayer(currentPlayer, "L5") instanceof SuccessfulMoveOnDangerousSectorEvent);
        else
            assertTrue(model.getGame().movePlayer(currentPlayer, "K9") instanceof SuccessfulMoveOnSafeSectorEvent);
        model.endTurn(currentPlayer, null);
    }

    @Test
    public void testMoveRequest2() {
        if (currentPlayer instanceof AlienPlayer)
            assertTrue(model.getGame().movePlayer(currentPlayer, "L5") instanceof SuccessfulMoveOnDangerousSectorEvent);
        else
            assertTrue(model.getGame().movePlayer(currentPlayer, "K9") instanceof SuccessfulMoveOnSafeSectorEvent);
        model.endTurn(currentPlayer, null);
    }

    @Test
    public void testMoveRequest3() {
        if (currentPlayer instanceof AlienPlayer)
            assertTrue(model.getGame().movePlayer(currentPlayer, "L5") instanceof SuccessfulMoveOnDangerousSectorEvent);
        else
            assertTrue(model.getGame().movePlayer(currentPlayer, "K9") instanceof SuccessfulMoveOnSafeSectorEvent);
        model.endTurn(currentPlayer, null);
    }

    /*
     * Other testing on movement to wrong destination and with full movement
     */

    @Test
    public void testMoveRequestToWrongDestination() {
        if (currentPlayer instanceof AlienPlayer)
            assertTrue(model.getGame().movePlayer(currentPlayer, "L7") instanceof IllegalActionEvent);
        else
            assertTrue(model.getGame().movePlayer(currentPlayer, "N9") instanceof IllegalActionEvent);
        model.endTurn(currentPlayer, null);
    }

    @Test
    public void testMoveRequestWithNoMovementsLeft() {
        if (currentPlayer instanceof AlienPlayer) {
            currentPlayer.setMovesThisTurn(2);
            assertTrue(model.getGame().movePlayer(currentPlayer, "L5") instanceof IllegalActionEvent);
        } else {
            currentPlayer.setMovesThisTurn(1);
            assertTrue(model.getGame().movePlayer(currentPlayer, "K9") instanceof IllegalActionEvent);
        }
        model.endTurn(currentPlayer, null);
    }
}
