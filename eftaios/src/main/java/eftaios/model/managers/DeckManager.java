package eftaios.model.managers;

import java.io.Serializable;

import eftaios.model.avatars.HumanPlayer;
import eftaios.model.avatars.Player;
import eftaios.model.board.DangerousSector;
import eftaios.model.board.EscapePodSector;
import eftaios.model.board.Sector;
import eftaios.model.decks.DangerousSectorsDeck;
import eftaios.model.decks.EscapePodDeck;
import eftaios.model.decks.ItemDeck;
import eftaios.model.decks.drawables.Card;
import eftaios.model.decks.drawables.Item;
import eftaios.model.gamerules.AdvancedGameRules;
import eftaios.model.gamerules.BasicGameRules;
import eftaios.model.gamerules.Rules;

public class DeckManager implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8452913317748254611L;
    private DangerousSectorsDeck dangerousSectorsDeck;
    private EscapePodDeck escapePodDeck;
    private ItemDeck itemDeck;
    
    
    public DeckManager(){
        
    }

     /**
      * Function that create Basic or Advanced decks 
      * according to the Rules passed (BasicGameRules or AdvancedGameRules)
      * @return void
      * @param rules to follow to create decks
      */
     public void createDecks(Rules rules) {
         if(rules instanceof BasicGameRules)
             createBasicDecks();
         else
             createAdvancedDecks();
     }
     
     /**
      * Function that creates a Basic set of decks, composed only with the Dangerous Sector card
      * @return void
      * @param nothing
      */ 
    private void createBasicDecks(){
        dangerousSectorsDeck = new DangerousSectorsDeck();
    }
    
    /**
     * Function that creates an Advanced set of decks, composed with Dangerous Sector cards,
     * EscapePod and Items
     * @return void
     * @param nothing
     */ 
    private void createAdvancedDecks() {
        dangerousSectorsDeck = new DangerousSectorsDeck();
        escapePodDeck = new EscapePodDeck();
        itemDeck = new ItemDeck();
    }


  

    /**
     * Function that adds an Item to the ownedItems array of the player passed as argument,
     * by fetching that item from the ItemDeck
     * @return void
     * @param player in which to add an element
     */ 
    public void givePlayerAnItem(Player player) {
        player.addItem(itemDeck.drawItem());
    }

    /**
     * Function removes an item from the ItemDeck
     * @return void
     * @param item to be removed
     */ 
    public void discardItem(Item item) {
        itemDeck.discardItem(item);
    }

    /**
     * Function that activates when the player steps on a Sector. If it's a dangerous sector, return a specific card,
     * or, only if the player is Human and the picked sector is an EscapePodSector (which are available only on AdvancedGameRules)
     * return an EscapePodCard. Return null otherwise
     * @return void
     * @param player in which to add an element, rules to be checked when the escapepod is reached
     */ 
    public Card drawSectorCard(Player player, Rules rules) {
        Sector sector = player.getPosition();
        if (sector instanceof DangerousSector) {
            return drawDangerousCard();
        } else {
            if (sector instanceof EscapePodSector && rules instanceof AdvancedGameRules && player instanceof HumanPlayer)
                return drawEscapePodCard();
            else
                return null;
        }
    }
    
    /**
     * Function that extracts an escape Pod Card form the escapePodDeck array
     * @return Card extracted (EscapePodCard)
     * @param nothing
     */ 
    private Card drawEscapePodCard() {
        return escapePodDeck.drawCard();
    }

    /** 
     * Function that extracts a DangerousCard form the DangerousSectorsDeck array
     * @return Card extracted (DangerousCard)
     * @param nothing
     */ 
    private Card drawDangerousCard() {
        return dangerousSectorsDeck.drawCard();
    }

    /**
     * Function that extracts an item from the itemDeck
     * @return Item extracted (drawItem)
     * @param nothing
     */ 
    public Item drawItem() {
        return itemDeck.drawItem();
    }
}
