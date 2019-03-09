package eftaios.view;

import eftaios.model.decks.drawables.Item;

public class UseItemException extends Exception {

    /**
     * Exception thrown when the user has to use or remove an item , 
     * after the prompt of too many items has opened. The picked up Item is stored here.
     * */
    private static final long serialVersionUID = 1366224682551603953L;
    private transient Item item;

    /**
     * this exception is used to handle 
     * the event related to the player 
     * having too much items
     * @param item the extra item
     */
    public UseItemException(Item item) {
        this.item = item;
    }
    
    /**
     * Function that gets the picked up item
     * @param nothing
     * @return item picked up
     */
    public Item getItem() {
        return item;
    }
    
    
    @Override
    public String getMessage(){
        return "You've got too many items";
    }

}
