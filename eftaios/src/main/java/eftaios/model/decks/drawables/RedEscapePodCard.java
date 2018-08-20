package eftaios.model.decks.drawables;

import eftaios.model.events.GameEvent;
import eftaios.model.events.RedEscapePodEvent;

public class RedEscapePodCard extends Card {

    /**
     * 
     */
    private static final long serialVersionUID = 6264046685370778813L;


    public RedEscapePodCard(boolean hasItem) {
    super(hasItem);
    }


    @Override
    public GameEvent getEvent() {
        return new RedEscapePodEvent("Picked up a Red Escape Pod Card!");
    }

}
