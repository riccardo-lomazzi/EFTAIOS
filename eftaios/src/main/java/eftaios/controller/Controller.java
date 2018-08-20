package eftaios.controller;

import java.util.Observable;
import java.util.Observer;

import eftaios.model.Model;
import eftaios.view.View;

public class Controller implements Observer {

    protected Model model;
    protected View view;
    
    public Controller(){
        
    }
    
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    /**
     * Function that updates with the observed View inputs and calls the 
     * executeCommand inside the userInput 
     * (checks before if the userInput or the view are valid, else it throws an exception)
     * @return void 
     * @param Observable (View), userInput (returned by the View methods)
     * @throws IllegalArgumentException
     */
    @Override
    public void update(Observable o, Object userInput) {
        if(o != view || !(userInput instanceof UserInput)){
            throw new IllegalArgumentException();
        }   
        ((UserInput) userInput).executeCommand(model, view.getCurrentPlayer());
    } 
    
}
