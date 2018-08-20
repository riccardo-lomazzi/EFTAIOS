package eftaios.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eftaios.model.gamerules.AdvancedGameRules;
import eftaios.model.gamerules.Rules;

public class GameInfoTest {

    private GameInfo gameInfo;

    @Before
    public void setUp() throws Exception {
        gameInfo = new GameInfo();
    }

    @Test
    public void testNumberOfPlayers() {
        gameInfo.setNumberOfPlayers(2);
        Assert.assertEquals(2, gameInfo.getNumberOfPlayers());
    }

    @Test
    public void testRules() {
        Rules rules = new AdvancedGameRules();
        gameInfo.setRules(rules);
        Assert.assertEquals(rules, gameInfo.getRules());
    }

    @Test
    public void testMapPath() {
        gameInfo.setMapPath("map");
        Assert.assertEquals("map", gameInfo.getMapPath());
    }

}
