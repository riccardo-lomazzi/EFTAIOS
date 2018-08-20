package eftaios.view.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import eftaios.model.board.Sector;

public class Hexagon extends JPanel {

    private static final int SPACING = 10;
    /**
     * 
     */
    private static final long serialVersionUID = -357430883993325007L;
    private static final int SECT = 8;
    private static final int HEXHEIGHT = SECT*4;
    private static final int HEXWIDTH = SECT*5;
    private Character charID;
    private int displayY;
    private int displayX;
    private Color sectorColor;
    private BufferedImage icon;
    
    public Hexagon(Sector sector) {
        super(true);
        /*
         * Sectors are always in the same position inside the map
         * in different maps they change their type so
         * the position assigned here are always correct
         * for each map
         */
        charID=sector.getCharId();
        setDisplayY((sector.getIntId()-1)*(HEXHEIGHT+5));
        /*
         * even char columns have an offset from the top 
         */
        if(Sector.isCharEven(charID)) {
            setDisplayY(getDisplayY()+HEXHEIGHT/2);
        }
        setDisplayX((sector.getCharId()-65)*HEXWIDTH);
        
        sectorColor=getSectorColor(sector.getClass().getSimpleName());
        
        setName(sector.getCompleteId());
        
        setBounds(getDisplayX()+8, getDisplayY()+8,getDisplayWidth(),getDisplayHeight());
    }
    
    private Color getSectorColor(String simpleName) {
        switch(simpleName) {
        case"AlienStartingSector":return Color.RED;
        case"DangerousSector":return Color.GRAY;
        case"EscapePodSector":return Color.GREEN;
        case"HumanStartingSector":return Color.BLUE;
        case"SafeSector":return  Color.LIGHT_GRAY;
        case"WallSector":return Color.BLACK;
        default: return Color.WHITE;
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        this.setOpaque(false);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        super.paintComponent(g2);
        Polygon p = new Polygon();
        
        p.addPoint(SECT*0,SECT*2);
        p.addPoint(SECT*1,SECT*0);
        p.addPoint(SECT*4,SECT*0);
        p.addPoint(SECT*5,SECT*2);
        p.addPoint(SECT*4,SECT*4);
        p.addPoint(SECT*1,SECT*4);
        
        g2.setColor(sectorColor);
        g2.fillPolygon(p);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font ("Arial", Font.BOLD , 11));
        g2.drawString(getName(), 13, 20);
        
        if(icon!=null) 
            g2.drawImage(icon, 4, 2, null);
      }

    /**
     * @return a Point object representing the coordinates of this Hexagon 
     */
    public Point getMapPosition() {
        return new Point(getDisplayX(),getDisplayY());
    }

    /**
     * @return the preferred y for this object
     */
    public int getDisplayY() {
        return displayY;
    }

    private void setDisplayY(int displayY) {
        this.displayY = displayY;
    }


    /**
     * @return the preferred x for this object
     */
    public int getDisplayX() {
        return displayX;
    }

    private void setDisplayX(int displayX) {
        this.displayX = displayX;
    }


    /**
     * @return the height for this object
     */
    public int getDisplayHeight() {
        return HEXHEIGHT+SPACING;
    }

    /**
     * @return the width for this object
     */
    public int getDisplayWidth() {
        return HEXWIDTH+SPACING;
    }

    
    public void setImage(BufferedImage icon) {
        this.icon = icon;
    }
}
