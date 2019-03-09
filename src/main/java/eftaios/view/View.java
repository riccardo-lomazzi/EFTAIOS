package eftaios.view;

import java.net.InetAddress;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import eftaios.controller.UserInput;
import eftaios.model.Model;
import eftaios.model.avatars.Player;
import eftaios.model.events.ConnectedToGameEvent;
import eftaios.model.events.EndGameEvent;
import eftaios.model.events.EndOfTurnEvent;
import eftaios.model.events.GameEvent;
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
import eftaios.model.match.Game;
import eftaios.network.socket.OnlineGameInfo;

    /**
     * Observable (by the controller) and Observer(of the Model) class containing
     * a copy the model of the user  
     * all of his infos
     */
public abstract class View extends Observable implements Observer, EventVisitor, Runnable {

    protected Player connectedPlayer;
    protected Game game;
    protected Model model;
    protected boolean play = true;
    protected boolean running;
    protected InetAddress viewID;
    protected List<OnlineGameInfo> matches;
    protected ClientMenu menu;

    /**
     *Creates a new view 
     */
    public View() {
    }

    /**
     * Function that gets the Model for the View
     * @param nothing
     * @return Model file
     */
    public Model getModel() {
        return model;
    }

    
    /**
     * Function that gets the currentPlayer of the View
     * @param nothing
     * @return String path of hexmap file
     */
    public Player getCurrentPlayer(){
        return connectedPlayer;
    }
    
   

    /**
     * Function that everytime the model is updated, the view captures the model and stores it
     * also stores the game and the currentPlayer.
     * After that, it executes the command by being the EventVisitor of the GameEvent sent as Object
     * @param Observable obs (Model), Object arg (GameEvent)
     * @return void
     */
    @Override
    public void update(Observable obs, Object arg) {
        // saves the game locally
        model = (Model) obs;
        game = model.getGame();
        // saves the currentPlayer
        connectedPlayer = game.getCurrentPlayer();
        // do something with a specific event
        ((GameEvent) arg).acceptVisit(this);
    }

    @Override
    public void visitEvent(EndOfTurnEvent event) {
    }

    @Override
    public void visitEvent(EndGameEvent event) {
    }

    @Override
    public void visitEvent(IllegalActionEvent event) {
    }

    @Override
    public void visitEvent(IllegalPlayerEvent event) {
    }

    @Override
    public void visitEvent(IOErrorEvent event) {
    }

    @Override
    public void visitEvent(ItemRequestEvent event) {
    }

    @Override
    public void visitEvent(StartPlayerTurnEvent event) {
    }

    @Override
    public void visitEvent(SuccessfulAttackEvent event) {
    }

    @Override
    public void visitEvent(SuccessfulItemAdditionEvent event) {
    }

    @Override
    public void visitEvent(SuccessfulMoveOnDangerousSectorEvent event) {
    }

    /**
     * @does controls the picked card and communicates it to the player
     * @return void
     */
    @Override
    public void visitEvent(SuccessfulMoveOnEscapePodSectorEvent event) {
    }

    @Override
    public void visitEvent(SuccessfulMoveOnSafeSectorEvent event) {
    }

    @Override
    public void visitEvent(TooMuchItemsEvent event) {
    }

    @Override
    public void visitEvent(UnableToAttackEvent event) {
    }

    @Override
    public void visitEvent(GreenEscapePodEvent event) {
    }

    @Override
    public void visitEvent(NoiseInAnySectorEvent event) {
    }

    @Override
    public void visitEvent(NoiseInYourSectorEvent event) {
    }

    @Override
    public void visitEvent(RedEscapePodEvent event) {
    }

    @Override
    public void visitEvent(SilenceEvent event) {
    }

    @Override
    public void visitEvent(SuccessfulGivenItemListEvent event) {
    }

    @Override
    public void visitEvent(SuccessfulMoveOnStartingSectorEvent event) {
    }

    @Override
    public void visitEvent(SuccessfulMoveOnWallSectorEvent event) {
    }

    @Override
    public void visitEvent(SuccessfulUseOfItemEvent event) {
    }

    @Override
    public void visitEvent(SuccessfulUseOfSpotLightItemEvent event) {
    }

    @Override
    public void visitEvent(GameStartedEvent event) {
    }

    @Override
    public void visitEvent(ConnectedToGameEvent event) {
    }
    
    @Override
    public void visitEvent(LogPrintEvent event) {
    }

    /**
     * Start this view
     */
    @Override
    public void run() {
    }

    protected String showPlayerInfo(Player player) {
        return null;
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * set the client id related to this view
     */
    public void setViewID(InetAddress inetAddress) {
        viewID = inetAddress;
    }

    public InetAddress getViewID() {
        return viewID;
    }

    public void setMatches(List<OnlineGameInfo> matches) {
        this.matches = matches;
    }

    public void setCurrentPlayer(Player player) {
        connectedPlayer = player;
    }

    public void closeScanner() {
        menu.closeScanner();
    }

    public void writeMessage(String string, boolean b) {
    }

    
    public void ChangeAndNotify( UserInput userInput) {
        setChanged();
        notifyObservers(userInput);
    }

    
    public ClientMenu getMenu() {
        return menu;
    }

}
