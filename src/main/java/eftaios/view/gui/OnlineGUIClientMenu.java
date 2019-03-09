package eftaios.view.gui;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import eftaios.ExceptionLogger;
import eftaios.model.avatars.Player;
import eftaios.model.events.EndGameEvent;
import eftaios.model.events.EndOfTurnEvent;
import eftaios.model.events.GameEvent;
import eftaios.model.events.GameStartedEvent;
import eftaios.model.events.GreenEscapePodEvent;
import eftaios.model.events.ItemRequestEvent;
import eftaios.model.events.LogPrintEvent;
import eftaios.model.events.NoiseInAnySectorEvent;
import eftaios.model.events.NoiseInYourSectorEvent;
import eftaios.model.events.RedEscapePodEvent;
import eftaios.model.events.SilenceEvent;
import eftaios.model.events.StartPlayerTurnEvent;
import eftaios.model.events.SuccessfulAttackEvent;
import eftaios.model.events.SuccessfulGivenItemListEvent;
import eftaios.model.events.SuccessfulItemAdditionEvent;
import eftaios.model.events.SuccessfulUseOfItemEvent;
import eftaios.model.events.SuccessfulUseOfSpotLightItemEvent;
import eftaios.model.events.TooMuchItemsEvent;
import eftaios.model.match.Game;
import eftaios.network.socket.OnlineGameInfo;
import eftaios.view.ClientMenu;
import eftaios.view.View;

public class OnlineGUIClientMenu extends ClientMenu {

    private IntroFrame introHolder;
    private GameFrame gameHolder;
    private JFrame introFrame;
    private JFrame playerMenuFrame;

    public OnlineGUIClientMenu(InputStream inputStream, OutputStream output) {
        super(inputStream, output);
    }

    public OnlineGUIClientMenu() {
        introHolder = new IntroFrame();
        gameHolder = new GameFrame();
        createLog();
    }

    @Override
    public void ShowIntro() {
        introFrame = introHolder.getIntroFrame();
        introFrame.setVisible(true);
    }

    /**
     * update the intro message
     */
    public void updateShowIntro(List<OnlineGameInfo> matches, Game game) {
        introHolder.updateShowIntro(matches, game);
    }

    @Override
    public void ShowOnlinePlayerMenu(Player connectedPlayer) {
        introFrame.dispose();
        playerMenuIsOn = true;
        playerMenuFrame = gameHolder.getPlayerMenuFrame();
        gameHolder.updatePlayerPosition(connectedPlayer);
        playerMenuFrame.setVisible(true);
        gameHolder.updateTurn(connectedPlayer, 0);
    }

    public void setView(View view) {
        introHolder.setView(view);
        gameHolder.setView(view);
    }

    public void handleEndTurn(Player connectedPlayer, EndOfTurnEvent event) {
        gameHolder.updateItems(connectedPlayer.getItems());
    }

    public void handleGameEnd(Player connectedPlayer, EndGameEvent event) {
        gameHolder.updateItems(connectedPlayer.getItems());
    }

    public void handleSuccessfulUseOfSpotLightItemEvent(Player connectedPlayer, SuccessfulUseOfSpotLightItemEvent event) {
        gameHolder.updateItems(connectedPlayer.getItems());
        String message = "";
        for (Player player : event.getRevealedPlayers()) {
            message = message + player.getPlayerID() + "-->[" + player.getPosition().getCompleteId() + "] ";
        }

        JOptionPane.showMessageDialog(playerMenuFrame, message, "Revealed players", JOptionPane.INFORMATION_MESSAGE);
    }

    public void handleSuccessfulUseOfItemEvent(Player connectedPlayer, SuccessfulUseOfItemEvent event) {
        gameHolder.updateItems(connectedPlayer.getItems());
        gameHolder.updatePlayerPosition(connectedPlayer);
    }

    public void handleGameStartedEvent(Player connectedPlayer, GameStartedEvent event) {
        gameHolder.updateItems(connectedPlayer.getItems());
    }

    public void handleSuccessfulMove(Player connectedPlayer, GameEvent event) {
        gameHolder.updatePlayerPosition(connectedPlayer);
        gameHolder.updateItems(connectedPlayer.getItems());
    }

    public void handleSuccessfulGivenItemListEvent(Player connectedPlayer, SuccessfulGivenItemListEvent event) {
        gameHolder.updateItems(connectedPlayer.getItems());
    }

    public void handleSilenceEvent(Player connectedPlayer, SilenceEvent event) {
        gameHolder.updateCard(event);
        gameHolder.updateItems(connectedPlayer.getItems());
    }

    public void handleRedEscapePodEvent(Player connectedPlayer, RedEscapePodEvent event) {
        gameHolder.updateCard(event);
        gameHolder.updateItems(connectedPlayer.getItems());
    }

    public void handleNoiseInYourSectorEvent(Player connectedPlayer, NoiseInYourSectorEvent event) {
        gameHolder.updateCard(event);
        gameHolder.updateItems(connectedPlayer.getItems());
    }

    public void handleNoiseInAnySectorEvent(Player connectedPlayer, NoiseInAnySectorEvent event) {
        gameHolder.updateCard(event);
        gameHolder.updateItems(connectedPlayer.getItems());
    }

    public void handleGreenEscapePodEvent(Player connectedPlayer, GreenEscapePodEvent event) {
        gameHolder.updateCard(event);
        gameHolder.updateItems(connectedPlayer.getItems());
    }

    public void handleTooMuchItemsEvent(Player connectedPlayer, TooMuchItemsEvent event) {
        gameHolder.showTooMuchItemsMenu(connectedPlayer, event.getPlayerItems());
    }

    public void handleSuccessfulItemAdditionEvent(Player connectedPlayer, SuccessfulItemAdditionEvent event) {
        gameHolder.updateItems(connectedPlayer.getItems());
    }

    public void handleIllegalEvent(Player connectedPlayer, GameEvent event) {
        gameHolder.updateItems(connectedPlayer.getItems());
    }

    public void handleItemRequestEvent(Player connectedPlayer, ItemRequestEvent event) {
        gameHolder.updateItems(connectedPlayer.getItems());
    }

    public void handleStartPlayerTurnEvent(Player connectedPlayer, StartPlayerTurnEvent event, int currentTurn) {
        gameHolder.updateTurn(connectedPlayer, currentTurn);
        gameHolder.updateItems(connectedPlayer.getItems());
    }

    public void handleSuccessfulAttackEvent(Player connectedPlayer, SuccessfulAttackEvent event) {
        gameHolder.updateItems(connectedPlayer.getItems());
    }

    @Override
    public void writeMessage(String message, boolean messageVisibleToEveryone) {
        if (playerMenuIsOn())
            gameHolder.getMessages().setText(message);
        if (messageVisibleToEveryone) {
            try {
                PrintStream temp = new PrintStream(log);
                temp.println(message);
                temp.close();
            } catch (FileNotFoundException e) {
                ExceptionLogger.info(e);
            }
        }
    }

    public void updateLog(LogPrintEvent event) {
        gameHolder.updateLog(event.getLog());
    }

}
