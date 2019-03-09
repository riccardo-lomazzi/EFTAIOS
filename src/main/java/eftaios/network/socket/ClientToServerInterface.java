package eftaios.network.socket;

import java.math.BigInteger;
import java.net.InetAddress;

import eftaios.controller.UserInput;

public interface ClientToServerInterface {
    public void setClientID(InetAddress clientID);
    public void setUserInput(UserInput userInput);
    public void setGameID(BigInteger gameID);
    public InetAddress getClientID();
    public UserInput getUserInput();
    public BigInteger getGameID();
}
