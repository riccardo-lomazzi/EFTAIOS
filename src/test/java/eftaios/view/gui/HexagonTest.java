package eftaios.view.gui;

import org.junit.Before;
import org.junit.Test;

import eftaios.model.board.AlienStartingSector;
import eftaios.model.board.DangerousSector;
import eftaios.model.board.EscapePodSector;
import eftaios.model.board.HumanStartingSector;
import eftaios.model.board.SafeSector;
import eftaios.model.board.Sector;
import eftaios.model.board.WallSector;

public class HexagonTest {

    private Hexagon hex;
    private Sector sector;

    @Before
    public void setUp() throws Exception {
        sector = new DangerousSector('A', 9);
        hex = new Hexagon(sector );
        sector = new SafeSector('B', 1);
        hex = new Hexagon(sector );
        sector = new WallSector('D', 12);
        hex = new Hexagon(sector );
        sector = new HumanStartingSector('F', 3);
        hex = new Hexagon(sector );
        sector = new AlienStartingSector('C', 4);
        hex = new Hexagon(sector );
        sector = new EscapePodSector('E', 11);
        hex = new Hexagon(sector );
    }

    @Test
    public void testPaint() {
        sector = new DangerousSector('A', 9);
        hex = new Hexagon(sector );
        hex.repaint();
    }

}
