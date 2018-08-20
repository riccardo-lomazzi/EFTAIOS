package eftaios.model;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Observable;

import eftaios.ExceptionLogger;
import eftaios.model.avatars.Player;
import eftaios.model.decks.drawables.Item;
import eftaios.model.events.GameEvent;
import eftaios.model.events.IllegalActionEvent;
import eftaios.model.gamerules.Rules;
import eftaios.model.match.Game;

public class Model extends Observable implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6456745242822723545L;
    protected Game game;

    public Model() {

    }

    /**
     * Create a game and notify his observers of the game first turn
     * 
     * @param numberOfPlayers
     *            number of players that will be playing the game
     * @param rules
     *            type of rules that the game will follow
     * @param mapName
     *            name of the map
     */
    public void createGame(int numberOfPlayers, Rules rules, String mapName) {
        try {
            game = new Game(numberOfPlayers, rules, mapName);
        } catch (FileNotFoundException e) {
            ExceptionLogger.info(e);
            notifyObservers(new IllegalActionEvent(e.getMessage()));
        }
        setChanged();
        notifyObservers(game.firstGameTurn());
    }

    /**
     * Create a game with a specific id and notify his observers of the game
     * first turn
     * 
     * @param numberOfPlayers
     *            number of players that will be playing the game
     * @param rules
     *            type of rules that the game will follow
     * @param mapName
     *            name of the map
     * @param gameID
     *            specific identifier for the game
     */
    public void createGame(int numberOfPlayers, Rules rules, String mapName, BigInteger gameID) {
        try {
            game = new Game(numberOfPlayers, rules, mapName, gameID);
        } catch (FileNotFoundException e) {
            ExceptionLogger.info(e);
            notifyObservers(new IllegalActionEvent(e.getMessage()));
        }
        setChanged();
        notifyObservers(game.firstGameTurn());
    }

    /*
     * The following methods will handle the user requests
     */

    /**
     * Handle the player request to move and notify the observers of the result
     * 
     * @param player
     * @param destination
     *            sector complete identifier
     */
    public void moveRequest(Player player, String destination) {
        setChanged();
        notifyObservers(game.movePlayer(player, destination));
    }

    /**
     * Handle the player request to attack and notify the observers of the
     * result
     * 
     * @param player
     */
    public void attackRequest(Player player) {
        setChanged();
        notifyObservers(game.attack(player));
    }

    /**
     * Handle the player request to use an item and notify the observers of the
     * result
     * 
     * @param player
     * @param item
     *            to be used
     */
    public void itemRequest(Player player, Item item) {
        setChanged();
        notifyObservers(game.useItem(player, item));
    }

    /**
     * Handle the player request to draw a card and notify the observers of the
     * result
     * 
     * @param player
     */
    public void drawCardRequest(Player player) {
        setChanged();
        notifyObservers(game.drawCard(player));

    }

    /**
     * Handle the player request to arrange his items and notify the observers
     * of the result
     * 
     * @param player
     * @param itemsList
     *            list of items to be set
     */
    public void setItemRequest(Player player, List<Item> itemsList) {
        setChanged();
        notifyObservers(game.setItemsTo(player, itemsList));

    }

    /**
     * Handle the player request to end his turn and notify the observers of the
     * result
     * 
     * @param player
     */
    public void endTurn(Player player, List<String> log) {
        setChanged();
        notifyObservers(game.endPlayerTurn(player, log));
    }

    /**
     * Handle the player request to end his match and notify the observers of
     * the result
     * 
     * @param player
     */
    public void endGame(Player player, List<String> log) {
        game.removePlayer(player);
        setChanged();
        notifyObservers(game.endPlayerTurn(player, log));
    }

    /**
     * Handle the player request to see the game log and notify the observers of
     * the result
     */
    public void logRequest() {
        setChanged();
        notifyObservers(game.getLog());
    }

    public Game getGame() {
        return game;
    }

    public void ChangeAndNotifyObservers(GameEvent event) {
        setChanged();
        notifyObservers(event);
    }

    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

}
