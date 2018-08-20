package eftaios.view.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import eftaios.view.View;

public class FrameEventHandler implements MouseListener {

    protected View view;

    
    /**
     * Set a  related view to this object
     * @param view the related view
     */
    public FrameEventHandler(View view) {
        this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
