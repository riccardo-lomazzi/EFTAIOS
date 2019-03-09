package eftaios.network.socket.server;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eftaios.ExceptionLogger;
import eftaios.network.socket.ClientRequest;
import eftaios.network.socket.OnlineGameInfo;

public class ServerSocketStarter implements Runnable{

    private final int PORT;
    private ServerSocket server;
    private static Map<BigInteger,OnlineGame> onlineGames;
    private static List<OnlineGameInfo> availableMatches;
    private Socket socket;
    private PrintStream serverLogger;
    private boolean serverUp;
    private ExecutorService executors;
    
    /**
     * Creates a server on the given port
     *@param port set the server's port
     *@throws IOException throws IOException if the port is not valid or already occupied
     */
    public ServerSocketStarter(int port) throws IOException {
        this.PORT=port;
        InetAddress inet =  InetAddress.getByName("localhost");
        this.server = new ServerSocket(PORT,100,inet);
        serverLogger = new PrintStream(System.out);
        onlineGames = new HashMap<BigInteger,OnlineGame>();
        availableMatches = new ArrayList<OnlineGameInfo>();
        executors = Executors.newFixedThreadPool(100);
    }
    
    /**
     * Run the server listening for socket, when a socket is accepted
     * the server delegates the handling of the socket to a thread 
     * from the executors pool
     */
    @Override
    public void run() {
                serverLogger.println("Server is Up");
                serverUp=true;
                try {
                    while (serverUp) {
                        socket = server.accept();
                        executors.submit(new ClientRequest(socket,onlineGames,availableMatches));
                    }
                } catch (IOException e) {
                    serverUp=false;
                    ExceptionLogger.info(e);
                } catch (Exception e) {
                    serverUp=false;
                    ExceptionLogger.info(e);
                }
    }

    public static void main(String[] args) {
        ServerSocketStarter server = null;
        try {
            server = new ServerSocketStarter(40000);
            server.run();  
        } catch (IOException e) {
            ExceptionLogger.info(e);
        }     
    }

}
