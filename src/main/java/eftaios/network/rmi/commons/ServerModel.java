package eftaios.network.rmi.commons;

import java.io.FileNotFoundException;
import java.util.List;

import eftaios.ExceptionLogger;
import eftaios.model.Model;
import eftaios.model.avatars.Player;
import eftaios.model.events.GameEvent;
import eftaios.model.events.IllegalActionEvent;
import eftaios.model.gamerules.Rules;

public class ServerModel extends Model implements Cloneable {

    /**
     * Class that adds a pair of new functions to the original Model,
     * specifically to get the next player starting from the currentPlayer, and
     * a connection counter to save how many people are connected to the game.
     */
    private static final long serialVersionUID = -8076350883335813289L;
    private int playerConnectionCounter; // it's a token for the connected
                                         // players
    private int nextPlayerToReturn;
    private GameEvent gameEvent;

    public ServerModel() {
        playerConnectionCounter = 0;
        nextPlayerToReturn = 0;
    }

    /**
     * This ENUM is created to choose between returning a currentPlayer to the
     * first user connecting, and other player for the users.
     * */
    public enum TypeOfPlayer {
        CURRENT, OTHER
    }

    /**
     * Function that is called when the user on the server has configured the
     * match. It then already start the match with the first turn being
     * executed.
     * 
     * @return void
     * @param numberOfPlayers
     *            , Rules of the match, hex mapPath in String format
     */
    @Override
    public void createGame(int numberOfPlayers, Rules rules, String mapPath) {
        try {
            game = new ServerGame(numberOfPlayers, rules, mapPath);
        } catch (FileNotFoundException e) {
            ExceptionLogger.info(e);
            setChanged();
            notifyObservers(new IllegalActionEvent(e.getMessage()));
        }

        // executes the first turn in the game
        gameEvent = game.firstGameTurn(); // saves the game event for the first
                                          // connection

    }

    public GameEvent getGameEvent() {
        return gameEvent;
    }

    /**
     * Function that gets the list of all players by calling the corresponding
     * method on the Game. It is used by the server to assign PlayersID to the
     * ones that connects
     * 
     * @return List of players already created
     * @param nothing
     */
    private List<Player> getListOfPlayers() {
        return ((ServerGame) game).getListOfPlayers();
    }

    /**
     * Function that updates the counter of players connected
     * 
     * @return void
     * @param if true, the counter increments, if false it decreases
     */
    private void updatePlayerCounter(boolean increment) {
        if (increment)
            playerConnectionCounter++;
        else
            // the player has disconnected
            playerConnectionCounter--;
    }

    /**
     * Function that returns the next player ID when the client connects. If the
     * player is the first one, the currentPlayer of the already started match
     * is assigned to him. Then the nextPlayerToReturn is updated, to save the
     * index of the next player to get. When another player connects, if the
     * nextPlayerToReturn has already surpassed the size of the list, gets the
     * one in first position. Then it updates the total number of players.
     * 
     * @param typeOfPlayer
     *            that request the actions. The first one is always the
     *            currentPlayer, the others are the OTHER type of Players.
     * @returns Player assigned.
     * */
    public Player getNextPlayer(TypeOfPlayer typePlayer) {
        // this is used for the first player
        Player temp; // if it's the first one, get the currentplayer in the
                     // model
        if (typePlayer.equals(TypeOfPlayer.CURRENT)) {
            temp = getCurrentPlayer();
            nextPlayerToReturn = getListOfPlayers().indexOf(temp) + 1;
        } else // else get the one in the position playerConnectionCounter
        {
            // if we're at the end of the list, get back to the first place
            if (nextPlayerToReturn >= getListOfPlayers().size())
                nextPlayerToReturn = 0;
            // at the end, just give me the player inside the nextPlayerToReturn
            // position

            temp = getListOfPlayers().get(nextPlayerToReturn);
            nextPlayerToReturn++;
        }

        // update the total counter of players
        updatePlayerCounter(true);
        return temp;
    }

    private boolean isGameNull() {
        return game == null;
    }

    /**
     * Function that checks if the game is initialized and the players'hexmap
     * and rules have all been set.
     * 
     * @param nothing
     * @returns true if game initialized, false if not
     * */
    public boolean isModelInitialized() {
        return !isGameNull() && ((ServerGame) game).isGameInitialized();
    }

    /**
     * Overridden function that just ends the turn and returns the event. It
     * does NOT inform the view.
     * 
     * @param Player
     *            requesting the endTurn the others are the OTHER type of
     *            Players.
     * */
    @Override
    public void endTurn(Player player, List<String> log) {
        this.gameEvent = game.endPlayerTurn(player, log);
    }

    /**
     * Function that calls the ChangeAndNotifyObservers of the Model, which is
     * protected.
     * 
     * @param GameEvent
     *            to signal to the view.
     * @return void
     * */
    public void notifyViewObserver(GameEvent event) {
        ChangeAndNotifyObservers(event);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (Exception e) {
            ExceptionLogger.info(e);
            return null;
        }
    }

}
