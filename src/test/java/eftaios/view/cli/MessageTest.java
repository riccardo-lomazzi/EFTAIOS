package eftaios.view.cli;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class MessageTest {

    @Before
    public void setUp() throws Exception{
       
    }
    
    @Test
    public void testMessage() throws Exception{
        assertNotNull(Message.getCreditsMessage());
        assertNotNull(Message.getInGameMenuHelp());
        assertNotNull(Message.getIntroMessage());
        assertNotNull(Message.getWelcomeMessage());
    }

}
