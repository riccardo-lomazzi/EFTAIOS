package eftaios.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;

import eftaios.ExceptionLogger;
import eftaios.model.avatars.AlienPlayer;
import eftaios.model.avatars.HumanPlayer;
import eftaios.model.avatars.Player;
import eftaios.model.board.GameBoard;
import eftaios.model.board.Sector;
import eftaios.model.decks.drawables.Item;
import eftaios.model.events.GreenEscapePodEvent;
import eftaios.model.events.NoiseInAnySectorEvent;
import eftaios.model.events.NoiseInYourSectorEvent;
import eftaios.model.events.RedEscapePodEvent;
import eftaios.model.events.SilenceEvent;

public class GameFrame extends Frame {

    protected JFrame playerMenuFrame;
    protected JPanel gameViewHolder;
    protected JPanel mapPanel;
    protected JPanel concreteMapPanel;
    protected JPanel eventPanel;
    protected JPanel actionPanel;
    protected JPanel cardPanel;
    protected JPanel logPanel;
    protected JPanel logView;
    protected GameFrameEventHandler eventHandler;
    protected TooMuchItemsHandler tooMuchItemsHandler;
    protected JButton attackButton;
    protected JButton endButton;
    protected JButton logButton;
    protected Hexagon playerHex;
    protected JLabel messages;
    protected Image icon;
    protected JPanel cardPanelRight;
    protected JPanel cardPanelLeft;
    protected JPanel mainList;
    protected JPanel infoPanel;
    protected JPanel messagePanel;
    protected JLabel currentPlayer;
    protected JLabel currentTurn;
    protected JPanel itemPanel;
    protected DrawablePanel item1;
    protected DrawablePanel item2;
    protected DrawablePanel item3;
    protected JFrame itemsFrame;
    protected JRadioButton useBox;
    protected JRadioButton discardBox;
    protected DrawablePanel extraItemPanel;
    protected ButtonGroup group;

    public GameFrame() {
        super();
    }

    /**
     * Create a frame that will host the player menu
     */
    public JFrame getPlayerMenuFrame() {
        playerMenuFrame = new JFrame();
        playerMenuFrame.setTitle("EFTIOS");
        playerMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        playerMenuFrame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        eventHandler = new GameFrameEventHandler(this, view);
        playerMenuFrame.add(getgameViewHolder());
        return playerMenuFrame;
    }

    protected JPanel getgameViewHolder() {
        /*
         * Panel holding the game view
         */
        gameViewHolder = new JPanel(new BorderLayout(), true);
        gameViewHolder.add(getInfoPanel(), BorderLayout.SOUTH);
        gameViewHolder.add(getEventPanel(), BorderLayout.EAST);
        gameViewHolder.add(getDrawedMap(), BorderLayout.CENTER);
        gameViewHolder.setVisible(true);
        return gameViewHolder;
    }

    protected JPanel getInfoPanel() {
        /*
         * panel holding player info
         */
        infoPanel = new JPanel(new BorderLayout(), true);
        infoPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
        infoPanel.add(getPlayerInfoPanel(), BorderLayout.WEST);
        infoPanel.add(getMessagePanel(), BorderLayout.EAST);
        infoPanel.add(getActionPanel(), BorderLayout.CENTER);
        messagePanel.add(getItemPanel(), BorderLayout.EAST);
        return infoPanel;
    }

    protected JPanel getItemPanel() {
        /*
         * panel holding items
         */
        itemPanel = new JPanel(new FlowLayout());
        item1 = new DrawablePanel();
        item1.setName("1");
        item1.addMouseListener(eventHandler);
        item1.setPreferredSize(new Dimension(30, 30));
        itemPanel.add(item1);
        item2 = new DrawablePanel();
        item2.setName("2");
        item2.addMouseListener(eventHandler);
        item2.setPreferredSize(new Dimension(30, 30));
        itemPanel.add(item2);
        item3 = new DrawablePanel();
        item3.addMouseListener(eventHandler);
        item3.setPreferredSize(new Dimension(30, 30));
        item3.setName("3");
        itemPanel.add(item3);
        return itemPanel;
    }

    protected Component getMessagePanel() {
        /*
         * panel holding messages to player
         */
        messagePanel = new JPanel(new BorderLayout(), true);
        messages = new JLabel();
        messagePanel.add(messages, BorderLayout.CENTER);
        return messagePanel;
    }

    protected Component getPlayerInfoPanel() {
        /*
         * panel holding personal info and turn count
         */
        infoPanel = new JPanel(new GridLayout(2, 1), true);
        currentPlayer = new JLabel();
        infoPanel.add(currentPlayer, 0, 0);
        currentTurn = new JLabel();
        infoPanel.add(currentTurn, 1, 0);
        return infoPanel;
    }

    protected JPanel getEventPanel() {
        /*
         * panel holding card and log
         */
        eventPanel = new JPanel(new GridLayout(2, 1), true);
        eventPanel.setBackground(Color.WHITE);
        eventPanel.setBorder(BorderFactory.createMatteBorder(5, 0, 0, 5, Color.BLACK));
        eventPanel.setPreferredSize(new Dimension(400, 1000));
        cardPanel = new JPanel(new BorderLayout(), true);
        icon = getIcon(".\\res\\cardback.png");
        cardPanelLeft = new DrawablePanel(icon);
        cardPanelRight = new JPanel(true);
        logPanel = new JPanel(true);
        logPanel.setSize(new Dimension(400, 200));
        logPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.BLACK));
        logPanel.add(getLogView());
        cardPanelLeft.setName("draw");
        cardPanelLeft.addMouseListener(eventHandler);
        cardPanelLeft.setPreferredSize(new Dimension(198, 300));
        cardPanelLeft.setToolTipText("Click Here To Draw A Card");
        cardPanel.add(cardPanelLeft, BorderLayout.WEST);
        cardPanelRight.setPreferredSize(new Dimension(198, 300));
        cardPanel.add(cardPanelRight, BorderLayout.EAST);
        cardPanel.add(new JPanel(), BorderLayout.CENTER);
        eventPanel.add(cardPanel, 0, 0);
        eventPanel.add(logPanel, 1, 0);
        return eventPanel;
    }

    protected JPanel getLogView() {
        logView = new JPanel();
        logView.setLayout(new BorderLayout());
        mainList = new JPanel(new GridBagLayout());
        setMainList();
        logView.setPreferredSize(new Dimension(400, 400));
        logView.add(new JScrollPane(mainList));
        return logView;
    }

    protected JPanel getActionPanel() {
        actionPanel = new JPanel(new FlowLayout(), true);
        attackButton = new JButton("Attack");
        attackButton.setName("attack");
        attackButton.addMouseListener(eventHandler);
        actionPanel.add(attackButton);
        endButton = new JButton("end");
        endButton.setName("end");
        endButton.addMouseListener(eventHandler);
        actionPanel.add(endButton);
        logButton = new JButton("log");
        logButton.setName("log");
        logButton.addMouseListener(eventHandler);
        actionPanel.add(logButton);
        return actionPanel;
    }

    protected JPanel getDrawedMap() {
        mapPanel = new JPanel(new GridLayout(1, 1), true);
        concreteMapPanel = new JPanel(true);
        concreteMapPanel.setLayout(null);
        concreteMapPanel.setBackground(Color.DARK_GRAY);
        concreteMapPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 0, 5, Color.BLACK));
        if (view != null)
            drawMapOn(view.getModel().getGame().getMap(), concreteMapPanel);
        mapPanel.add(concreteMapPanel, 0, 0);
        return mapPanel;
    }

    /**
     * @return the messages label for this game frame
     */
    public JLabel getMessages() {
        return messages;
    }

    protected void drawMapOn(GameBoard map, JPanel mapPanel) {
        Map<String, Sector> temp = map.getBoard();
        Iterator<Entry<String, Sector>> it = temp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Sector> pair = (Map.Entry<String, Sector>) it.next();
            drawPair(mapPanel, pair);
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    protected void drawPair(JPanel mapPanel, Entry<String, Sector> pair) {
        Hexagon temp = new Hexagon(pair.getValue());
        temp.addMouseListener(eventHandler);
        mapPanel.add(temp);
    }

    /**
     * update the frame showing the player position
     * @param connectedPlayer the player from which the information will be taken
     */
    public void updatePlayerPosition(Player connectedPlayer) {
        if (playerHex != null) {
            concreteMapPanel.remove(playerHex);
            refreshPanel(playerHex);
        }
        playerHex = new Hexagon(connectedPlayer.getPosition());
        for (Component component : concreteMapPanel.getComponents()) {

            if (component.getName().equals(connectedPlayer.getPosition().getCompleteId())) {
                try {
                    if (connectedPlayer instanceof AlienPlayer)
                        playerHex.setImage(ImageIO.read(new File(".\\res\\alien.png")));
                    if (connectedPlayer instanceof HumanPlayer)
                        playerHex.setImage(ImageIO.read(new File(".\\res\\human.png")));
                } catch (IOException e) {
                    ExceptionLogger.info(e);
                }
                concreteMapPanel.add(playerHex);
                concreteMapPanel.setComponentZOrder(playerHex, 0);
                concreteMapPanel.repaint();
                /*
                 * there is only one sector to be changed once it has found it
                 * there is no more need to scan the list anymore
                 */
                break;
            }
        }
    }

    protected Image getIcon(String string) {
        try {
            return ImageIO.read(new File(string));
        } catch (IOException e) {
            ExceptionLogger.info(e);
            return null;
        }
    }

    /**
     * update the Card box with the relative card event
     */
    public void updateCard(NoiseInYourSectorEvent event) {
        refreshPanel(cardPanelRight);
        icon = getIcon(".\\res\\noiseyoursector.png");
        drawCard();
    }

    protected void drawCard() {
        Graphics g = cardPanelRight.getGraphics();
        if(g!=null)
        g.drawImage(icon, 0, 0, null);
    }

    /**
     * update the Card box with the relative card event
     */
    public void updateCard(RedEscapePodEvent event) {
        refreshPanel(cardPanelRight);
        icon = getIcon(".\\res\\redpod.png");
        drawCard();
    }

    /**
     * update the Card box with the relative card event
     */
    public void updateCard(NoiseInAnySectorEvent event) {
        refreshPanel(cardPanelRight);
        icon = getIcon(".\\res\\noiseanysector.png");
        drawCard();
    }

    /**
     * update the Card box with the relative card event
     */
    public void updateCard(GreenEscapePodEvent event) {
        refreshPanel(cardPanelRight);
        icon = getIcon(".\\res\\greenpod.png");
        drawCard();
    }

    /**
     * update the Card box with the relative card event
     */
    public void updateCard(SilenceEvent event) {
        refreshPanel(cardPanelRight);
        icon = getIcon(".\\res\\silence.png");
        drawCard();
    }

    /**
     * update the logs box with the given logs
     */
    public void updateLog(List<String> logs) {
        mainList.removeAll();

        setMainList();

        for (String log : logs) {
            addLogLabel(log);
        }
    }

    protected void setMainList() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainList.add(new JPanel(), gbc);
    }

    protected void addLogLabel(String log) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(log));
        panel.setOpaque(true);
        panel.setBackground(getColor(log));
        panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainList.add(panel, gbc, 0);
        logView.validate();
        logView.repaint();
    }

    protected Color getColor(String log) {
        switch (Integer.parseInt("" + log.charAt(6))) {
            case 0:
                return Color.BLUE;
            case 1:
                return Color.RED;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.MAGENTA;
            case 4:
                return Color.ORANGE;
            case 5:
                return Color.PINK;
            case 6:
                return Color.YELLOW;
            case 7:
                return Color.GRAY;
            default:
                return Color.CYAN;
        }
    }

    class DrawablePanel extends JPanel {

        /**
         * 
         */
        protected static final long serialVersionUID = -682542293137367103L;
        protected Image icon;

        public DrawablePanel(Image icon) {
            this.icon = icon;
        }

        public DrawablePanel() {
        }

        /**
         * set the icon that this panel will paint on itself
         * @param the icon
         */
        public void setIcon(Image icon) {
            this.icon = icon;
        }

        @Override
        public void paintComponent(Graphics g) {
            this.setOpaque(false);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (icon != null)
                g2.drawImage(icon, 0, 0, null);
        }
    }

    /**
     * update the information box with the given player and turn
     */
    public void updateTurn(Player connectedPlayer, int currentTurn) {
        currentPlayer.setText("You are Player:" + connectedPlayer.getPlayerID());
        this.currentTurn.setText("Turn number:" + currentTurn);
    }

    /**
     * update the items panel
     */
    public void updateItems(List<Item> items) {
        cleanDrawablePanel(item1);
        cleanDrawablePanel(item2);
        cleanDrawablePanel(item3);
        if (items != null) {
            if (items.size() >= 1 && items.get(0) != null)
                item1.setIcon(getIcon(".\\res\\" + items.get(0).getType().toLowerCase() + ".png"));
            if (items.size() >= 2 && items.get(1) != null)
                item2.setIcon(getIcon(".\\res\\" + items.get(1).getType().toLowerCase() + ".png"));
            if (items.size() >= 3 && items.get(2) != null)
                item3.setIcon(getIcon(".\\res\\" + items.get(2).getType().toLowerCase() + ".png"));
            item1.validate();
            item1.repaint();
            item2.validate();
            item2.repaint();
            item3.validate();
            item3.repaint();
        }
    }
    
    private void cleanDrawablePanel(DrawablePanel panel) {
        panel.setIcon(null);
        panel.repaint();
    }
    
    /**
     * show the too much items menu
     * @param connectedPlayer the player with too much items
     * @param playerItems the items
     */
    public void showTooMuchItemsMenu(Player connectedPlayer, List<Item> playerItems) {
        itemsFrame = new JFrame();
        itemsFrame.setSize(200, 200);
        tooMuchItemsHandler = new TooMuchItemsHandler(view, playerItems, useBox, discardBox, this);
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Choose use or discard and than click on the item"));
        itemsFrame.add(panel);
        panel.add(itemPanel);
        extraItemPanel = new DrawablePanel();
        extraItemPanel = new DrawablePanel();
        extraItemPanel.setName("4");
        extraItemPanel.addMouseListener(tooMuchItemsHandler);
        extraItemPanel.setPreferredSize(new Dimension(30, 30));
        ((DrawablePanel) extraItemPanel).setIcon(getIcon(".\\res\\" + playerItems.get(3).getType().toLowerCase() + ".png"));
        group = new ButtonGroup();
        useBox = new JRadioButton("Use");
        useBox.setSelected(true);
        discardBox = new JRadioButton("Discard");
        group.add(useBox);
        group.add(discardBox);
        panel.add(extraItemPanel);
        panel.add(useBox);
        panel.add(discardBox);
        itemsFrame.setVisible(true);
    }


    /**
     * reset the items panel
     * and closes the too much item menu
     */
    public void resetItemsPanels() {
        itemsFrame.dispose();
        infoPanel.add(itemPanel);
    }

}
