package eftaios.model.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import eftaios.ExceptionLogger;

public class GameBoardTest {

    GameBoard gameBoard;

    @Before
    public void initTest() {
        try {
            gameBoard = new GameBoard("Galilei");
        } catch (FileNotFoundException e) {
            ExceptionLogger.info(e);
        }
    }

    @Test
    public void testMoveToValidLocationOfOdd0() {
        Sector From = gameBoard.getSector("M3");
        Sector To = gameBoard.getSector("M2");
        assertEquals(gameBoard.moveTo(From, To), To);
    }

    @Test
    public void testMoveToValidLocationOfOdd1() {
        Sector From = gameBoard.getSector("M3");
        Sector To = gameBoard.getSector("N2");
        assertEquals(gameBoard.moveTo(From, To), To);
    }

    @Test
    public void testMoveToValidLocationOfOdd2() {
        Sector From = gameBoard.getSector("M3");
        Sector To = gameBoard.getSector("N3");
        assertEquals(gameBoard.moveTo(From, To), To);
    }

    @Test
    public void testMoveToValidLocationOfOdd3() {
        Sector From = gameBoard.getSector("M3");
        Sector To = gameBoard.getSector("M4");
        assertEquals(gameBoard.moveTo(From, To), To);
    }

    @Test
    public void testMoveToValidLocationOfOdd4() {
        Sector From = gameBoard.getSector("M3");
        Sector To = gameBoard.getSector("L3");
        assertEquals(gameBoard.moveTo(From, To), To);
    }

    @Test
    public void testMoveToValidLocationOfOdd5() {
        Sector From = gameBoard.getSector("M3");
        Sector To = gameBoard.getSector("L2");
        assertEquals(gameBoard.moveTo(From, To), To);
    }

    @Test
    public void testMoveToValidLocationOfEven0() {
        Sector From = gameBoard.getSector("J10");
        Sector To = gameBoard.getSector("I10");
        assertEquals(gameBoard.moveTo(From, To), To);
    }

    @Test
    public void testMoveToValidLocationOfEven1() {
        Sector From = gameBoard.getSector("J10");
        Sector To = gameBoard.getSector("J9");
        assertEquals(gameBoard.moveTo(From, To), To);
    }

    @Test
    public void testMoveToValidLocationOfEven2() {
        Sector From = gameBoard.getSector("J10");
        Sector To = gameBoard.getSector("K10");
        assertEquals(gameBoard.moveTo(From, To), To);
    }

    @Test
    public void testMoveToValidLocationOfEven3() {
        Sector From = gameBoard.getSector("J10");
        Sector To = gameBoard.getSector("K11");
        assertEquals(gameBoard.moveTo(From, To), To);
    }

    @Test
    public void testMoveToValidLocationOfEven4() {
        Sector From = gameBoard.getSector("J10");
        Sector To = gameBoard.getSector("J11");
        assertEquals(gameBoard.moveTo(From, To), To);
    }

    @Test
    public void testMoveToValidLocationOfEven5() {
        Sector From = gameBoard.getSector("J10");
        Sector To = gameBoard.getSector("I11");
        assertEquals(gameBoard.moveTo(From, To), To);
    }

    @Test
    public void testMoveToInvalidLocation() {
        Sector From = gameBoard.getSector("L6");
        Sector To = gameBoard.getSector("I10");
        assertEquals(gameBoard.moveTo(From, To), From);
    }

    @Test
    public void testMoveToInvalidWallLocation() {
        Sector From = gameBoard.getSector("L6");
        Sector To = gameBoard.getSector("L7");
        assertNotEquals(gameBoard.moveTo(From, To), To);
    }

    @Test
    public void testMoveToInvalidStartingLocation() {
        Sector From = gameBoard.getSector("L5");
        Sector To = gameBoard.getSector("L6");
        assertNotEquals(gameBoard.moveTo(From, To), To);
    }

    @Test
    public void testEquals() {
        Sector From = gameBoard.getSector("M3");
        Sector To = gameBoard.getSector("M3");
        assertEquals(From, To);
        From = null;
        To = null;
        assertEquals(From, To);
    }

    @Test
    public void testNotEquals() {
        Sector From = gameBoard.getSector("M3");
        Sector To = gameBoard.getSector("M2");
        assertNotEquals(From, To);
        From = gameBoard.getSector("A3");
        To = gameBoard.getSector("B3");
        assertNotEquals(From, To);
        From = gameBoard.getSector("M3");
        To = null;
        assertNotEquals(From, To);
    }

    @Test
    public void generatedSector() {
        Sector a = gameBoard.getSector("A1");
        Sector b = gameBoard.getSector("B1");
        Sector c = gameBoard.getSector("C1");
        Sector d = gameBoard.getSector("B2");
        Sector e = gameBoard.getSector("L6");
        Sector f = gameBoard.getSector("L8");
        Sector g = gameBoard.getSector("Z24");
        assertNotEquals(a, null);
        assertNotEquals(b, null);
        assertNotEquals(c, null);
        assertNotEquals(d, null);
        assertNotEquals(e, null);
        assertNotEquals(f, null);
        assertEquals(g, null);
        assertNotEquals(a.getEvent(), null);
        assertNotEquals(b.getEvent(), null);
        assertNotEquals(c.getEvent(), null);
        assertNotEquals(d.getEvent(), null);
        assertNotEquals(e.getEvent(), null);
        assertNotEquals(f.getEvent(), null);
        assertNotEquals(a.getDescription(), null);
        assertNotEquals(b.getDescription(), null);
        assertNotEquals(c.getDescription(), null);
        assertNotEquals(d.getDescription(), null);
        assertNotEquals(e.getDescription(), null);
        assertNotEquals(f.getDescription(), null);
    }
}
