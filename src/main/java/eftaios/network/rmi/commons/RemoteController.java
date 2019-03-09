package eftaios.network.rmi.commons;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Observable;

import eftaios.ExceptionLogger;
import eftaios.controller.AttackInput;
import eftaios.controller.Controller;
import eftaios.controller.DrawInput;
import eftaios.controller.EndGameInput;
import eftaios.controller.EndTurnInput;
import eftaios.controller.RMIConnectToGameInput;
import eftaios.controller.UserInput;
import eftaios.model.Model;
import eftaios.network.rmi.server.ServerInterface;
import eftaios.view.View;

public class RemoteController extends Controller implements RemoteControllerInterface {

    /**
     * Class stub for connecting to the server. It implements the remote
     * methods. It holds the ConnectionData of the player's session and, a
     * userInput that is memorized a blockActions flag to avoid any user writing
     * of something on the console.
     */

    private UserInput userInput;
    private ServerInterface server;
    private PlayerConnectionData pcd;
    private boolean blockActions;
    private boolean connected;

    public RemoteController(Model model, View view) {
        super(model, view);
        blockActions = false;
        connected = false;
    }

    public RemoteController(View view, UserInput userInput) {
        this.view = view;
        this.userInput = userInput;
        connected = false;
    }

    public RemoteController(Model model, View view, UserInput userInput) {
        super(model, view);
        this.userInput = userInput;
        connected = false;
    }

    @Override
    public void setModel(Model model) throws RemoteException {
        this.model = model;
    }

    @Override
    public void setServer(ServerInterface server) throws RemoteException {
        this.server = server;
    }

    @Override
    public PlayerConnectionData getPlayerConnectionData() throws RemoteException {
        return pcd;
    }

    public boolean isConnected() {
        return connected;
    }

    /**
     * Function that saves the userInput of the player's view and executes its
     * command. If the userInput is of endTurnInput type, it overwrites the data
     * on the server. If it's a EndGameInput, it still overwrites the model on
     * the server's side, but also disconnects..
     * 
     * @return void
     * @param Observable
     *            object, UserInput casted as Object
     * @throws IllegalArgumentException
     *             when the observable is not the view or the Input is not a
     *             UserInput
     */
    @Override
    // everytime an user input will come, it will be outputted to the server
    public void update(Observable o, Object userInput) {
        if (o != view || !(userInput instanceof UserInput)) {
            throw new IllegalArgumentException();
        }
        // the model on the user/server will update only if the user has granted
        // permissions
        if (!blockActions) {
            // user's model update
            this.userInput = (UserInput) userInput;
            this.userInput.executeCommand(model, model.getCurrentPlayer());
            // update the model on the server only if the user input is an
            // EndTurnInput

            if (userInput instanceof EndTurnInput) {
                processUserInput((EndTurnInput) userInput);
            }
            /*
             * if the userInput is an EndGameEvent (when the user call it quits,
             * basically), his data will be stored on the client
             */
            else {
                if (userInput instanceof EndGameInput) {
                    processUserInput((EndGameInput) userInput);
                }
                if (userInput instanceof AttackInput) {
                    processUserInput((AttackInput) userInput);
                }
                if (userInput instanceof DrawInput) {
                    processUserInput((DrawInput) userInput);
                }
            }
        } else {
            writeSomethingOnClientConsole("WAIT FOR YOUR TURN");
        }
    }

    private void processUserInput(DrawInput input) {
        writeSomethingOnServerConsole(model.getCurrentPlayer().getPlayerID() + " is drawing from the deck");
    }

    private void processUserInput(EndTurnInput input) {
        updateModelOnTheServer();
    }

    private void processUserInput(AttackInput input) {
        writeSomethingOnServerConsole(model.getCurrentPlayer().getPlayerID() + " is landing an attack");
    }

    private void processUserInput(EndGameInput input) {
        writeSomethingOnClientConsole("You're being extracted...");
        pcd.saveOnDisk();
        removeMeFromServer(); //detach from server
    }

    /**
     * Function that removes the user by deleting himselft from the server's
     * array and unexporting himself as a UnicastRemoteObject
     * 
     * @return void
     * @param nothing
     */
    private void removeMeFromServer() {
        try {
            // first set up the server to remove this object, then unexport it
            server.removeClient(this, model);
            connected = false;
            UnicastRemoteObject.unexportObject(this, true);
        } catch (RemoteException e) {
            ExceptionLogger.info(e);
        }
    }

    /**
     * Remote method invoked when client wants to connect to server's stub It
     * granbs the ConnectionData (updatedModel, server and Player's ID)
     * 
     * @return void
     * @param nothing
     * @throws RemoteException
     */
    public void executeConnectCommand() throws RemoteException {
        PlayerConnectionData temp = null;
        if (userInput instanceof RMIConnectToGameInput)
            temp = ((RMIConnectToGameInput) userInput).executeCommand(this);
        // if server returns null;
        if (temp == null) {
            writeSomethingOnClientConsole("Unable to get the data from the server. Disconnecting...");
            return;
        } else {
            // updates the connected flag
            connected = true;
            // save the connection data
            pcd = (PlayerConnectionData) temp.clone();
            // set the server
            setServer(pcd.getServer());
            // we'll update the current model with the one sent by the server
            setModel(pcd.getModel());
            // then this RemoteController will listen to the current player's
            // View
            view.addObserver(this);
            // and the player's CLI will listen the player's Model
            model.addObserver(view);
            // tell the server we're ready to play
            server.readyToPlay();
        }
    }

    /**
     * Remote method invoked when the server blocks the actions of the user to
     * prevent him from playing during other's turn.
     * 
     * @return void
     * @param nothing
     * @throws RemoteException
     */
    @Override
    public void blockActions() throws RemoteException {
        blockActions = true;
    }

    /**
     * Remote method invoked when the server unlocks the actions of the user
     * when it's his turn.
     * 
     * @return void
     * @param nothing
     * @throws RemoteException
     */
    
    @Override
    public void unlockActions() throws RemoteException {
        blockActions = false;
    }

    /**
     * Remote method invoked when the server wants to write on the console of
     * the user.
     * 
     * @return void
     * @param nothing
     * @throws RemoteException
     */

    @Override
    public void dispatchMessage(String message) throws RemoteException {
        ((RMILocalCommandLineInterface) view).writeMessage(message, false);
    }

    /**
     * Remote method invoked when the server wants to start the turn of the
     * player. The observer of the cli is notified, thus generating the view
     * commands.
     * 
     * @return void
     * @param nothing
     * @throws RemoteException
     */
    @Override
    public void callPlayerMenuInCLI() throws RemoteException {
        ((ServerModel) model).notifyViewObserver(((ServerModel) model).getGameEvent());
    }

    /**
     * Remote method invoked when the server allows the player to start his
     * turn. It resets the observer of the model and the view since they've been
     * changed undirectly. Then updated the model and currentPlayer stats in
     * pcd. Proceeds then to notify the observer of the view..
     * 
     * @return void
     * @param nothing
     * @throws RemoteException
     */
    @Override
    public void unlockClient() throws RemoteException {
        // resetting the observers is necessary to fake a setChanged()
        resetObservers();
        // update the new data in the pcd, to be able to save it in case of a
        // disconnection of the client/server
        updateCurrentPlayerInPcd();
        updateLatestModelInPcd();
        // unlock the actions of the player
        unlockActions();
        // bring up the menu
        callPlayerMenuInCLI();
    }

    private void updateCurrentPlayerInPcd() {
        pcd.setPlayer(model.getCurrentPlayer());
    }

    private void updateLatestModelInPcd() {
        pcd.setModel(model);
    }

    private void resetObservers() {
        view.deleteObservers();
        model.deleteObservers();
        view.addObserver(this);
        model.addObserver(view);
    }

    private void updateModelOnTheServer() {
        try {
            // server's model gets an update with a clone (think of it as
            // writing on a stream)
            server.updateModel((ServerModel) ((ServerModel) model).clone());
            writeSomethingOnClientConsole("SERVER UPDATED");
        } catch (RemoteException e) {
            writeSomethingOnClientConsole("Can't find server " + e.getMessage());
        }
    }

    private void writeSomethingOnClientConsole(String message) {
        try {
            dispatchMessage(message);
        } catch (RemoteException e) {
            ExceptionLogger.info(e);
        }
    }

    private void writeSomethingOnServerConsole(String message) {
        try {
            server.dispatchMessage(message);
        } catch (RemoteException e) {
            ExceptionLogger.info(e);
        }
    }

    public boolean getBlockActions() {
        return blockActions;
    }

}
