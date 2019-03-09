package eftaios.network.rmi.commons;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import eftaios.model.Model;
import eftaios.model.avatars.HumanPlayer;
import eftaios.network.rmi.server.ServerInterface;

public class PlayerConnectionDataTest {

    PlayerConnectionData pcd;
    ServerInterface server;
    
    @Before
    public void setUp() throws Exception{
        server = null;
        pcd=new PlayerConnectionData(new Model(), server, new HumanPlayer("1"));
    }
    
    @Test
    public void testCheckModelNotNull() {
        assertNotNull(pcd.getModel());
    }
    
    @Test
    public void testCheckServerNull() {
        assertNull(pcd.getServer());
    }
    
    @Test
    public void testCheckRemotePlayerNotNull() {
        assertNotNull(pcd.getRemotePlayer());
    }


}
