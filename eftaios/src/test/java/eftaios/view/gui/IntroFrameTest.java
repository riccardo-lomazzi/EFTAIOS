package eftaios.view.gui;

import javax.swing.JPanel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IntroFrameTest {

    IntroFrame frame;

    @Before
    public void setUp() throws Exception {
        frame = new IntroFrame();
    }

    @Test
    public void testGetIntroFrame() throws Exception {
        Assert.assertNotNull(frame.getIntroFrame());
    }

    @Test
    public void testGetMisc() throws Exception {
        frame.getIntroFrame();
        Assert.assertNotNull(frame.getMisc());
    }

    @Test
    public void testGetSelectedGame() throws Exception {
        Assert.assertNull(frame.getSelectedGame());
    }

    @Test
    public void testGetNewGameFrame() throws Exception {
        Assert.assertNotNull(frame.getNewGameFrame());
    }

    @Test
    public void testRefreshPanel() throws Exception {
        frame.refreshPanel(new JPanel());
    }

}
