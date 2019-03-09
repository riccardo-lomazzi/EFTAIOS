package eftaios.model.match;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import eftaios.ExceptionLogger;
import eftaios.model.avatars.AlienPlayer;
import eftaios.model.avatars.HumanPlayer;
import eftaios.model.avatars.Player;
import eftaios.model.board.GameBoard;
import eftaios.model.decks.drawables.Card;
import eftaios.model.decks.drawables.DefenseItem;
import eftaios.model.decks.drawables.GreenEscapePodCard;
import eftaios.model.decks.drawables.Item;
import eftaios.model.events.GameEvent;
import eftaios.model.events.IllegalActionEvent;
import eftaios.model.events.IllegalPlayerEvent;
import eftaios.model.events.LogPrintEvent;
import eftaios.model.events.SystemEventsMessage;
import eftaios.model.events.TooMuchItemsEvent;
import eftaios.model.gamerules.Rules;
import eftaios.model.managers.DeckManager;
import eftaios.model.managers.GameBoardManager;
import eftaios.model.managers.PlayerManager;
import eftaios.model.managers.RulesManager;
import eftaios.model.managers.TurnManager;

/**
 * Class created using the Mediator pattern (it's actually the Mediator)
 * */

public class Game implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8948337993477824106L;
    protected DeckManager deckManager;
    protected PlayerManager playerManager;
    protected RulesManager rulesManager;
    protected TurnManager turnManager;
    protected GameBoardManager gameboardManager;
    protected BigInteger gameID;
    protected File serverLog;

    /**
     * Create a new game with the given settings
     * 
     * @param numberOfPlayers
     *            number of players that will be playing the game
     * @param rules
     *            type of rules that the game will follow
     * @param mapName
     *            name of the map
     */
    public Game(int numberOfPlayers, Rules rules, String mapPath) throws FileNotFoundException {
        this.gameID = new BigInteger(130, new SecureRandom());
        // declaration of every Singleton instance of Managers
        deckManager = new DeckManager();
        playerManager = new PlayerManager(numberOfPlayers);
        rulesManager = new RulesManager(rules);
        turnManager = new TurnManager();
        gameboardManager = new GameBoardManager(mapPath);
    }

    /**
     * Create a new game with the given settings and game identifier
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
    public Game(int numberOfPlayers, Rules rules, String mapPath, BigInteger gameID) throws FileNotFoundException {
        this.gameID = gameID;
        deckManager = new DeckManager();
        playerManager = new PlayerManager(numberOfPlayers);
        rulesManager = new RulesManager(rules);
        turnManager = new TurnManager();
        gameboardManager = new GameBoardManager(mapPath);
    }

    /**
     * Handle the first game of the game initializing components
     * 
     * @return the event that will start the first turn of the first player
     */
    public GameEvent firstGameTurn() {
        serverLog = new File(".//serverGameLogs//gameLog" + gameID.toString() + ".txt");
        try {
            serverLog.createNewFile();
        } catch (IOException e) {
            ExceptionLogger.info(e);
        }
        // 1 - Get the array of players and pass it to the gameboard to assign
        // starting positions
        List<Player> playersArray = playerManager.getPlayersArray();
        gameboardManager.movePlayersOnTheStartingPositions(playersArray);
        // 2 - Create decks
        deckManager.createDecks(rulesManager.getRules());
        // 3 - Already begin the match
        return turnManager.beginTurn(playersArray);
    }

    /**
     * Handle the movement of a player
     * 
     * @param player
     *            the player to be moved
     * @param destination
     *            the destination to reach
     * @return the event which represent the result of the move
     */
    public GameEvent movePlayer(Player player, String destination) {
        if (player.equals(turnManager.getCurrentPlayer())) {
            // 1 - move the player somewhere
            if (player.canIMove() && !player.alreadyAttacked() && !player.hasAlreadyDrawed()) {
                return gameboardManager.movePlayer(player, destination);
            } else
                return new IllegalActionEvent("You used all your movements this turn");
        }
        return new IllegalPlayerEvent(SystemEventsMessage.WRONGPLAYER.toString());
    }

    /**
     * Handle the attacks of a player
     * 
     * @param player
     *            the attacker
     * @return the event which represent the result of the attack
     */
    public GameEvent attack(Player player) {
        if (player.equals(turnManager.getCurrentPlayer())) {
            // if the current player can attack, then it's either an alien or a
            // player with an item
            // either way, in the end a player it's going to be eliminated if
            // it's on the place
            // so first we must check out if the current player can attack...
            if ((!rulesManager.canIAttack(player) || player.alreadyAttacked()) && !player.hasAlreadyMoved()) {
                // if not, exception
                return new IllegalActionEvent("Unable to attack");
            } else {
                // try to attack. From now on, Game will just catch the
                // exception and notify the observer
                player.setAlreadyAttacked(true);
                return playerManager.attack(player, rulesManager.canAlienIncreaseSpeed());
            }
        } else
            return new IllegalPlayerEvent(SystemEventsMessage.WRONGPLAYER.toString());
    }

    /**
     * Handle the use of an item from a player
     * 
     * @param player
     *            the user of the item
     * @param item
     *            the item to be used
     * @return the event which represent the result of the item usage
     */
    public GameEvent useItem(Player player, Item item) {
        Item temp = null;
        if (player.equals(turnManager.getCurrentPlayer())) {
            // only humans can use items
            if (player instanceof HumanPlayer) {
                /*
                 * items are added to a player only if they are usable so there
                 * is no need to check it here Defense item cannot be used
                 * directly
                 */
                if (player.hasItem(item) && !(item instanceof DefenseItem)) {
                    /*
                     * the item reference in the deck must put in the discarded
                     * pile
                     */
                    deckManager.discardItem(item);
                    /*
                     * the temp object will hold the item taken from the player
                     */
                    temp = playerManager.removeItemfromPlayer(player, item);
                    if (temp != null) {
                        GameEvent effect = temp.dispatchEffect(player, playerManager, gameboardManager);
                        if (effect != null)
                            return effect;
                        /*
                         * user friendly messages to show why he can't use an
                         * item
                         */
                        else
                            return new IllegalActionEvent("That item has no effect");
                    } else
                        return new IllegalActionEvent("You don't have any items");
                } else
                    return new IllegalActionEvent("You can't use that item");
            } else
                return new IllegalActionEvent("Aliens cannot use items");
        } else
            return new IllegalPlayerEvent(SystemEventsMessage.WRONGPLAYER.toString());
    }

    /**
     * Handle the draw of a card
     * 
     * @param player
     *            the drawer
     * @return the event which represent the result of the draw
     */
    public GameEvent drawCard(Player player) {
        if (player.equals(turnManager.getCurrentPlayer())) {
            if (!player.alreadyAttacked() && !player.isSedated()) {

                Card drawed = deckManager.drawSectorCard(player, rulesManager.getRules());
                player.setAlreadyDrawed(true);

                if (drawed == null)
                    return new IllegalActionEvent("Unable to draw card in this sector");

                if (drawed.hasItem() && rulesManager.areItemUsable() && !(player instanceof AlienPlayer)) {
                    /*
                     * need to check if the player has the maximum number of
                     * item and handle the arrangement of the items if necessary
                     */
                    if (player.getItems().size() < Player.getMaxOwnedItems()) {
                        deckManager.givePlayerAnItem(player);
                    } else {
                        List<Item> items = player.getItems();
                        items.add(deckManager.drawItem());
                        return new TooMuchItemsEvent("Too much items get rid of one", items, drawed.getEvent());
                    }
                }
                /*
                 * SPECIAL CASE 1: reached an Escape Pod Sector. WHAT TO DO:
                 * must be extracted an escape pod card, to see if it's red or
                 * green, then notify the view of the EscapePodCard picked up
                 */
                if (drawed instanceof GreenEscapePodCard && player instanceof HumanPlayer) {
                    // SPECIAL CASE 2: Is the Card Green? Then, the player is
                    // automatically a winner
                    // First, I remove the player from the array, because he has
                    // won and doesn't play anymore
                    playerManager.removePlayerFromArray(player);
                    return drawed.getEvent();
                } else {
                    return drawed.getEvent();
                }
            } else
                return new IllegalPlayerEvent("Unable to draw card this turn");
        } else
            return new IllegalPlayerEvent(SystemEventsMessage.WRONGPLAYER.toString());
    }

    /**
     * Handle the arrangement of the items of a player
     * 
     * @param player
     * @param items
     *            to be given
     * @return the event that will notify the result of the arrangement
     */
    public GameEvent setItemsTo(Player player, List<Item> itemsList) {
        if (player.equals(turnManager.getCurrentPlayer())) {
            return playerManager.giveItemsTo(player, itemsList);
        } else
            return new IllegalPlayerEvent(SystemEventsMessage.WRONGPLAYER.toString().toString());
    }

    /**
     * @return the current player of the game
     */
    public Player getCurrentPlayer() {
        return turnManager.getCurrentPlayer();
    }

    /**
     * Handle the end of a turn
     * 
     * @param player
     * @param log
     *            match log to be updated
     * @return the event that will start the first turn of the first player
     */
    public GameEvent endPlayerTurn(Player player, List<String> log) {
        if (player == null)
            return turnManager.beginTurn(playerManager.getPlayersArray());
        if (player.equals(turnManager.getCurrentPlayer())) {
            updateLog(log);
            return turnManager.beginTurn(playerManager.getPlayersArray());
        } else
            return new IllegalPlayerEvent(SystemEventsMessage.WRONGPLAYER.toString());
    }

    private void updateLog(List<String> logs) {
        if (logs != null) {
            try {
                PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(".\\serverGameLogs\\gameLog" + gameID.toString() + ".txt", true)));
                for (String log : logs) {
                    writer.println(log);
                }
                writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                ExceptionLogger.info(e);
            }
        }
    }

    /**
     * Handle the user request to see the game log
     * 
     * @return the event that will show the match log
     */
    public GameEvent getLog() {
        return new LogPrintEvent("Log For this Match", serverLog);
    }

    /**
     * @return the game identifier related to this object
     */
    public BigInteger getGameID() {
        return gameID;
    }

    /**
     * removes a player from the player list
     */
    public void removePlayer(Player player) {
        playerManager.removePlayerFromArray(player);
    }

    /**
     * @return the list of players playing the game
     */
    public List<Player> getPlayers() {
        return playerManager.getPlayersArray();
    }

    /**
     * @return the gameboard map object of this game
     */
    public GameBoard getMap() {
        return gameboardManager.getMap();
    }

    /**
     * @return current turn of the game
     */
    public int getCurrentTurn() {
        return turnManager.getCurrentTurn();
    }

}
