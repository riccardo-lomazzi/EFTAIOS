package eftaios;

import org.junit.Before;
import org.junit.Test;

import eftaios.model.Model;
import eftaios.view.cli.CommandLineInterface;
import eftaios.view.cli.LocalCLIClientMenu;

public class LocalGameStarterTest {

    LocalGameStarter gameStarter; 
    Model model;
    
    @Before
    public void setUp() throws Exception{
        model=new Model();
        gameStarter= new LocalGameStarter();
        gameStarter.setUpGame();
        
    }
    
    @Test
    public void testObservers(){
        gameStarter.addObserverToObservable(model, new CommandLineInterface(new LocalCLIClientMenu(System.in, System.out)));
    }

}
