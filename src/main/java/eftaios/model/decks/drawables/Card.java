package eftaios.model.decks.drawables;

import java.io.Serializable;

import eftaios.model.events.GameEvent;

public abstract class Card implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6541359971175920133L;
    protected final boolean hasItem;
    
    /**
     * create a new card
     * @param hasItem specify if this card has an item or not
     */
    protected Card(boolean hasItem) {
        /*
         * each card is given when they are created if they have an item attached to them
         * this item will be given to the player who drew this
         */
        this.hasItem = hasItem;
    }

    /**
     * return if this card has an attached item
     * @return true if this card has an attached item
     */
    public boolean hasItem() {
        return hasItem;
    }

    /**
     * return the game event attached to this card
     * @return the attached event
     */
    public abstract GameEvent getEvent();
}
