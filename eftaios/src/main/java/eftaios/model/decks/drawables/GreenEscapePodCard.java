package eftaios.model.decks.drawables;

import eftaios.model.events.GameEvent;
import eftaios.model.events.GreenEscapePodEvent;

public class GreenEscapePodCard extends Card {

    /**
     * 
     */
    private static final long serialVersionUID = -7891159344144196842L;

    public GreenEscapePodCard(boolean hasItem) {
    super(hasItem);
    }

    @Override
    public GameEvent getEvent() {
        return new GreenEscapePodEvent("Picked up a Green Escape Pod Card");
    }

}
