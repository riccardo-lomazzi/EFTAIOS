package eftaios.network.socket.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import eftaios.ExceptionLogger;
import eftaios.controller.Controller;
import eftaios.controller.CreateGameInput;
import eftaios.controller.OnlineCreateGameInput;
import eftaios.controller.SocketConnectToGameInput;
import eftaios.controller.UserInput;
import eftaios.model.Model;
import eftaios.model.avatars.Player;
import eftaios.model.events.ConnectedToGameEvent;
import eftaios.model.events.GameEvent;
import eftaios.model.events.IllegalActionEvent;
import eftaios.model.gamerules.AdvancedGameRules;
import eftaios.model.gamerules.Rules;
import eftaios.network.socket.OnlineGameInfo;
import eftaios.network.socket.SocketProtocolClientToServer;
import eftaios.view.View;

public class SocketServerInput extends View {

    private Controller controller;
    private UserInput userInput;
    private SocketServerOutput serverOut;
    private ObjectInputStream reader;
    private BigInteger gameID;
    private boolean listening;
    private SocketProtocolClientToServer protocol;
    private InetAddress client;
    private GameEvent event;
    private Socket socket;
    private boolean stop;
    private Timer startGameTimer;
    private static Map<BigInteger, OnlineGame> onlineGames;
    private static List<OnlineGameInfo> availableMatches;
    private static Map<InetAddress, SocketServerOutput> clientsHandlers = new HashMap<InetAddress, SocketServerOutput>();

    /**
     * This class will create an object to handle the socket input stream
     * 
     * @param socket
     *            The socket the player used to connect to the server
     * @param onlineGames
     *            The Map containing the online games on the server
     * @param availableMatches
     *            The list holding the reference to the online games
     */
    public SocketServerInput(Socket socket, SocketServerOutput output, Map<BigInteger, OnlineGame> onlineGames, List<OnlineGameInfo> availableMatches) {
        this.socket = socket;
        serverOut = output;
        stop = false;
        /*
         * add the connected client to a Map associating each client to the
         * object that handles their output stream so that when an online games
         * notify something each object handling a client request can broadcast
         * it to all the connected clients that are interested in that game
         */
        clientsHandlers.put(socket.getInetAddress(), serverOut);
        SocketServerInput.onlineGames = onlineGames;
        SocketServerInput.availableMatches = availableMatches;
    }

    /**
     * Set the input stream on which this object will read
     * 
     * @param socket
     *            the socket holding the connection between client and server
     */
    public void setInputStream(InputStream inputStream) {
        try {
            reader = new ObjectInputStream(inputStream);
            listening = true;
        } catch (IOException e) {
            ExceptionLogger.info(e);
        }
    }

    /**
     * Start to listening to input stream
     */
    public void runInput() {
        // if there are not available online games one is created
        if (availableMatches.isEmpty())
            createGameOnServer();
        // All connection needed variable are retrieved from the buffer
        try {
            getDataFromProtocol();
        } catch (SocketException e) {
            ExceptionLogger.info(e);
            stop = true;
        }
        // the received input is handled
        checkInput();
        // all the ready games are started
        startGames();
    }

    private synchronized void createGameOnServer() {
        /*
         * a standard game is created on the server the number of players will
         * be changed based on the number of clients connected
         */
        Rules rules = new AdvancedGameRules();
        OnlineGameInfo onlineGameInfo = new OnlineGameInfo();
        onlineGameInfo.setMapPath("Galilei");
        onlineGameInfo.setRules(rules);
        onlineGameInfo.setNumberOfPlayers(8);
        createGame(onlineGameInfo);
    }

    private synchronized void createGame(OnlineGameInfo onlineGameInfo) {
        /*
         * the created game is put in a Map holding the online games and its
         * reference is added to the available games
         */
        Model temp = new Model();
        temp.deleteObservers();
        temp.createGame(onlineGameInfo.getNumberOfPlayers(), onlineGameInfo.getRules(), onlineGameInfo.getMapPath());
        onlineGameInfo.setGameID(temp.getGame().getGameID());
        onlineGames.put(temp.getGame().getGameID(), new OnlineGame(temp));
        availableMatches.add(onlineGameInfo);
    }

    private synchronized void startGames() {
        /*
         * the online Games map is scanned for only the available games not the
         * ready or playing ones and if they are ready the controller is
         * notified
         */
        OnlineGame temp;
        for (OnlineGameInfo onlineGameInfo : availableMatches) {

            if (onlineGameInfo != null && onlineGameInfo.getGameID() != null) {

                temp = onlineGames.get(onlineGameInfo.getGameID());

                if (temp != null && temp.isReady() && !temp.isPlaying() && !temp.isStarting()) {
                    /*
                     * a thread will start the game at the 
                     * end of a timer
                     */
                    temp.setStarting(true);
                    waitTimeToStart(onlineGameInfo);
                }
            }
            if (availableMatches.size() <= 0)
                break;
        }
    }

    protected void waitTimeToStart(final OnlineGameInfo onlineGameInfo) {
        /*
         * creating a new game based on the old one
         */
        startGameTimer = new Timer();
        startGameTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                /*
                 * a new game will be created with the exact number of
                 * player corresponding to the number of clients connected
                 * to that game
                 */
                OnlineGame temp = onlineGames.get(onlineGameInfo.getGameID());
                onlineGameInfo.setNumberOfPlayers(temp.getNumberOfConnectedPlayers());
                final OnlineCreateGameInput onlineUserInput = new OnlineCreateGameInput(onlineGameInfo);
                final BigInteger tempID = temp.getModel().getGame().getGameID();
                final OnlineGameInfo tempInfo = onlineGameInfo;
                availableMatches.remove(tempInfo);
                notifyControllerOfNewGame(tempID, client, onlineUserInput);
                cancel();
            }
        }, 20 * 1000);
    }
    
    private void notifyControllerOfNewGame(BigInteger gameID, InetAddress client, OnlineCreateGameInput onlineUserInput) {
        /*
         * the new game must have the game id of the old one
         */
        OnlineGame onlineGame = onlineGames.get(gameID);
        onlineGame.setPlaying(true);
        /*
         * a new model is created and will be notified with the online create
         * game user input
         */
        Model model = new Model();
        /*
         * all the server object holding the output streams connected to the
         * clients which are interested in this game are added as observer to
         * this model
         */
        addObserversTo(model, onlineGame);
        /*
         * this object simulate a view and notify the controller so that the
         * game will be created
         */
        controller = new Controller(model, this);
        deleteObservers();
        addObserver(controller);
        setChanged();
        notifyObservers(onlineUserInput);
    }

    private void getDataFromProtocol() throws SocketException {
        try {
            protocol = (SocketProtocolClientToServer) reader.readObject();
            
        } catch (ClassNotFoundException e) {
            stop = true;
            ExceptionLogger.info(e);
        } catch (IOException e) {
            stop = true;
            ExceptionLogger.info(e);
        }
        this.userInput = protocol.getUserInput();
        this.client = protocol.getClientID();
        this.gameID = protocol.getGameID();
    }

    private void checkInput() {
        if (gameID == null && client != null || userInput instanceof SocketConnectToGameInput || userInput instanceof CreateGameInput) {
            /*
             * this case handle the connection requests from the client there
             * are two cases
             */
            if (userInput instanceof SocketConnectToGameInput)
                handleConnectToGame();
            if (userInput instanceof CreateGameInput)
                handleCreateGame();
        } else {
            /*
             * this case handle the game request from the client
             */
            connectedPlayer = getCorrespondingPlayerInGame(client, gameID);
            if (connectedPlayer == null && gameID != null)
                serverOut.sendToClient(new IllegalActionEvent("You are not in that game"), null);
            else
                handleGenericInput();
        }
    }

    private void handleCreateGame() {
        /*
         * a new game is created and its reference added to the end of the
         * available games and the client is given that game
         */
        OnlineGameInfo onlineGameInfo = new OnlineGameInfo(((CreateGameInput) userInput).getGameinfo());
        createGame(onlineGameInfo);
        giveClientAGame(client, availableMatches.size() - 1);
    }

    private void handleConnectToGame() {
        /*
         * the client is given the selected game if the selected game is not
         * valid the client is not given anything the selectedGame value -1 is
         * used by the clients to refresh their available game list
         */
        int selectedGame = ((SocketConnectToGameInput) userInput).getSelectedGame();
        if (selectedGame >= 0 && selectedGame <= availableMatches.size() - 1)
            giveClientAGame(client, selectedGame);
        else
            serverOut.sendToClient(new ConnectedToGameEvent("Server Connection established"), new Model());
    }

    private void handleGenericInput() {
        notifyController(onlineGames.get(gameID), client, userInput);
    }

    private Player getCorrespondingPlayerInGame(InetAddress client, BigInteger gameID) {
        if (client == null || gameID == null)
            return null;
        return onlineGames.get(gameID).getPlayer(client);
    }

    /**
     * This method will add a reference to a client inside the selected game and
     * then notify the result to the player
     * 
     * @param client
     *            the client that will receive a game
     * @param the
     *            selected game
     */
    private void giveClientAGame(InetAddress client, int i) {
        OnlineGame temp = onlineGames.get(availableMatches.get(i).getGameID());
        /*
         * the client is removed from the other available games not from the one
         * he is playing
         */
        for (OnlineGameInfo gameInfo : availableMatches)
            onlineGames.get(gameInfo.getGameID()).removeClient(client);
        event = temp.addClientID(client);
        onlineGames.put(availableMatches.get(i).getGameID(), temp);
        /*
         * the client is notified of the result of his request
         */
        serverOut.sendToClient(event, temp.getModel());
    }

    private void notifyController(OnlineGame onlineGame, InetAddress client, UserInput userInput) {
        /*
         * all observers are deleted to prevent multiple calls on a method from
         * illegal objects
         */
        model = onlineGame.getModel();
        addObserversTo(model, onlineGame);
        controller = new Controller(model, this);
        deleteObservers();
        addObserver(controller);
        setChanged();
        notifyObservers(userInput);
    }

    private void addObserversTo(Model model, OnlineGame onlineGame) {
        /*
         * all previous observer are deleted to prevent illegal calls from
         * object not related to this model
         */
        model.deleteObservers();
        /*
         * when the model will notify something it will be broad casted to all
         * the clients interested with this model
         */
        List<InetAddress> clients = onlineGame.getConnectedClients();
        Map<InetAddress, Player> connectedPlayers = onlineGame.getConnectedPlayers();
        for (InetAddress tempClient : clients) {
            if (connectedPlayers.get(tempClient) != null) {
                SocketServerOutput handler = clientsHandlers.get(tempClient);
                if(handler!=null)
                model.addObserver(handler);
            }
        }
    }

    /**
     * @return listening is true if the stream attached to this process is open
     */
    public boolean isListening() {
        return listening;
    }

    public Socket getSocket() {
        return socket;
    }

    /**
     * Check if this object needs to stop
     * 
     * @return true if an error occurred and this object needs to stop
     */
    public boolean stop() {
        return stop;
    }
}
