package eftaios.model.events;

import java.util.List;

import eftaios.model.decks.drawables.Item;
import eftaios.view.EventVisitor;

public class ItemRequestEvent extends GameEvent {
    /**
     * 
     */
    private static final long serialVersionUID = -6377744549941000757L;
    private List<Item> ownedItems;
    
    public ItemRequestEvent(String message, List <Item> ownedItems){
        super(message);
        this.ownedItems=ownedItems;
    }
    
    
    /**
     * Function that returns the ownedItems of the player 
     * @return List of Items
     * @param nothing
     */
    public List<Item> getOwnedItemsInHand(){
        return ownedItems;
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
}
