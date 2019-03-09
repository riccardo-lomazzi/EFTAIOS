package eftaios.network.socket.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;

import eftaios.ExceptionLogger;
import eftaios.controller.UserInput;
import eftaios.network.socket.SocketProtocolClientToServer;
import eftaios.view.View;

public class SocketClientOutput implements Observer {

    private ObjectOutputStream writer;
    private UserInput userInput;
    private BigInteger gameID;
    private SocketProtocolClientToServer protocol;
    private InetAddress clientID;
    private boolean stop;

    /**
     * Create an object to habdle the outgoing protocols from the client to the
     * server
     */
    public SocketClientOutput() {
        stop = false;
    }

    /**
     * Set the output stream on which this object will write
     * 
     * @param socket
     *            the socket holding the connection between client and server
     */
    public void setOutputStream(OutputStream outputStream) {
        try {
            writer = new ObjectOutputStream(outputStream);
        } catch (IOException e) {
            ExceptionLogger.info(e);
        }
    }

    /**
     * This object extends the observer interface and will observe the client's
     * view when the view notify something the update method in this object will
     * forward them to the server
     * 
     * @param o
     *            The client view
     * @param arg
     *            the user input
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof View && arg instanceof UserInput) {
            try {
                userInput = (UserInput) arg;
                clientID = ((View) o).getViewID();
                /*
                 * this check must be done as this field can be null
                 * i.e. the first time the client connect the client
                 * has no game thus model and game and game id are null
                 * the server can handle this null fields
                 */
                if (((View) o).getModel() != null && ((View) o).getModel().getGame() != null)
                    /*
                    *get the game identifier of the game
                    *the view is playing
                    */
                    gameID = ((View) o).getModel().getGame().getGameID();
                /*
                 * the protocol from client to server requires 
                 * this three objects to correctly handle the client
                 */
                write(userInput, clientID, gameID);
            } catch (IOException e) {
                stop = true;
                ExceptionLogger.info(e);
            } catch (NullPointerException e) {
                stop = true;
                ExceptionLogger.info(e);
            }
        }
    }

    private void write(UserInput userInput, InetAddress clientID, BigInteger gameID) throws IOException, SocketException {
        writer.reset();
        if (userInput != null) {
            protocol = new SocketProtocolClientToServer();
            protocol.setUserInput(userInput);
            protocol.setClientID(clientID);
            protocol.setGameID(gameID);
            writer.writeObject(protocol);
        }
        writer.flush();
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
