package eftaios.view.gui;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import eftaios.ExceptionLogger;
import eftaios.view.View;

public abstract class Frame {

    protected View view;

    protected Frame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            ExceptionLogger.info(e);
        }
    }

    /**
     * Set a related view to this object
     * 
     * @param view
     *            the related view
     */
    public void setView(View view) {
        this.view = view;
    }

    protected void refreshPanel(JPanel panel) {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }
}
