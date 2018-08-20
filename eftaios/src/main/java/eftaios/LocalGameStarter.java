package eftaios;

import java.util.Observable;
import java.util.Observer;

import eftaios.controller.Controller;
import eftaios.model.Model;
import eftaios.view.View;
import eftaios.view.cli.CommandLineInterface;
import eftaios.view.cli.LocalCLIClientMenu;

public class LocalGameStarter {

    protected Model model;
    protected View view;
    protected Controller controller;

    /**
     * This class is used as a final test for the game
     * it will create an offline multiplayer single console version of the game
     * its aim is to test and debug the interaction between model view and controller
     * as well as providing a basic idea of the game
     */
    public LocalGameStarter() {
    }

    /**
     * create and bound the main component of the MVC pattern
     */
    public void setUpGame() {
        model = new Model();
        view = new CommandLineInterface(new LocalCLIClientMenu(System.in, System.out));
        controller = new Controller(model, view);
        view.addObserver(controller);
        model.addObserver(view);
    }

    private void run() {
        Thread viewThread = new Thread(view);
        viewThread.start();
    }

    protected void addObserverToObservable(Observable observable, Observer observer) {
        observable.addObserver(observer);
    }


    public static void main(String[] args) {
        LocalGameStarter gameStarter = new LocalGameStarter();
        gameStarter.setUpGame();
        gameStarter.run();
    }
}
