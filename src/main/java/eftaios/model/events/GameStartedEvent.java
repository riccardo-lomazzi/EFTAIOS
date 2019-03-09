package eftaios.model.events;

public class GameStartedEvent extends GameEvent {

    /**
     * 
     */
    private static final long serialVersionUID = -2453116266727190771L;

    public GameStartedEvent() {
    }

    public GameStartedEvent(String message) {
        super(message);
    }

}
