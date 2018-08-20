package eftaios.model.events;

import org.junit.Before;
import org.junit.Test;

public class SystemEventsMessageTest {

    @Before
    public void setUp() throws Exception {
        
    }

    @Test
    public void testToString() {
        SystemEventsMessage.ENTERVALIDSECTOR.toString();
        SystemEventsMessage.WRONGINPUTVALIDSECTOR.toString();
        SystemEventsMessage.WRONGPLAYER.toString();
    }

}
