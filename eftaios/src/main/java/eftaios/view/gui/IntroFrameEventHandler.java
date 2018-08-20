package eftaios.view.gui;

import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import eftaios.controller.CreateGameInput;
import eftaios.controller.SocketConnectToGameInput;
import eftaios.view.View;
import eftaios.view.cli.Message;

public class IntroFrameEventHandler extends FrameEventHandler{

    private IntroFrame frame;

    public IntroFrameEventHandler(IntroFrame frame,View view) {
        super(view);
        this.frame = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (((JComponent) e.getSource()).getName()) {
        case "showintro":
            frame.getMisc().setText(
                    Message.getIntroMessage().substring(0,
                            Message.getIntroMessage().lastIndexOf('\n')));
            break;
        case  "showcredits" :
            frame.getMisc().setText(
                    Message.getCreditsMessage().substring(0,
                            Message.getCreditsMessage().lastIndexOf('\n')));
            break;
        case "start":
            view.ChangeAndNotify(new SocketConnectToGameInput((int)frame.getSelectedGame().getValue()));
            break;
        case "refresh":
            /*
             * connecting to the game with a negative number 
             * will refresh the match list without adding the client to a game
             */
            view.ChangeAndNotify(new SocketConnectToGameInput(-1));
            break;
        case "newgame":
            frame.getNewGameFrame().setVisible(true);
            break;
        case "sendnewgame":
            if(frame.getGameInfo()!=null)
            view.ChangeAndNotify(new CreateGameInput(frame.getGameInfo()));
            break;
        }
    }

    }
