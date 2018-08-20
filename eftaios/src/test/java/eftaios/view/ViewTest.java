package eftaios.view;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eftaios.model.avatars.AlienPlayer;
import eftaios.model.avatars.Player;
import eftaios.view.cli.CommandLineInterface;

public class ViewTest {

    View view;
    
    @Before
    public void setUp() throws Exception {
        view = new CommandLineInterface(new ClientMenu());
    }

    @Test
    public void testShowPlayerInfo() {
        Player player = new AlienPlayer("PLayer0");
        view.setCurrentPlayer(player);
        Assert.assertEquals(player, view.getCurrentPlayer());
    }

    @Test
    public void testIsRunning() {
        view.isRunning();
    }

    @Test
    public void testSetViewID() {
        try {
            view.setViewID(InetAddress.getByName("127.0.0.0"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
