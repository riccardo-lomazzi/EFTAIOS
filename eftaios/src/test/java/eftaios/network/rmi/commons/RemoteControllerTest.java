package eftaios.network.rmi.commons;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;

import eftaios.model.Model;
import eftaios.view.cli.CommandLineInterface;
import eftaios.view.cli.LocalCLIClientMenu;

public class RemoteControllerTest {

    RemoteController cwr;
    
    @Before
    public void setUp() throws Exception{
        cwr=new RemoteController(new Model(), new CommandLineInterface(new LocalCLIClientMenu(System.in, System.out)));
    }
    
    @Test
    public void testActionsBlocked() throws RemoteException{
        cwr.blockActions();
        assertTrue(cwr.getBlockActions());
    }
    
    @Test
    public void testActionsUnlocked() throws RemoteException{
        cwr.unlockActions();
        assertFalse(cwr.getBlockActions());
    }

}
