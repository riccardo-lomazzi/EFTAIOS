package eftaios.view.gui;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import eftaios.view.View;


public class FrameTest extends Frame {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Test
    public void testSetView() throws Exception {
        View temp = new GraphicUserInterface(new OnlineGUIClientMenu());
        setView(temp);
        Assert.assertNotNull(view);
    }

}
