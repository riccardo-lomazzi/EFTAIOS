package eftaios.model.decks;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import eftaios.model.decks.drawables.Item;

public class ItemDeckTest {

    ItemDeck itemDeck = new ItemDeck();

    @Before
    public void beforeTest(){
        /*
         * the first eleven items are drawn and discarded in the discarded deck
         * the twelfth is drawn by the regular item deck
         * the thirteenth is drawn from the regular item deck after it is filled of the discarded deck items
         */
        for(int i=0;i < 11;i++) 
            itemDeck.discardItem(itemDeck.drawItem());
    }
    
    @Test
    public void testDrawItem1() {
        assertTrue(itemDeck.drawItem() instanceof Item);
    }
    
    @Test
    public void testDrawItem2() {
        assertTrue(itemDeck.drawItem() instanceof Item);
    }
}
