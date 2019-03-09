package eftaios.view.gui;

import java.util.List;

import javax.swing.JOptionPane;

import eftaios.model.avatars.Player;
import eftaios.model.events.ConnectedToGameEvent;
import eftaios.model.events.EndGameEvent;
import eftaios.model.events.EndOfTurnEvent;
import eftaios.model.events.GameStartedEvent;
import eftaios.model.events.GreenEscapePodEvent;
import eftaios.model.events.IOErrorEvent;
import eftaios.model.events.IllegalActionEvent;
import eftaios.model.events.IllegalPlayerEvent;
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
import eftaios.model.events.SuccessfulMoveOnDangerousSectorEvent;
import eftaios.model.events.SuccessfulMoveOnEscapePodSectorEvent;
import eftaios.model.events.SuccessfulMoveOnSafeSectorEvent;
import eftaios.model.events.SuccessfulMoveOnStartingSectorEvent;
import eftaios.model.events.SuccessfulMoveOnWallSectorEvent;
import eftaios.model.events.SuccessfulUseOfItemEvent;
import eftaios.model.events.SuccessfulUseOfSpotLightItemEvent;
import eftaios.model.events.TooMuchItemsEvent;
import eftaios.model.events.UnableToAttackEvent;
import eftaios.view.ClientMenu;
import eftaios.view.View;

public class GraphicUserInterface extends View {

    protected OnlineGUIClientMenu onlineMenu;

    public GraphicUserInterface(OnlineGUIClientMenu onlineGUIClientMenu) {
        onlineMenu = onlineGUIClientMenu;
        menu = onlineGUIClientMenu;
    }

    // Overrides of the methods visitEvent

    @Override
    public void visitEvent(EndOfTurnEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleEndTurn(connectedPlayer, event);
    }

    @Override
    public void visitEvent(EndGameEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleGameEnd(connectedPlayer, event);
    }

    @Override
    public void visitEvent(IllegalActionEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleIllegalEvent(connectedPlayer, event);
    }

    @Override
    public void visitEvent(IllegalPlayerEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleIllegalEvent(connectedPlayer, event);
    }

    @Override
    public void visitEvent(IOErrorEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleIllegalEvent(connectedPlayer, event);
    }

    @Override
    public void visitEvent(ItemRequestEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleItemRequestEvent(connectedPlayer, event);
    }

    @Override
    public void visitEvent(StartPlayerTurnEvent event) {
        menu.writeMessage(event.getMessage(), false);
        if (!onlineMenu.playerMenuIsOn())
            onlineMenu.ShowOnlinePlayerMenu(connectedPlayer);
        else
            onlineMenu.handleStartPlayerTurnEvent(connectedPlayer, event ,model.getGame().getCurrentTurn());
    }

    @Override
    public void visitEvent(SuccessfulAttackEvent event) {
        List<Player> eliminatedPlayers = event.getEliminatedPlayersList();

        if (!eliminatedPlayers.isEmpty()) {
            for (Player player : eliminatedPlayers) {
                menu.writeMessage(connectedPlayer.getPlayerID()
                        + "has killed: " + player.getPlayerID(), true);
            }
        } else {
            menu.writeMessage(connectedPlayer.getPlayerID()
                    + event.getMessage(), true);
        }
        onlineMenu.handleSuccessfulAttackEvent(connectedPlayer, event);
    }

    @Override
    public void visitEvent(SuccessfulItemAdditionEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleSuccessfulItemAdditionEvent(connectedPlayer, event);
    }

    @Override
    public void visitEvent(SuccessfulMoveOnDangerousSectorEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleSuccessfulMove(connectedPlayer, event);
    }

    @Override
    public void visitEvent(SuccessfulMoveOnEscapePodSectorEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleSuccessfulMove(connectedPlayer, event);
    }

    @Override
    public void visitEvent(SuccessfulMoveOnSafeSectorEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleSuccessfulMove(connectedPlayer, event);
    }

    @Override
    public void visitEvent(TooMuchItemsEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleTooMuchItemsEvent(connectedPlayer, event);
    }

    @Override
    public void visitEvent(UnableToAttackEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleIllegalEvent(connectedPlayer, event);
    }

    @Override
    public void visitEvent(GreenEscapePodEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleGreenEscapePodEvent(connectedPlayer, event);
    }

    @Override
    public void visitEvent(NoiseInAnySectorEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleNoiseInAnySectorEvent(connectedPlayer, event);
        String inputValue;
        do {
        inputValue = JOptionPane.showInputDialog("Noise in Any Sector:\nPlease insert a valid sector id");
        }while(!ClientMenu.isValidID(inputValue.toUpperCase()));
        menu.writeMessage(connectedPlayer.getPlayerID() + " made noise in [" + inputValue.toUpperCase() + "]", true);
    }

    @Override
    public void visitEvent(NoiseInYourSectorEvent event) {
        menu.writeMessage(connectedPlayer.getPlayerID() + " made noise in [" + connectedPlayer.getPosition().getCompleteId() + "]", true);
        onlineMenu.handleNoiseInYourSectorEvent(connectedPlayer, event);
    }

    @Override
    public void visitEvent(RedEscapePodEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleRedEscapePodEvent(connectedPlayer, event);
    }

    @Override
    public void visitEvent(SilenceEvent event) {
        menu.writeMessage(connectedPlayer.getPlayerID() + " makes no sound", true);
        onlineMenu.handleSilenceEvent(connectedPlayer, event);
    }

    @Override
    public void visitEvent(SuccessfulGivenItemListEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleSuccessfulGivenItemListEvent(connectedPlayer, event);
    }

    @Override
    public void visitEvent(SuccessfulMoveOnStartingSectorEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleSuccessfulMove(connectedPlayer, event);
    }

    @Override
    public void visitEvent(SuccessfulMoveOnWallSectorEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleSuccessfulMove(connectedPlayer, event);
    }

    @Override
    public void visitEvent(SuccessfulUseOfItemEvent event) {
        menu.writeMessage(connectedPlayer.getPlayerID() + " " + event.getMessage(), true);    
        onlineMenu.handleSuccessfulUseOfItemEvent(connectedPlayer, event);
    }

    @Override
    public void visitEvent(SuccessfulUseOfSpotLightItemEvent event) {
        menu.writeMessage(event.getMessage(), true);
        onlineMenu.handleSuccessfulUseOfSpotLightItemEvent(connectedPlayer, event);
    }

    @Override
    public void visitEvent(GameStartedEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.handleGameStartedEvent(connectedPlayer, event);
    }

    @Override
    public void visitEvent(ConnectedToGameEvent connectedToGameEvent) {
        onlineMenu.updateShowIntro(matches, game);
    }

    @Override
    public void visitEvent(LogPrintEvent event) {
        menu.writeMessage(event.getMessage(), false);
        onlineMenu.updateLog(event);
    }

}
