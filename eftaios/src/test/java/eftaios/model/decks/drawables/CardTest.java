package eftaios.model.decks.drawables;

import org.junit.Assert;
import org.junit.Test;

public class CardTest {

    Card card;

    @Test
    public void testHasItemGreenpod() {
        card = new GreenEscapePodCard(true);
        Assert.assertTrue(card.hasItem);
    }

    @Test
    public void testGetEventGreenpod() {
        card = new GreenEscapePodCard(true);
        Assert.assertNotEquals(null, card.getEvent());
    }

    @Test
    public void testHasItemRedpod() {
        card = new RedEscapePodCard(true);
        Assert.assertTrue(card.hasItem);
    }

    @Test
    public void testGetEventRedpod() {
        card = new RedEscapePodCard(true);
        Assert.assertNotEquals(null, card.getEvent());
    }

    @Test
    public void testHasItemNoiseInAnySectorCard() {
        card = new GreenEscapePodCard(true);
        Assert.assertTrue(card.hasItem);
    }

    @Test
    public void testGetEventNoiseInAnySectorCard() {
        card = new GreenEscapePodCard(true);
        Assert.assertNotEquals(null, card.getEvent());
    }

    @Test
    public void testHasItemNoiseInYourSectorCard() {
        card = new GreenEscapePodCard(true);
        Assert.assertTrue(card.hasItem);
    }

    @Test
    public void testGetEventNoiseInYourSectorCard() {
        card = new GreenEscapePodCard(true);
        Assert.assertNotEquals(null, card.getEvent());
    }

    @Test
    public void testHasItemSilenceCard() {
        card = new GreenEscapePodCard(true);
        Assert.assertTrue(card.hasItem);
    }

    @Test
    public void testGetEventSilenceCard() {
        card = new GreenEscapePodCard(true);
        Assert.assertNotEquals(null, card.getEvent());
    }

}
