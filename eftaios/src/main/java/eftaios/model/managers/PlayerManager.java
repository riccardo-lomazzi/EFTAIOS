package eftaios.model.managers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import eftaios.model.avatars.AlienPlayer;
import eftaios.model.avatars.Player;
import eftaios.model.avatars.PlayersFactory;
import eftaios.model.board.Sector;
import eftaios.model.decks.drawables.DefenseItem;
import eftaios.model.decks.drawables.Item;
import eftaios.model.events.GameEvent;
import eftaios.model.events.IllegalActionEvent;
import eftaios.model.events.SuccessfulAttackEvent;
import eftaios.model.events.SuccessfulGivenItemListEvent;

public class PlayerManager implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9150279540156279430L;
    private List<Player> players;

    public PlayerManager(int numberOfPlayers) {
        players = new ArrayList<Player>(numberOfPlayers);
        players = PlayersFactory.createPlayers(numberOfPlayers);
    }
    
    /**
     * Function that returns the array of players
     * @return List of players
     * @param nothing
     */
    public List<Player> getPlayersArray(){
        return players;
    }
    
    /**
     * Function that returns the size of the array of players
     * @return integer size of players array
     * @param nothing
     */
    public int getNumberOfPlayers() {
        return players.size();
    }
   
    /**
     * Function that checks if the player can still move by checking his flag
     * @return true if player can still move, false if not
     * @param Player to be checked
     */
    public boolean canPlayerMove(Player player) {
        return player.canIMove();
    }

    /**
     * Function that performs the attack request of a player
     * First it checks for every player that he has the same position of the attacked sector.
     * If so, he's put into an array, but if the player has got a defence item, he will not be added.
     * Instead, a defenceitem from his array will be removed, and this if info is logged into the IllegalActionEvent message
     * @return IllegalActionEvent if the attack isn't successful, SuccessfulAttackEvent if the attack has killed someone,
     * along with the attacked players
     * @param Player that request the attack, flag that checks if the AlienPlayer can increase the speed 
     */

    public GameEvent attack(Player attacker, boolean canAlienIncreaseSpeed) {
        /*
         * the method will fill a list of players that are affected by the
         * attack to be displayed in the view
         */
        List<Player> attackedPlayers = new ArrayList<Player>(players.size() - 1);
        String defenceItemLog = "";

        for (Player attacked : players) {

            if (!attacked.equals(attacker)
                    && isAttackedOnTheSamePosition(attacker, attacked)) {
                // d-fence items are used here
                if (!attacked.hasDefenseItem()) {
                    attackedPlayers.add(attacked);
                } else {
                    attacked.removeItem(new DefenseItem());
                    defenceItemLog = defenceItemLog.concat(attacked
                            .getPlayerID() + ": used a defence item");
                }
            }
        }

        if (attackedPlayers.isEmpty())
            return new SuccessfulAttackEvent(" attack killed no one"
                    + defenceItemLog, attackedPlayers);
        else {
            
            if (attacker instanceof AlienPlayer && canAlienIncreaseSpeed)
                attacker.setMaxNumberOfMoves(3);

            for (Player target : attackedPlayers) {
                for (Player temp : players) {
                    if (target.equals(temp)) {
                        players.remove(temp);
                        break;
                    }
                }
            }
            return new SuccessfulAttackEvent("You landed an attack "
                    + defenceItemLog, attackedPlayers);
        }
    }

    private boolean isAttackedOnTheSamePosition(Player player, Player attacked) {
        // all the players in the same sector as the attacker ,that are not the
        // attacker ,are selected
        return player.getPosition().equals(attacked.getPosition());
    }

    /**
     * Function that checks if the player has a defence item in his ownedItems array
     * @return true if player has a defenceitem, false if not
     * @param Player to be checked
     */
    public boolean hasPlayerInSectorDefenceItem(Player player) {
        return player.hasDefenseItem();
    }

    /**
     * Function that removes a player from the array of players
     * @return true if player is found and removed, false if not
     * @param Player to be removed
     */
    public boolean removePlayerFromArray(Player player){
        return players.remove(player);
    }

    /**
     * Function that removes a player's item from the ownedItems array of the player
     * @return Item removed if the player's item is found and removed, false if not
     * @param Player's item to be removed
     */
    public Item removeItemfromPlayer(Player player, Item item) {
        return player.removeItem(item);
    }

    /**
     * Function that is invoked when the player has too many items and one must be
     * removed from his deck. The new itemsList is parsed upon the player's one. 
     * If it's not empty, the items are rearranged and an event is returned.
     * @return SuccessfulGivenItemListEvent if player's item list is found and rearranged
     * IllegalActionEvent if not
     * @param Player to which look his itemsList, new itemsList to be parsed.
     */
    public GameEvent giveItemsTo(Player player, List<Item> itemsList) {
        if (!itemsList.isEmpty()) {
            player.giveItems(itemsList);
            return new SuccessfulGivenItemListEvent(
                    "Correctly rearranged items ");
        } else
            return new IllegalActionEvent("Unable to deliver an empty list");
    }

    /**
     * Function that is invoked when a player uses a Spotlight Item . It searches for
     * all the players in the sector selected by the player and returns all the players in that position or the six adjacent.
     * @return temp (list of  of players, adjacent or in position)
     * @param sector to check
     */
    public List<Player> getPlayersInPositionAndAdjacent(Sector sector) {
        List<Player> temp = new ArrayList<Player>(players.size());
        for (Player player : players) {
            if (GameBoardManager.isInPositionOrAdjacent(player.getPosition(), sector))
                temp.add(player);
        }
        return temp;
    }

}
