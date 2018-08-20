package eftaios.network.socket;

import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import eftaios.ExceptionLogger;
import eftaios.network.socket.server.OnlineGame;
import eftaios.network.socket.server.SocketServerInput;
import eftaios.network.socket.server.SocketServerOutput;

public class ClientRequest implements Callable<SocketServerInput> {

    private SocketServerOutput output;
    private SocketServerInput input;

    /**
     * This class will create an object to handle a client request 
     * @param socket The socket the player used to connect to the server
     * @param onlineGames The Map containing the online games on the server
     * @param availableMatches The list holding the reference to the online games
     */
    public ClientRequest(Socket socket,Map<BigInteger, OnlineGame> onlineGames, List<OnlineGameInfo> availableMatches) {
        this.output = new SocketServerOutput(onlineGames,availableMatches);
        this.input = new SocketServerInput( socket,output,onlineGames,availableMatches);
        try {
            this.output.setOutputStream(socket);
            this.input.setInputStream(socket.getInputStream());
        } catch (IOException e) {
            ExceptionLogger.info(e);
        }
    }

    /**
     * Start the client request handler 
     */
    @Override
    public SocketServerInput call() {
        /*
         * This object will keep reading from the buffer
         * until the socket is opened or an error as occurred
         */
            try {
                while(input.isListening()) {
                    input.runInput();
                    if(input.getSocket().isClosed()||input.stop()||output.stop())
                        break;
                }
            } catch (Exception e) {
                ExceptionLogger.info(e);
            }
        return null;
    }

}
