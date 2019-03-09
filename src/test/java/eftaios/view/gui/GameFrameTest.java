package eftaios.view.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eftaios.model.avatars.HumanPlayer;
import eftaios.model.avatars.Player;
import eftaios.model.board.DangerousSector;
import eftaios.model.decks.drawables.Item;
import eftaios.model.events.GreenEscapePodEvent;
import eftaios.model.events.NoiseInAnySectorEvent;
import eftaios.model.events.NoiseInYourSectorEvent;
import eftaios.model.events.RedEscapePodEvent;
import eftaios.model.events.SilenceEvent;

public class GameFrameTest extends GameFrame {

    GameFrame frame;

    @Before
    public void setUp() throws Exception {
        frame = new GameFrame();
        frame.getgameViewHolder();
    }

    @Test
    public void testGetPlayerMenuFrame() {
        Assert.assertNotNull(frame.getPlayerMenuFrame());
    }

    @Test
    public void testgameViewHolder() {
        Assert.assertNotNull(getgameViewHolder());
    }

    @Test
    public void testInfoPanel() {
        Assert.assertNotNull(getInfoPanel());
    }

    @Test
    public void testItemPanel() {
        Assert.assertNotNull(getItemPanel());
    }

    @Test
    public void testMessagePanel() {
        Assert.assertNotNull(getMessagePanel());
    }

    @Test
    public void testPlayerInfoPanel() {
        Assert.assertNotNull(getPlayerInfoPanel());
    }

    @Test
    public void testgetEventPanel() {
        Assert.assertNotNull(getEventPanel());
    }

    @Test
    public void testgetLogView() {
        Assert.assertNotNull(getLogView());
    }

    @Test
    public void testgetActionPanel() {
        Assert.assertNotNull(getActionPanel());
    }

    @Test
    public void testgetDrawedMap() {
        Assert.assertNotNull(getDrawedMap());
    }

    @Test
    public void testCardNoiseInYourSectorUpdate() {
        frame.updateCard(new NoiseInYourSectorEvent("green"));
    }

    @Test
    public void testCardNoiseInAnySectorUpdate() {
        frame.updateCard(new NoiseInAnySectorEvent("green"));
    }

    @Test
    public void testCardSilenceUpdate() {
        frame.updateCard(new SilenceEvent("green"));
    }

    @Test
    public void testCardRedPodUpdate() {
        frame.updateCard(new RedEscapePodEvent("green"));
    }

    @Test
    public void testCardGreenPodUpdate() {
        frame.updateCard(new GreenEscapePodEvent("green"));
    }

    @Test
    public void testUpdateItems() {
        List<Item> items = new ArrayList<Item>(2);
        frame.updateItems(items);
    }

    @Test
    public void testLogs() {
        frame.addLogLabel("PLayer2 logtest");
        List<String> logs = new ArrayList<String>(2);
        frame.updateLog(logs);
    }

    @Test
    public void testUpdatePosition() {
        Player player = new HumanPlayer("TestPLayer0");
        DangerousSector position = new DangerousSector('A', 3);
        player.setPosition(position);
        frame.updatePlayerPosition(player);
        Assert.assertEquals(position, player.getPosition());
    }

    @Test
    public void testUpdateTurn() {
        Player player = new HumanPlayer("TestPLayer0");
        frame.updateTurn(player, 0);
    }

    @Test
    public void testgetColor() {
        Color color = frame.getColor("PLayer2");
        color = frame.getColor("PLayer3");
        color = frame.getColor("PLayer4");
        color = frame.getColor("PLayer5");
        color = frame.getColor("PLayer6");
        color = frame.getColor("PLayer7");
        color = frame.getColor("PLayer0");
        color = frame.getColor("PLayer1");
        Assert.assertEquals(color, Color.RED);
    }
    
    
}
