package eftaios.network.rmi.commons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import eftaios.ExceptionLogger;
import eftaios.model.Model;
import eftaios.model.avatars.Player;
import eftaios.network.rmi.server.ServerInterface;

public class PlayerConnectionData implements Serializable, Cloneable {

    /**
     * Class for storing the data of the player during the session
     */
    
    private static final long serialVersionUID = 7833473887344045446L;
    private ServerInterface server;
    private Model latestModel;
    private Player remotePlayer;
    
    public PlayerConnectionData(Model initialModel, ServerInterface server, Player remotePlayer) {
        this.latestModel=initialModel;
        this.server = server;
        this.remotePlayer = remotePlayer;
    }
    
    public ServerInterface getServer() {
        return server;
    }

    public String getPlayerRemoteID() {
        return remotePlayer.getPlayerID();
    }
    
    public Player getRemotePlayer(){
        return remotePlayer;
    }
    
    public Model getModel(){
        return latestModel;
    }
    
    public void setPlayer(Player currentPlayer){
        this.remotePlayer=currentPlayer;
    }
    
    public void setModel(Model model){
        this.latestModel=model;
    }
    
    @Override
    public Object clone(){  
        try{  
            return super.clone();  
        }catch(Exception e){ 
            return null; 
        }
    }

    public void saveOnDisk() {
            try {
                File createDirectory=new File( "C:/eftaios/saves/rmi_client_saves/match/");
                createDirectory.mkdirs();
                File saveFile=new File( "C:/eftaios/saves/rmi_client_saves/match/match_number_" + System.currentTimeMillis()
                        + ".ser");
                saveFile.createNewFile();
                FileOutputStream fileOut = new FileOutputStream(saveFile);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(this);
                out.close();
                fileOut.close();
            } catch (IOException e) {
                ExceptionLogger.info(e);
            } 
        
    }

}
