package eftaios.network.rmi.commons;

import java.util.Observable;

import eftaios.controller.Controller;
import eftaios.model.Model;
import eftaios.view.View;

public class ServerController extends Controller {

    public ServerController(ServerModel model, View view) {
        super(model, view);
    }
    
    /**
     * Function that returns the model in the super class Controller 
     * @return Model
     * @param nothing
     */
    public Model getModel(){
        return this.model;
    }
    
    @Override
    public void update(Observable o, Object userInput) {
        super.update(o, userInput);
        
    } 

}
