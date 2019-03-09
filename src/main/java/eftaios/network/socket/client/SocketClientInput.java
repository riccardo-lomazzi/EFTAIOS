package eftaios.network.socket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Observable;

import eftaios.ExceptionLogger;
import eftaios.model.Model;
import eftaios.model.events.GameEvent;
import eftaios.network.socket.OnlineGameInfo;
import eftaios.network.socket.SocketProtocolServerToClient;
import eftaios.view.View;

public class SocketClientInput extends Observable {

    private ObjectInputStream reader;
    private Model model;
    private GameEvent event;
    private View view;
    private InputStream inputStream;
    private SocketProtocolServerToClient protocol;
    private List<OnlineGameInfo> matches;
    private Socket socket;
    private boolean stop;

    /**
     * Create a new object that will handle the incoming protocols from the
     * server
     */
    public SocketClientInput() {
        model = new Model();
        stop = false;
    }

    /**
     * Set the input stream on which this object will read
     * 
     * @param socket
     *            the socket holding the connection between client and server
     */
    public void setInputStream(Socket socket) {
        this.socket = socket;
        try {
            this.inputStream = socket.getInputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            ExceptionLogger.info(e);
        }
        createWriter();
    }

    protected void createWriter() {
        try {
            reader = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            ExceptionLogger.info(e);
        }
    }

    /**
     * Start to listening to input stream
     */
    public void run() {
        try {
            // read data from the protocol
            readProtocol();
            // update elements
            updateView();
            if (model != null) {
                updateModel();
            }
        } catch (IOException e) {
            stop = true;
            ExceptionLogger.info(e);
        } catch (ClassNotFoundException e) {
            stop = true;
            ExceptionLogger.info(e);
        } catch (Exception e) {
            stop = true;
            ExceptionLogger.info(e);
        }
    }

    protected void updateModel() {
        model.deleteObservers();
        model.addObserver(view);
        // force the model to change and notify
        model.ChangeAndNotifyObservers(event);
    }

    protected void updateView() {
        view.setCurrentPlayer(protocol.getPlayer());
        view.setMatches(matches);
    }

    protected void readProtocol() throws IOException, ClassNotFoundException, SocketException {
        protocol = (SocketProtocolServerToClient) reader.readObject();
        model = protocol.getModel();
        matches = protocol.getAvailableMatches();
        event = protocol.getEvent();
    }

    /**
     * Set the view that the client is using
     * 
     * @param view
     *            The Client's View
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Check if this object needs to stop
     * 
     * @return true if an error occurred and this object needs to stop
     */
    public boolean stop() {
        return stop;
    }

    /**
     * Check if the socket used in this object is closed
     *
     * @return true if the socket is closed
     */
    public boolean isClosed() {
        return socket.isClosed();
    }

}
