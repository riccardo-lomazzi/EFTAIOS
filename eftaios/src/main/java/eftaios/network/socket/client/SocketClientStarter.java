package eftaios.network.socket.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.SecureRandom;

import eftaios.ExceptionLogger;
import eftaios.view.View;
import eftaios.view.gui.GUIClient;
import eftaios.view.gui.OnlineGUIClientMenu;

public class SocketClientStarter implements Runnable{

    private View view;
    private SocketClientInput input;
    private SocketClientOutput output;
    private Socket socket;
    private int port;
    private static int i = 1;
    
    /**
     * Create a socket client with an attached view
     * @param view View that will be started
     */
    public SocketClientStarter(View view) {
        this.view = view;
    }

    /**
     * Set the connection with the server at a given port
     * @param port The port where the game need to socket
     */
    private void setSocket(int port) throws IOException {
        socket = new Socket();
        this.port = port;
        i = new SecureRandom().nextInt(100);
        socket.bind(new InetSocketAddress("127.0.0."+i,0));
        this.output = new SocketClientOutput();
        this.input = new SocketClientInput();
        view.addObserver(output);
        input.setView(view);
        view.setViewID(socket.getLocalAddress());
    }
    
    /**
     * Starts the server
     */
    @Override
    public void run() {
        try {
            connect();
            output.setOutputStream(socket.getOutputStream());
            input.setInputStream(socket);
            Thread viewThread = new Thread(view);
            viewThread.start();
            while (view.isRunning()) {
                input.run();
                if(input.isClosed()||input.stop()||output.stop())
                    break;
            }
            socket.close();
        } catch (Exception e) {
            ExceptionLogger.info(e);
        }
    }
    
    private void connect() throws IOException {
        socket.connect(new InetSocketAddress("localhost",port ));
    }

    public static void main(String[] args) {
//        InputStream inStream = System.in;
//        View view = new CLIClient(new OnlineCLIClientMenu(inStream, System.out));
        View view = new GUIClient(new OnlineGUIClientMenu());
        SocketClientStarter client = new SocketClientStarter( view );
        try {
            client.setSocket(40000 );
            client.run();
        } catch (UnknownHostException e) {
            ExceptionLogger.info(e);
        } catch (IOException e) {
            ExceptionLogger.info(e);
        }
    }
}
