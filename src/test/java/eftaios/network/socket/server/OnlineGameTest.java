package eftaios.network.socket.server;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eftaios.ExceptionLogger;
import eftaios.model.Model;
import eftaios.model.gamerules.AdvancedGameRules;
import eftaios.model.gamerules.Rules;

public class OnlineGameTest {

    Model model;
    OnlineGame onlineGame;
    private InetAddress clientID;

    @Before
    public void setUp() throws Exception {
        model = new Model();
        Rules rules = new AdvancedGameRules();
        model.createGame(8, rules, "Galilei");
        onlineGame = new OnlineGame(model);
    }

    @Test
    public void getModelTest() {
        Assert.assertEquals(model, onlineGame.getModel());
    }

    @Test
    public void getPlayerTest() {
        try {
            clientID = InetAddress.getByName("127.0.0.0");
        } catch (UnknownHostException e) {
            ExceptionLogger.info(e);
        }
        onlineGame.addClientID(clientID);
        Assert.assertNotEquals(null, onlineGame.getConnectedPlayers());
    }

    @Test
    public void getPlayersTest() {
        try {
            clientID = InetAddress.getByName("127.0.0.0");
        } catch (UnknownHostException e) {
            ExceptionLogger.info(e);
        }
        onlineGame.addClientID(clientID);
        Assert.assertNotEquals(null, onlineGame.getPlayer(clientID));
    }

    @Test
    public void addClientTest() {
        try {
            clientID = InetAddress.getByName("127.0.0.0");
        } catch (UnknownHostException e) {
            ExceptionLogger.info(e);
        }
        onlineGame.addClientID(clientID);
        Assert.assertTrue(onlineGame.getConnectedClients().size() == 1);
        Assert.assertNotEquals(null, onlineGame.getPlayer(clientID));
    }

    @Test
    public void removeClientTest() {
        try {
            clientID = InetAddress.getByName("127.0.0.0");
        } catch (UnknownHostException e) {
            ExceptionLogger.info(e);
        }
        onlineGame.addClientID(clientID);
        onlineGame.removeClient(clientID);
        Assert.assertTrue(onlineGame.getConnectedClients().size() == 0);
    }

    @Test
    public void assignTest() {
        try {
            clientID = InetAddress.getByName("127.0.0.0");
        } catch (UnknownHostException e) {
            ExceptionLogger.info(e);
        }
        onlineGame.addClientID(clientID);
        try {
            clientID = InetAddress.getByName("127.0.0.1");
        } catch (UnknownHostException e) {
            ExceptionLogger.info(e);
        }
        onlineGame.addClientID(clientID);
        onlineGame.reAssignClientsToPlayes();
        onlineGame.reAssignClientsToPlayes();
        Assert.assertNotEquals(null, onlineGame.getConnectedClients());
    }

}
