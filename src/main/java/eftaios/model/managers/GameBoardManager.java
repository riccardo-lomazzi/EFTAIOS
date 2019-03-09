package eftaios.model.managers;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import eftaios.model.avatars.HumanPlayer;
import eftaios.model.avatars.Player;
import eftaios.model.board.GameBoard;
import eftaios.model.board.Sector;
import eftaios.model.events.GameEvent;
import eftaios.model.events.IllegalActionEvent;

public class GameBoardManager implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2976495034309726259L;
    private GameBoard gameBoard;

    public GameBoardManager(String path) throws FileNotFoundException {
        gameBoard = new GameBoard(path);
    }

     
     /**
      * Function that checks if the map has been found, loaded and stored
      * @return true if the map exists, false if not
      * @param nothing
      */
     public boolean doesMapExists(){
         return gameBoard.getMapLoaded();
     }
     
     /**
      * Function that executes the move request of a player.
      * First if gets the starting position the movement, the destination, and tries to move
      * Then, if the move is correct (if he hasn't move on -the same place as before or-the starting position or
      * -his species' starting sector) it will change his position and set his alreadyMoved flag as true.
      * @return GameEvent related to that position, if the move is successful, else IllegalActionEvent
      * @param player that wants to move, destination as String
      */
    public GameEvent movePlayer(Player player, String destination){
            Sector startingPosition=player.getPosition();
            Sector realDestination = gameBoard.getSector(destination);
            Sector temp = gameBoard.moveTo(player.getPosition(), realDestination);
            /*
             * if moveTo is successful, the new position will be the destination
             * if it isn't the player position won't be changed 
             */
            if(isMoveCorrect(temp, realDestination, player, startingPosition)) {
                player.setPosition(temp);
                generateAdjacentSectors(player);
                player.incrementMovesThisTurn();
                player.setAlreadyMoved(true);
                return player.getPosition().getEvent();
            }
            else
                return new IllegalActionEvent("Unable to move to target location");
    }

    private boolean isMoveCorrect(Sector temp, Sector realDestination, Player player, Sector startingPosition) {
        return temp.equals(realDestination) && !temp.equals(player.getTurnStartingPosition()) && !startingPosition.equals(temp);

    }


    /**
     * Function that sets the starting position of humans and aliens by looping through the players List
     * and setting the positions based on what is set on the hexmap. It also generates the adjacent sector
     * of that player
     * @return void
     * @param array of players from the PlayerManager
     */
    public void movePlayersOnTheStartingPositions(List <Player> arrayOfPlayers) {
        for(Player player:arrayOfPlayers){
            if(player instanceof HumanPlayer)
                player.setPosition(gameBoard.getHumanStartingSector());
            else
                player.setPosition(gameBoard.getAlienStartingSector());
            generateAdjacentSectors(player);
        }
    }

    /**
     * Function that sets the adjacents of that player by reading the char of it's position. 
     * If it's even (B,D, ecc) call getEventAdjacent, getOddAdjacent if otherwise.
     * They both work with the player's position as the center one.
     * @return void
     * @param players of which the adjacents are needed
     */
    public void generateAdjacentSectors(Player player) {
        Sector center = player.getPosition();
        if (Sector.isCharEven(center.getCharId())) {
            player.setAdjacentSectors(getEvenAdjacent(center));
        } else {
            player.setAdjacentSectors(getOddAdjacent(center));
        }
    }

    private List<Sector> getOddAdjacent(Sector center) {
        List<Sector> temp = new ArrayList<Sector>(6);
        Integer IntID = center.getIntId();
        Character CharID = center.getCharId();
        Sector adjacent;
        int j = 1;
        for (int i = 0; i < 3; i++) {
            adjacent = gameBoard.getSector("" + (char) (CharID + j) + "" + (IntID - 1));
            j--;
            if (adjacent != null)
                temp.add(adjacent);
        }
        j = 1;
        for (int i = 0; i < 3; i++) {
            adjacent = gameBoard.getSector("" + (char) (CharID + j) + "" + (IntID + 1 - Math.abs(j)));
            j--;
            if (adjacent != null)
                temp.add(adjacent);
        }
        return temp;
    }

    private List<Sector> getEvenAdjacent(Sector center) {
        List<Sector> temp = new ArrayList<Sector>(6);
        Integer IntID = center.getIntId();
        Character CharID = center.getCharId();
        Sector adjacent;
        int j = 1;
        for (int i = 0; i < 3; i++) {
            adjacent = gameBoard.getSector("" + (char) (CharID + j) + "" + (IntID + 1));
            j--;
            if (adjacent != null)
                temp.add(adjacent);
        }
        j = 1;
        for (int i = 0; i < 3; i++) {
            adjacent = gameBoard.getSector("" + (char) (CharID + j) + "" + (IntID - 1 + Math.abs(j)));
            j--;
            if (adjacent != null)
                temp.add(adjacent);
        }
        return temp;
    }

    /**
     * Function that gets the starting sector of a human on the gameboard
     * @return HumanStartingSector
     * @param nothing
     */
    public Sector getHumanStartingSector() {
        return gameBoard.getHumanStartingSector();
    }

    /**
     * Function that gets the starting sector of an alien on the gameboard
     * @return AlienStartingSector
     * @param nothing
     */
    public Sector getAlienStartingSector() {
        return gameBoard.getAlienStartingSector();
    }


    /**
     * Function that checks a sector is near a "center" one, or it's the same 
     * @return true if the sector to be checked is near the "center" one or it's the center.
     * @param sector toBeChecked to be checked if near the "center" sector
     */
  
    public static boolean isInPositionOrAdjacent(Sector toBeChecked, Sector realCenter) {
        return Sector.areNear(realCenter, toBeChecked) || realCenter.equals(toBeChecked);
    }

    public GameBoard getMap() {
        return gameBoard;
    }

}
