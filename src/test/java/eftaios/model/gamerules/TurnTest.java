package eftaios.model.gamerules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TurnTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testIsGameLastTurn() {
        Assert.assertTrue(Turn.isGameFirstTurn(0));
    }

    @Test
    public void testIsGameFirstTurn() {
        Assert.assertTrue(Turn.isGameLastTurn(39));
    }

}
