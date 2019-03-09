package eftaios.view.cli;

import java.util.List;

import eftaios.ExceptionLogger;
import eftaios.controller.CreateGameInput;
import eftaios.controller.ItemInput;
import eftaios.controller.SelectedItemsInput;
import eftaios.controller.UserInput;
import eftaios.model.avatars.Player;
import eftaios.model.decks.drawables.Item;
import eftaios.model.decks.drawables.SpotLightItem;
import eftaios.model.events.EndGameEvent;
import eftaios.model.events.EndOfTurnEvent;
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
import eftaios.view.GameInfo;
import eftaios.view.UseItemException;
import eftaios.view.View;

public class CommandLineInterface extends View {
    /**
     * Extension of the view containing a client menu
     */
    
    protected ClientMenu menu;

    public CommandLineInterface(ClientMenu menu) {
        this.menu = menu;
    }

    /**
     * Function starts the CLI thread. Shows the intro and if the user can play, start the intro menu
     * @param nothing
     * @return void
     */
    @Override
    public void run() {
        menu.ShowIntro();
        if (menu.PlayStart())
            handleStartMenu();
    }

    protected void handleStartMenu() {
        GameInfo gameinfo=menu.ShowStartMenu(); //waits for the user to set up the game
        if(gameinfo==null) { //if the info are not written, retry by showing the menu again
            menu.writeMessage("fatal error loading game... retry",false);
        }
        setChanged();
        notifyObservers(new CreateGameInput(gameinfo)); //notify the controller of the creation of the model
    }

    
    //activated when the menu of the player is about to be shown. Returns the main info of the user
    protected void showPlayerMenu() {
        UserInput userInput;
        menu.writeMessage(showPlayerInfo(connectedPlayer), false);
        userInput = menu.ShowPlayerMenu(connectedPlayer);
        if (userInput != null) {
            setChanged();
            notifyObservers(userInput);
        }

    }


    protected void showEndMenu() {
        menu.writeMessage("***Game Ended***", false);
        handleStartMenu();
    }

    // Overrides of the methods visitEvent

    /**
     * Function that is called by the GameEvent accepter when the turn has just begun 
     * It updates the console and shows the player's functions menu
     * @return void
     * @param StartPlayerTurnEvent 
     * */
    @Override
    public void visitEvent(StartPlayerTurnEvent event) {
        menu.clearLog();
        menu.writeMessage(event.getMessage(), false);
        showPlayerMenu();
    }

    /**
     * Function that is called by the GameEvent accepter when the turn has just ended 
     * It updates the console and shows the player's functions menu
     * @return void
     * @param EndOfTurnEvent 
     * */
    @Override
    public void visitEvent(EndOfTurnEvent event) {
        menu.writeMessage(event.getMessage(), false);
        showPlayerMenu();
    }

    /**
     * Function that is called by the GameEvent accepter when the game has ended 
     * It updates the console and shows the end menu
     * @return void
     * @param EndGameEvent 
     * */
    @Override
    public void visitEvent(EndGameEvent event) {
        menu.writeMessage(event.getMessage(), false);
        showEndMenu();
    }

    /**
     * Function that is called by the GameEvent accepter when something bad happens
     * It updates the console and shows the player's functions menu
     * @return void
     * @param IllegalActionEvent 
     * */
    @Override
    public void visitEvent(IllegalActionEvent event) {
        menu.writeMessage(event.getMessage(), false);
        showPlayerMenu();
    }

    /**
     * Function that is called by the GameEvent accepter when the player acting isn't the right one
     * It updates the console and shows the player's functions menu
     * @return void
     * @param IllegalPlayerEvent 
     * */
    @Override
    public void visitEvent(IllegalPlayerEvent event) {
        menu.writeMessage(event.getMessage(), false);
        showPlayerMenu();
    }

    /**
     * Function that is called by the GameEvent accepter when the writing on the file isn't correctly executed
     * It updates the console and ends the game
     * @return void
     * @param IOErrorEvent 
     * */
    @Override
    public void visitEvent(IOErrorEvent event) {
        menu.writeMessage(event.getMessage(), false);

    }

    /**
     * Function that is called by the GameEvent accepter when the user requests an item
     * It updates the console and shows the player's functions menu
     * @return void
     * @param ItemRequestEvent 
     * */
    @Override
    public void visitEvent(ItemRequestEvent event) {
        menu.writeMessage(event.getMessage(), false);
        showPlayerMenu();
    }

   
    /**
     * Function that is called by the GameEvent accepter when the user has landed an attack
     *->get a lists of the eliminated players and checks
     *->if the size is 0, the player has killed no one
     *->if the size is >0, than the player has killed a list of players   
     * It updates the console and shows the player's functions menu
     * @return void
     * @param SuccessfulAttackEvent 
     * */
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
                    + "has killed no one", true);
        }

        showPlayerMenu();
    }

    /**
     * Function that is called when the user draws a card with an item upon it  
     * It updates the console and shows the player's functions menu
     * @return void
     * @param SuccessfulItemAdditionEvent 
     * */
    @Override
    public void visitEvent(SuccessfulItemAdditionEvent event) {
        menu.writeMessage(event.getMessage(), false);
        showPlayerMenu();
    }

    /**
     * Function that is called when the user has moved on a dangerous sector
     * It updates the console and shows the player's functions menu
     * @return void
     * @param SuccessfulMoveOnDangerousSectorEvent 
     * */
    @Override
    public void visitEvent(SuccessfulMoveOnDangerousSectorEvent event) {
        menu.writeMessage(event.getMessage(), false);
        showPlayerMenu();
    }

    /**
     * Function that is called when the user has moved on a escape pod sector
     * It updates the console and shows the player's functions menu
     * @return void
     * @param SuccessfulMoveOnEscapePodSectorEvent 
     * */
    @Override
    public void visitEvent(SuccessfulMoveOnEscapePodSectorEvent event) {
        menu.writeMessage(event.getMessage(), false);
        showPlayerMenu();
    }

    /**
     * Function that is called when the user has moved on a safe sector
     * It updates the console and shows the player's functions menu
     * @return void
     * @param SuccessfulMoveOnSafeSectorEvent 
     * */
    @Override
    public void visitEvent(SuccessfulMoveOnSafeSectorEvent event) {
        menu.writeMessage(event.getMessage(), false);
        showPlayerMenu();
    }

    /**
     * Function that is called when the user has landed an attack, but can't because
     * he's a human and has no attack cards, or an alien that tries to attack in the starting sector
     * It updates the console and shows the player's functions menu
     * @return void
     * @param UnableToAttackEvent 
     * */
    @Override
    public void visitEvent(UnableToAttackEvent event) {
        menu.writeMessage(event.getMessage(), false);
        showPlayerMenu();
    }

    /**
     * Function that is called when the user has picked a GreenEscapePodCard
     * It updates the console and shows the player's functions menu
     * @return void
     * @param GreenEscapePodEvent 
     * */
    @Override
    public void visitEvent(GreenEscapePodEvent event) {
        menu.writeMessage(event.getMessage(), false);
        showPlayerMenu();
    }

    /**
     * Function that is called when the user has picked a NoiseInAnySectorCard.
     * It waits for the user's input (the sector to make noise). 
     * Then it updates the console and shows the player's functions menu
     * @return void
     * @param NoiseInAnySectorEvent 
     * */
    @Override
    public void visitEvent(NoiseInAnySectorEvent event) {
        menu.writeMessage(event.getMessage(), false);
        menu.writeMessage("Choose a sector in which to make noise", false);
        menu.ShowNoiseInAnySectorMenu(connectedPlayer);
        showPlayerMenu();
    }

    /**
     * Function that is called when the user has picked a NoiseInYourSectorCard.
     * It outputs the user's position 
     * Then it updates the console and shows the player's functions menu
     * @return void
     * @param NoiseInYourSectorEvent 
     * */
    @Override
    public void visitEvent(NoiseInYourSectorEvent event) {
        menu.writeMessage(event.getMessage(), false);
        menu.writeMessage(connectedPlayer.getPlayerID() + " made noise in ["
                + connectedPlayer.getPosition().getCompleteId() + "]", true);
        showPlayerMenu();
    }

    /**
     * Function that is called when the user has picked a RedEscapePodCard.
     * Then it updates the console and shows the player's functions menu
     * @return void
     * @param RedEscapePodEvent 
     * */
    @Override
    public void visitEvent(RedEscapePodEvent event) {
        menu.writeMessage(event.getMessage(), false);
        showPlayerMenu();
    }

    /**
     * Function that is called when the user has picked a SilenceCard.
     * The message is outputted on the console.
     * Then it updates the console and shows the player's functions menu
     * @return void
     * @param SilenceCard 
     * */
    @Override
    public void visitEvent(SilenceEvent event) {
        menu.writeMessage(event.getMessage(), false);
        menu.writeMessage(connectedPlayer.getPlayerID() + " makes no sound",
                true);
        showPlayerMenu();
    }

    /**
     * Function that is called when the user has picked too many cards and the ownedItems list must be cropped..
     * It updates the console and shows the player's functions menu
     * @return void
     * @param SuccessfulGivenItemListEvent 
     * */
    @Override
    public void visitEvent(SuccessfulGivenItemListEvent event) {
        menu.writeMessage(event.getMessage(), false);
        showPlayerMenu();
    }

    /**
     * Function that is called when the user has moved on the starting sector
     * It updates the console and shows the player's functions menu
     * @return void
     * @param SuccessfulMoveOnStartingSectorEvent 
     * */
    @Override
    public void visitEvent(SuccessfulMoveOnStartingSectorEvent event) {
        menu.writeMessage(event.getMessage(), false);
        showPlayerMenu();
    }

    /**
     * Function that is called when the user has moved on a wall
     * It updates the console and shows the player's functions menu
     * @return void
     * @param SuccessfulMoveOnWallSectorEvent 
     * */
    @Override
    public void visitEvent(SuccessfulMoveOnWallSectorEvent event) {
        menu.writeMessage(event.getMessage(), false);
        showPlayerMenu();
    }

    /**
     * Function that is called when the user has used an item
     * It updates the console and shows the player's functions menu
     * @return void
     * @param SuccessfulUseOfItemEvent 
     * */
    @Override
    public void visitEvent(SuccessfulUseOfItemEvent event) {
        menu.writeMessage(
                connectedPlayer.getPlayerID() + " " + event.getMessage(), true);
        showPlayerMenu();
    }

    /**
     * Function that is called when the user has used a spotlight item
     * It updates the console and shows the player's functions menu
     * @return void
     * @param SuccessfulUseOfSpotLightItemEvent 
     * */
    @Override
    public void visitEvent(SuccessfulUseOfSpotLightItemEvent event) {
        menu.writeMessage(event.getMessage(), false);
        showPlayerMenu();
    }

    /**
     * Function that is called when the user has too many items.
     * It updates the console and shows the player's functions menu
     * @return void
     * @param TooMuchItemsEvent 
     * */
    @Override
    public void visitEvent(TooMuchItemsEvent event) {
        List<Item> itemsList = null;
        try {
            //grabs the items list of the user. If it's empty, retry.
                itemsList = menu.tooMuchItemMenu(event);
                if(itemsList==null) {
                    menu.writeMessage("fatal error sending input... retry",false);
                    itemsList = menu.tooMuchItemMenu(event);
                }
            } catch (UseItemException e) { //then the item must be used or removed
                ExceptionLogger.info(e);
                writeMessage(e.getMessage(), false);
                event.getCardEvent().acceptVisit(this); //if it happens, the card returns an Event
                setChanged();
                //if a Spotlight Item is used, the controller must be informed of the used Spotlight card: he will handle
                //the return of the infos of the player
                if(e.getItem() instanceof SpotLightItem){
                    SpotLightItem temp = (SpotLightItem) e.getItem();
                    temp.setSector(menu.ShowSpotLightMenu());
                    notifyObservers(new ItemInput(temp));  
                }
                else {
                    notifyObservers(new ItemInput(e.getItem()));
                }
            }
        //else just execute the card event and notify the controller to update the ownedItems of the currentPlayer in the model
        event.getCardEvent().acceptVisit(this);
        setChanged();
        notifyObservers(new SelectedItemsInput(itemsList));
    }

    
    @Override
    public void visitEvent(LogPrintEvent event) {
        menu.writeMessage(event.getMessage(), false);
            for(String log:event.getLog()) {
                menu.writeMessage(log,false);
            }
        showPlayerMenu();
    }
    
    @Override
    protected String showPlayerInfo(Player player) {
        return player.getInfo();
    }
}
