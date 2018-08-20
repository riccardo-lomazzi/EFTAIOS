package eftaios.model.gamerules;

import org.junit.Assert;
import org.junit.Test;

public class RulesTest {

    Rules rules;

    @Test
    public void testGetMaxHumanMoveAdvanced() {
        rules = new AdvancedGameRules();
        Assert.assertEquals(1, rules.getMaxHumanMove());
    }

    @Test
    public void testGetMaxAlienMoveAdvanced() {
        rules = new AdvancedGameRules();
        Assert.assertEquals(2, rules.getMaxAlienMove());
    }

    @Test
    public void testAreItemsUsableAdvanced() {
        rules = new AdvancedGameRules();
        Assert.assertTrue(rules.areItemsUsable());
    }

    @Test
    public void testCanAlienIncreaseSpeedAdvanced() {
        rules = new AdvancedGameRules();
        Assert.assertTrue(rules.canAlienIncreaseSpeed());
    }

    @Test
    public void testGetMaxHumanMoveBasic() {
        rules = new BasicGameRules();
        Assert.assertEquals(1, rules.getMaxHumanMove());
    }

    @Test
    public void testGetMaxAlienMoveBasic() {
        rules = new BasicGameRules();
        Assert.assertEquals(2, rules.getMaxAlienMove());
    }

    @Test
    public void testAreItemsUsableBasic() {
        rules = new BasicGameRules();
        Assert.assertFalse(rules.areItemsUsable());
    }

    @Test
    public void testCanAlienIncreaseSpeedBasic() {
        rules = new BasicGameRules();
        Assert.assertFalse(rules.canAlienIncreaseSpeed());
    }

}
