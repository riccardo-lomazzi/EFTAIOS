package eftaios.view.cli;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eftaios.view.ClientMenu;

public class LocalCLIClientMenuTest{

    LocalCLIClientMenu localmenu;
    PrintStream oos;
    File makeFile;
    
    @Before
    public void setUp() throws Exception{
        makeFile=new File("Ciao");
        makeFile.createNewFile();
        oos=new PrintStream(makeFile);
        localmenu=new LocalCLIClientMenu(System.in, oos); 
        }
    
    @Test
    public void testSectID(){
            assertFalse(ClientMenu.isValidID("M46"));
            assertFalse(ClientMenu.isValidID(":M46"));
            assertTrue(ClientMenu.isValidID("M8"));
        }
    
    
    @After
    public void testDeleteStream(){
        oos.close();
        makeFile.delete();
    }
 

}
