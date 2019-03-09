package eftaios.model.decks.drawables;

import eftaios.model.events.GameEvent;
import eftaios.model.events.SilenceEvent;

public class SilenceCard extends Card {

    /**
     * 
     */
    private static final long serialVersionUID = -648505773805332831L;

    public SilenceCard(boolean hasItem) {
    super(hasItem);
    }

    @Override
    public GameEvent getEvent() {
        return new SilenceEvent("Picked up a Silence Card");
    }

}
