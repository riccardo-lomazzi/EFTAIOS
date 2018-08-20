package eftaios.model.events;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import eftaios.ExceptionLogger;
import eftaios.view.EventVisitor;

public class LogPrintEvent extends GameEvent {

    /**
     * 
     */
    private static final long serialVersionUID = -4244375972927651997L;

    List<String> log= new ArrayList<String>();
    
    public LogPrintEvent(String message, File serverLog) {
        super(message);
        Scanner reader;
        if(serverLog!=null) {
            try {
                reader = new Scanner(serverLog);
                while(reader.hasNextLine()) {
                    log.add(reader.nextLine());
                }
                reader.close();
            } catch (FileNotFoundException e) {
                ExceptionLogger.info(e);
            }
        }
    }

    public List<String> getLog() {
        return log;
    }
    
    @Override
    public void acceptVisit(EventVisitor visitor) {
        visitor.visitEvent(this);
    }
}
