package eftaios.controller;

import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import eftaios.model.Model;
import eftaios.model.avatars.Player;
import eftaios.model.decks.drawables.AttackItem;
import eftaios.model.decks.drawables.Item;
import eftaios.model.gamerules.AdvancedGameRules;
import eftaios.network.socket.OnlineGameInfo;
import eftaios.view.GameInfo;

public class UserInputTest {

    
    Model modelAdvanced;
    Player player1;
    List <String> log;
    AttackInput ai; CreateGameInput cgi; DrawInput di; EndGameInput egi; EndTurnInput eti;
    ItemInput ii; LogInput li; MoveInput mi; OnlineCreateGameInput ocgi;
    RMIConnectToGameInput RMIctgi; RMICreateGameInput RMIcgi; SelectedItemsInput selectedinput;
    SocketConnectToGameInput socketconnecttogameinput; 
    
    @Before
    public void setUp() throws Exception {
        
        modelAdvanced=new Model();
        BigInteger gameID=new BigInteger(0,new Random());
        modelAdvanced.createGame(2, new AdvancedGameRules(), "Galilei", gameID);
        modelAdvanced.getGame().firstGameTurn();
        player1=modelAdvanced.getCurrentPlayer();
        //game info init
        GameInfo gameInfo=new GameInfo();
        OnlineGameInfo onlineGameInfo= new OnlineGameInfo();
        gameInfo.setMapPath("Galilei");
        gameInfo.setNumberOfPlayers(2);
        gameInfo.setRules(new AdvancedGameRules());
        
        log=new ArrayList<String>();
        
        //input initialization
        ai=new AttackInput();
        cgi=new CreateGameInput(gameInfo);
        di=new DrawInput();
        egi=new EndGameInput(log);
        eti=new EndTurnInput(log);
        ii=new ItemInput(new AttackItem());
        li=new LogInput();
        mi=new MoveInput("M5");
        ocgi=new OnlineCreateGameInput(onlineGameInfo);
        RMIctgi=new RMIConnectToGameInput("EFTAIOS_server");
        selectedinput=new SelectedItemsInput(new ArrayList <Item>(4));
        socketconnecttogameinput=new SocketConnectToGameInput(0);
    }
    
    @Test
    public void testExecuteCommand(){
        ai.executeCommand(modelAdvanced, player1);
        cgi.executeCommand(modelAdvanced, player1);
        di.executeCommand(modelAdvanced, player1);
        egi.executeCommand(modelAdvanced, player1);
        eti.executeCommand(modelAdvanced, player1);
        ii.executeCommand(modelAdvanced, player1);
        li.executeCommand(modelAdvanced, player1);
        mi.executeCommand(modelAdvanced, player1);
        ocgi.executeCommand(modelAdvanced, player1);
        RMIctgi.executeCommand(modelAdvanced, player1);
        //RMIcgi.executeCommand(modelAdvanced, player1);
        selectedinput.executeCommand(modelAdvanced, player1);
        socketconnecttogameinput.executeCommand(modelAdvanced, player1);
    }
    
    @Test
    public void testGetters(){
        assertNotNull(cgi.getGameinfo());
        assertNotNull(egi.getLog());
        assertNotNull(eti.getLog());
        assertNotNull(ocgi.getGameinfo());
        //assertNotNull(RMIctgi.executeCommand(new RemoteController(modelAdvanced, new CommandLineInterface(new LocalCLIClientMenu(System.in, System.out)))));
        assertNotNull(socketconnecttogameinput.getSelectedGame());
    }
    

}
