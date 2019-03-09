package eftaios.model.decks.drawables;

import eftaios.model.events.GameEvent;
import eftaios.model.events.NoiseInYourSectorEvent;

public class NoiseInYourSectorCard extends Card {

    /**
     * 
     */
    private static final long serialVersionUID = 4472702382684893193L;


    public NoiseInYourSectorCard(boolean hasItem) {
    super(hasItem);
    }


    @Override
    public GameEvent getEvent() {
        return new NoiseInYourSectorEvent("Picked up a Noise In Your Sector Card!");
    }

}
