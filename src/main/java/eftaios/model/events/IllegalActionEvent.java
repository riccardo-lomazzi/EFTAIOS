package eftaios.model.events;


import eftaios.view.EventVisitor;

public class IllegalActionEvent extends GameEvent{

    /**
     * 
     */
    private static final long serialVersionUID = -2177959190407473220L;

    public IllegalActionEvent() {
    }
    
    public IllegalActionEvent(String message) {
        super(message);
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
