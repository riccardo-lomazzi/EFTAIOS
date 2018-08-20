package eftaios.network.socket;

import java.io.Serializable;
import java.math.BigInteger;
import java.net.InetAddress;

import eftaios.controller.UserInput;

public class SocketProtocolClientToServer implements ClientToServerInterface, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5151567760091758147L;
    private InetAddress clientID;
    private UserInput userInput;
    private BigInteger gameID;

    /**
     * Create a new protocol to send data from
     * the client to the server
     */
    public SocketProtocolClientToServer() {
    }

    @Override
    public void setClientID(InetAddress clientID) {
        this.clientID = clientID;
    }

    @Override
    public void setUserInput(UserInput userInput) {
        this.userInput = userInput;
    }

    @Override
    public void setGameID(BigInteger gameID) {
        this.gameID = gameID;
    }

    @Override
    public UserInput getUserInput() {
        return userInput;
    }

    @Override
    public BigInteger getGameID() {
        return gameID;
    }

    @Override
    public InetAddress getClientID() {
        return clientID;
    }

}
