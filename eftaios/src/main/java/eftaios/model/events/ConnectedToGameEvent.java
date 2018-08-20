package eftaios.model.events;

import eftaios.view.EventVisitor;

public class ConnectedToGameEvent extends GameEvent {

    /**
     * 
     */
    private static final long serialVersionUID = 8922345478600231849L;
    
    public ConnectedToGameEvent(String message) {
        super(message);
    }

    @Override
    public void acceptVisit(EventVisitor visitor) {
        visitor.visitEvent(this);
    }
}
