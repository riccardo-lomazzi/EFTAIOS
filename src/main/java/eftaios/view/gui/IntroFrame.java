package eftaios.view.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;

import eftaios.model.gamerules.AdvancedGameRules;
import eftaios.model.gamerules.BasicGameRules;
import eftaios.model.gamerules.Rules;
import eftaios.model.match.Game;
import eftaios.network.socket.OnlineGameInfo;
import eftaios.view.GameInfo;

public class IntroFrame extends Frame{

    private JPanel gameSelector;
    private JPanel miscHandler;
    private JPanel gameList;
    private JPanel connectedGame;
    private JComponent miscSelector;
    private Component showintro;
    private JButton showCredits;
    private JPanel miscViewer;
    private JTextArea misc;
    private JScrollPane scrollPane;
    private  IntroFrameEventHandler eventHandler;
    private JFrame introFrame;
    private JPanel contentHolder;
    private JSpinner selectedGame;
    private JButton connectToGame;
    private JButton refreshGameList;
    private Component newGame;
    private GameInfo gameInfo;
    private JPanel newgamepanel;
    private JSpinner selectedMap;
    private JSpinner selectedRules;
    private JButton sendNewGame;

    public IntroFrame() {
        super();
    }

    /**
     * @return the frame holding the server connection menu
     */
    public JFrame getIntroFrame() {
        introFrame = new JFrame();
        introFrame.setTitle("EFTIOS");
        introFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        introFrame.setLocation(100, 100);
        introFrame.setSize(800, 600);
        introFrame.setResizable(false);
        contentHolder = new JPanel(true);
        eventHandler=new IntroFrameEventHandler(this,view);
        setIntroPanel(contentHolder);
        introFrame.add(contentHolder);
        return introFrame;
    }

    private void setIntroPanel(JPanel contentHolder) {
        setContentHolder(contentHolder);
        setGameSelector();
        setMiscHandler();
        miscHandler.add(refreshGameList);
        miscHandler.add(newGame);
        miscHandler.add(connectToGame);
        miscHandler.add(selectedGame);
        miscHandler.add(miscSelector);
        miscHandler.add(miscViewer);
        contentHolder.add(gameSelector, new Point(0, 0));
        contentHolder.add(miscHandler, new Point(1, 0));
        gameSelector.add(gameList, new Point(0, 0));
        gameSelector.add(connectedGame, new Point(0, 1));
        gameSelector.add(gameList);
        gameSelector.add(connectedGame);
    }

    private void setMiscHandler() {
        miscHandler = new JPanel(true);
        miscHandler.setLayout(new FlowLayout());
        miscHandler.setBackground(Color.WHITE);
        miscSelector = new JPanel(true);
        miscSelector.setBackground(Color.WHITE);
        showintro = new JButton("Show Intro");
        showintro.setName("showintro");
        miscSelector.add(showintro);
        showCredits = new JButton("Show Credits");
        showCredits.setName("showcredits");
        showintro.addMouseListener(eventHandler);
        showCredits.addMouseListener(eventHandler);
        miscSelector.add(showCredits);
        misc = new JTextArea("****EFTAIOS****\nWelcome to the game!");
        misc.setEditable(false);
        miscViewer = new JPanel(true);
        scrollPane = new JScrollPane(misc, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(700, 200));
        miscViewer.add(scrollPane);
        miscViewer.setBackground(Color.WHITE);
    }

    private void setContentHolder(JPanel contentHolder) {
        contentHolder.setLayout(new GridLayout(2, 1));
        contentHolder.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        contentHolder.setBackground(Color.WHITE);
    }

    private void setGameSelector() {
        gameSelector = new JPanel(new GridLayout(1, 2));
        gameSelector.setBorder(BorderFactory.createMatteBorder(0,0,5,0,Color.BLACK));
        gameSelector.setBackground(Color.WHITE);
        gameList = new JPanel(true);
        gameList.setToolTipText("Online Games");
        gameList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0,0,0,5,Color.BLACK), "Online Games"));
        gameList.setBackground(Color.WHITE);
        connectedGame = new JPanel(true);
        connectedGame.setBackground(Color.WHITE);
        connectedGame.setBorder(BorderFactory.createTitledBorder("Connected Game"));
        selectedGame=new JSpinner(new SpinnerNumberModel());
        connectToGame = new JButton("Connect");
        connectToGame.setName("start");
        connectToGame.addMouseListener(eventHandler);
        refreshGameList = new JButton("refresh");
        refreshGameList.setName("refresh");
        refreshGameList.addMouseListener(eventHandler);
        newGame = new JButton("New Game");
        newGame.setName("newgame");
        newGame.addMouseListener(eventHandler);
    }

    /**
     * @return return the text area used to display messages
     */
    public JTextArea getMisc() {
        return misc;
    }

    /**
     * @return update the server connection menu
     * @param the list of server online matches
     * @param the game at which this client is connected
     */
    public void updateShowIntro(List<OnlineGameInfo> matches, Game game) {
        refreshPanel(gameList);
        for(OnlineGameInfo gameInfo:matches) { 
            String temp = "[" + matches.indexOf(gameInfo) + "]<--"+"Map:"+gameInfo.getMapPath()+" Rules:"+gameInfo.getRules().getClass().getSimpleName();
            if(game!=null&&gameInfo.getGameID().equals(game.getGameID())) {
                refreshPanel(connectedGame);
                connectedGame.add(new JLabel(temp));
            }
            else
            gameList.add(new JLabel(temp));
        }
        introFrame.setVisible(true);
    }

    /**
     * @return the selected game in the game selector
     */
    public JSpinner getSelectedGame() {
        return selectedGame;
    }
   
    /**
     * @return the frame used for creating new games
     */
    public JFrame getNewGameFrame() {
        JFrame newGameFrame = new JFrame();
        newGameFrame.setTitle("New Game");
        newGameFrame.setSize(200, 200);
        newGameFrame.setResizable(false);
        newgamepanel = new JPanel(new GridLayout(3,1),true);
        newGameFrame.add(newgamepanel);
        selectedMap= new JSpinner();
        if(view!=null)
        selectedMap.setModel(new SpinnerListModel(view.getMenu().viewAvailableMaps()));
        String[] rules = {"Basic","Advanced"};
        selectedRules= new JSpinner(new SpinnerListModel(rules));
        sendNewGame = new JButton("Create");
        sendNewGame.setName("sendnewgame");
        sendNewGame.addMouseListener(eventHandler);
        newgamepanel.add(selectedMap,0,0);
        newgamepanel.add(selectedRules,1,0);
        newgamepanel.add(sendNewGame,2,0);
        return newGameFrame;
    }

    /**
     * @return the game info of the newly created games 
     * to be forwarded to the server
     */
    public GameInfo getGameInfo() {
        gameInfo = new OnlineGameInfo();
        gameInfo.setMapPath((String) selectedMap.getValue());
        Rules rules = parseRules((String)selectedRules.getValue());
        gameInfo.setRules(rules);
        gameInfo.setNumberOfPlayers(8);
        return gameInfo;
    }

    private Rules parseRules(String value) {
        if("Basic".equals(value))
            return new BasicGameRules();
        else
            return new AdvancedGameRules();
    }

}
