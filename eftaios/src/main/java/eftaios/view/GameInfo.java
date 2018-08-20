package eftaios.view;

import java.io.Serializable;

import eftaios.model.gamerules.Rules;

public class GameInfo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1720239513908283812L;
    private int numberOfPlayers;
    private Rules rules;
    private String mapPath;


    /**
     * Create an object with all the information 
     * to create a new game
     */
    public GameInfo() {
    }
    
    /**
     * Function that gets the number of players setted up by the user
     * @param nothing
     * @return integer number of players
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Function that sets the number of players set up by the user
     * @param number of players to be set
     * @return void
     */
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Function that sets the rules set up by the user
     * @param nothing
     * @return Rules set up
     */
    public Rules getRules() {
        return rules;
    }

    /**
     * Function that sets the rules of the Game
     * @param rules to be set
     * @return void
     */
    public void setRules(Rules rules) {
        this.rules = rules;
    }

    /**
     * Function that gets the map path for the hexmap of the GameBoard
     * @param nothing
     * @return String path of hexmap file
     */
    public String getMapPath() {
        return mapPath;
    }

    /**
     * Function that sets the map path for the hexmap of the GameBoard
     * @param String mapPath
     * @return void
     */
    public void setMapPath(String mapPath) {
        this.mapPath = mapPath;
    }

}
