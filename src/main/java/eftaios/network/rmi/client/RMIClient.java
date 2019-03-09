package eftaios.network.rmi.client;

public class RMIClient{

    public RMIClient() {
    }

    public static void main(String[] args) {
       ClientWithRemoteImplementation cwri=new ClientWithRemoteImplementation("EFTAIOS_server");
       Thread newThread=new Thread(cwri);
       newThread.start();

    }

}
