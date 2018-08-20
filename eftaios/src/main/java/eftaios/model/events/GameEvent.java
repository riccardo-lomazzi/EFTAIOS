package eftaios.model.events;



import java.io.Serializable;

import eftaios.view.EventVisitor;


public abstract class GameEvent implements VisitableByView, Serializable{
   
    /**
     * 
     */
    private static final long serialVersionUID = -5343919523167292520L;
    private String message;

    
    public GameEvent(){
        
    }
    
    
    public GameEvent(String message){
        this.message=message;
    }
    
    /**
     * Function that returns the message of the event
     * @return String of the message
     * @param nothing
     */
    public String getMessage(){
        return message;
    }
    

    
    /**
     * Built with the visitor pattern in mind, it's a function 
     * that accepts a Visit from an EventVistor instance
     * (actually a stub to be overridden by every child of GameEvent)
     * @return void
     * @param the visitor of the event (EventVisitor)
     */
    @Override
    public void acceptVisit(EventVisitor visitor){
        
    }
}
