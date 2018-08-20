package eftaios.model.events;

import java.util.List;

import eftaios.model.decks.drawables.Item;
import eftaios.view.EventVisitor;

public class TooMuchItemsEvent extends GameEvent {
    
    /**
     * 
     */
    private static final long serialVersionUID = 832854309263676801L;
    protected List <Item> items;
    protected GameEvent pickedCardEvent;

    public TooMuchItemsEvent(String message, List <Item> items ,GameEvent pickedCardEvent) {
        super(message);
        this.items=items;
        this.pickedCardEvent=pickedCardEvent;
    }
    
    /**
     * Built with the visitor pattern in mind, it's a function 
     * that accepts a Visit from an EventVistor instance and calls the visitEvent method
     * of the visitor, by passing an instance of itself, the visited object.
     * The visitor contains overloaded methods for every GameEvent 
     * @return void
     * @param the visitor of the event (EventVisitor)
     */
    @Override
    public void acceptVisit(EventVisitor visitor) {
        visitor.visitEvent(this);
    }

    /**
     * Function that returns the ownedItems list of the player
     * @return List of Items
     * @param /
     */
    public List<Item> getPlayerItems() {
        return items;
    }

    /**
     * Function that returns the instance of the GameEvent associated to the card
     * @return GameEvent linked to the extracted card
     * @param /
     */
    public GameEvent getCardEvent() {
        return pickedCardEvent;
    }
    
    

}
