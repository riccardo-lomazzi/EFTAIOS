package eftaios.network.rmi;

import org.junit.Before;
import org.junit.Test;

import eftaios.network.rmi.client.ClientWithRemoteImplementation;
import eftaios.network.rmi.commons.ServerModel;
import eftaios.network.rmi.server.ServerWithRemoteImplementation;

public class ServerTest{
    
    ServerModel model;
    ServerWithRemoteImplementation swri;
    ClientWithRemoteImplementation client1;
    ClientWithRemoteImplementation client2;
    static boolean setUpDone=false;
    
    @Before
    public void setUp() throws Exception {
        //model=new ServerModel();
        //model.createGame(2,new AdvancedGameRules(),"Galilei");
        //swri=new ServerWithRemoteImplementation("EFTAIOS_server", model);
        /*TimeUnit.SECONDS.sleep(5);
        client1=new ClientWithRemoteImplementation("EFTAIOS_server");
        client2=new ClientWithRemoteImplementation("EFTAIOS_server");
        client1.run();
        TimeUnit.SECONDS.sleep(5);
        client2.run();
        TimeUnit.SECONDS.sleep(5);*/
    }
    
    @Test
    public void testIfInitialModelHasParsedCorrectly() throws Exception{
       // assertTrue(client1.getRemoteController().getPlayerConnectionData().getModel().equals(swri.getServerModel()));
       // assertEquals(swri.getServerName().equals("EFTAIOS_server"), true);
    }
    
   /* @Test
    public void testIfModelIsTheSameBetweenClients() throws Exception{
     //   assertTrue(client1.getRemoteController().getPlayerConnectionData().getModel().equals(client2.getRemoteController().getPlayerConnectionData().getModel()));
    }*/
}

