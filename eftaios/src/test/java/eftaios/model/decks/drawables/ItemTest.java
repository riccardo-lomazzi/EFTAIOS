package eftaios.model.decks.drawables;

import org.junit.Assert;
import org.junit.Test;

public class ItemTest {

    Item item;

    @Test
    public void testGetTypeAdrenalineItem() {
        item = new AdrenalineItem();
        Assert.assertNotNull(item.getType());
    }

    @Test
    public void testGetTypeAttacklineItem() {
        item = new AttackItem();
        Assert.assertNotNull(item.getType());
    }

    @Test
    public void testGetTypeDefenseItem() {
        item = new DefenseItem();
        Assert.assertNotNull(item.getType());
    }

    @Test
    public void testGetTypeSedativeItem() {
        item = new SedativeItem();
        Assert.assertNotNull(item.getType());
    }

    @Test
    public void testGetTypeSpotLightItem() {
        item = new SpotLightItem();
        Assert.assertNotNull(item.getType());
    }

    @Test
    public void testGetTypeTeleportItem() {
        item = new TeleportItem();
        Assert.assertNotNull(item.getType());
    }

}
