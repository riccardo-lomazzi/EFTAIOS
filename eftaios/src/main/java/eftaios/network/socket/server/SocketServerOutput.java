package eftaios.network.socket.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import eftaios.ExceptionLogger;
import eftaios.model.Model;
import eftaios.model.avatars.Player;
import eftaios.model.events.GameEvent;
import eftaios.network.socket.OnlineGameInfo;
import eftaios.network.socket.SocketProtocolServerToClient;

public class SocketServerOutput implements Observer {

    private ObjectOutputStream writer;
    private Model model;
    private GameEvent event;
    private SocketProtocolServerToClient protocol;
    private Player player;
    private OnlineGame onlineGame;
    private Socket socket;
    private boolean stop;
    private static Map<BigInteger, OnlineGame> onlineGames;
    private static List<OnlineGameInfo> availableMatches;

    /**
     * This class will handle the output stream side of the server writing to
     * client
     * 
     * @param onlineGames
     *            The Map containing the online games on the server
     * @param availableMatches
     *            The list holding the reference to the online games
     */
    public SocketServerOutput(Map<BigInteger, OnlineGame> onlineGames, List<OnlineGameInfo> availableMatche) {
        SocketServerOutput.onlineGames = onlineGames;
        SocketServerOutput.availableMatches = availableMatche;
        stop = false;
    }

    /**
     * Set the output stream on which this object will write
     * 
     * @param socket
     *            the socket holding the connection between server and client
     */
    public void setOutputStream(Socket socket) {
        try {
            writer = new ObjectOutputStream(socket.getOutputStream());
            this.socket = socket;
        } catch (IOException e) {
            ExceptionLogger.info(e);
        }
    }

    /**
     * This object extends the observer interface and will observe the server's
     * model when the model notify something the update method in this object
     * will forward them to the client
     * 
     * @param o
     *            The server model
     * @param arg
     *            the game event
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Model && arg instanceof GameEvent) {
            model = (Model) o;
            event = (GameEvent) arg;
            /*
             * updating the online game with the new model
             */
            onlineGame = onlineGames.get(model.getGame().getGameID());
            onlineGame.setModel(model);
            //get the related player to this socket client
            checkPlayerAssignment(onlineGame);
            try {
                write(generateProtocol(model, event));
            } catch (SocketException e) {
                ExceptionLogger.info(e);
                stop = true;
            }
        }
    }

    private void checkPlayerAssignment(OnlineGame onlineGame) {
        onlineGame.reAssignClientsToPlayes();
        player = onlineGame.getPlayer(socket.getInetAddress());
    }

    /**
     * This method is used to forward a model and a game event to the client
     * without passing trough the model, it is used for not game communication
     * i.e. when the client is not playing to a game but still connecting
     * @param event the event to be notified
     * @param model the model to be send it can be null or with a null game
     */
    public void sendToClient(GameEvent event, Model model) {
        try {
            write(generateProtocol(model, event));
        } catch (SocketException e) {
            ExceptionLogger.info(e);
            stop = true;
        }
    }

    private SocketProtocolServerToClient generateProtocol(Model model, GameEvent event) {
        protocol = new SocketProtocolServerToClient();
        protocol.setModel(model);
        protocol.setEvent(event);
        protocol.setMatches(availableMatches);
        protocol.setPlayer(player);
        return protocol;
    }

    private void write(SocketProtocolServerToClient protocol) throws SocketException {
        try {
            writer.reset();
            writer.writeObject(protocol);
            writer.flush();
        } catch (IOException e) {
            ExceptionLogger.info(e);
            stop = true;
        }
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
