package eftaios.model.managers;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import eftaios.model.avatars.HumanPlayer;
import eftaios.model.avatars.Player;
import eftaios.model.events.EndGameEvent;
import eftaios.model.events.GameEvent;
import eftaios.model.events.StartPlayerTurnEvent;
import eftaios.model.gamerules.Turn;


public class TurnManager implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = -8439739342499894653L;
    private int currentTurn;
    private Player currentPlayer;

    public TurnManager() {
        currentTurn=0;
    }

    
    /**
     * Function that returns the currentPlayer 
     * @return Player instance of currentPlayer
     * @param nothing
     * */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * Function that select a new player to start the turn, saves it to the currentPlayer variable,
     * and then returns a GameEvent checking if it's the last turn of the game, the first, or a normal turn
     * Also, resets all the stats of the player by calling a specific function (resetPlayer sets the moves
     * the flags for attacking and everything else)
     * @return EndGameEvent if it's the last turn, StartPlayerTurnEvent if the next player is set to start his turn
     * @param array of players retrieved from playerManager 
     * */
    public GameEvent beginTurn(List <Player> playersArray) {
        //SPECIAL CASE 1: Is this the game's last turn? If yes, end turn 
        if(Turn.isGameLastTurn(currentTurn)) {
            return new EndGameEvent("The Game ends here. Thank you For Playing!!");
        }
        else{
            //SPECIAL CASE 2: Is this the first turn of the game? If yes, return a random player
            if(Turn.isGameFirstTurn(currentTurn)) {
                currentPlayer =  getNextRandomPlayerFromArray(playersArray);
            }
            else{ //SPECIAL CASE 3: Is this the normal match? Then assign the next player from the array
                currentPlayer = getNextPlayerFromArray(playersArray, currentPlayer);    
            }  
          //in the new turn, he can use an item and move a certain number of times
            resetPlayer(currentPlayer);
          //and finally we'll update the turn counter
            currentTurn++;
            if(playersArray.size()<=1)
                return new EndGameEvent("You won the game");
            if(onlyAliensRemains(playersArray))
                return new EndGameEvent("Aliens won the game");
            return new StartPlayerTurnEvent("Your turn starts");
       }
    }
    
    private boolean onlyAliensRemains(List<Player> playersArray) {
        int count=0;
        for(Player player: playersArray) {
            if(player instanceof HumanPlayer)
                count++;
        }
        return count<=0;
    }
    
    private void resetPlayer(Player currentPlayer) {
        currentPlayer.setTurnStartingPosition(currentPlayer.getPosition());
        currentPlayer.setAlreadyAttacked(false);
        currentPlayer.setAlreadyMoved(false);
        currentPlayer.setAlreadyDrawed(false);
        currentPlayer.setSedated(false);
        currentPlayer.setAlreadyAttacked(false);
        currentPlayer.setMovesThisTurn(0); 
    }
    /**
     * Function that returns a random player from the Array
     * @param playersArray
     * @return a random Player
     */
    public Player getNextRandomPlayerFromArray(List <Player> playersArray){
        return playersArray.get(new Random().nextInt(playersArray.size()));
    }
    
    /**
     * Function that returns the actual next player from the Array, by checking if the array has been already
     * looped once (then it will return the first element) or still in the process of scrolling through
     * (then will get the element in the currentPlayer position + 1)
     * @param playersArray, currentPlayer
     * @return the next player in the array given the one inserted
     */
    public Player getNextPlayerFromArray(List <Player> playersArray, Player currentPlayer)
    {
        //SPECIAL CASE 1: if the currentPlayer is at the end of the list, return the first
        if(playersArray.indexOf(currentPlayer)==playersArray.size()-1){
            return playersArray.get(0);
        }
        //else return the element at the index of (current+1)
        return playersArray.get(playersArray.indexOf(currentPlayer)+1);
       
    }
    

    /**
     * Function that sets the currentPlayer based on the one inserted as argument
     * @param player to be set as current
     * @return nothing
     */
    public void setCurrentPLayer(Player player) {
        currentPlayer = player;
    }

    
    /**
     * @return current turn of the game
     */
    public int getCurrentTurn() {
        return currentTurn;
    }

    
}
