package eftaios.model.decks.drawables;

import eftaios.model.events.GameEvent;
import eftaios.model.events.NoiseInAnySectorEvent;

public class NoiseInAnySectorCard extends Card {

    /**
     * 
     */
    private static final long serialVersionUID = 1958586288767871039L;


    public NoiseInAnySectorCard(boolean hasItem) {
    super(hasItem);
    }


    @Override
    public GameEvent getEvent() {
        return new NoiseInAnySectorEvent("Noise In Any Sector Card!");
    }

}
