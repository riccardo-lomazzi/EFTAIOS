package eftaios.view.cli;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.List;

import eftaios.ExceptionLogger;
import eftaios.controller.EndGameInput;
import eftaios.controller.EndTurnInput;
import eftaios.controller.ItemInput;
import eftaios.controller.LogInput;
import eftaios.controller.MoveInput;
import eftaios.controller.UserInput;
import eftaios.model.avatars.AlienPlayer;
import eftaios.model.avatars.HumanPlayer;
import eftaios.model.avatars.Player;
import eftaios.model.board.SafeSector;
import eftaios.model.decks.drawables.Item;
import eftaios.model.decks.drawables.SpotLightItem;
import eftaios.model.events.SystemEventsMessage;
import eftaios.model.events.TooMuchItemsEvent;
import eftaios.model.gamerules.AdvancedGameRules;
import eftaios.model.gamerules.BasicGameRules;
import eftaios.view.ClientMenu;
import eftaios.view.GameInfo;
import eftaios.view.UseItemException;

public class LocalCLIClientMenu extends ClientMenu {


    /**
     * Class that writes a ClientMenu specifically for the local gaming
     * There's also a PrintStream for writing on the matchlog.txt file 
     * */
   

    public LocalCLIClientMenu(InputStream inputStream, OutputStream output) {
        super(inputStream, output);

    }

 
    /**
     * Function that is called when a message is communicated to the user's console.
     * If the flag writeOnMatchLog is true, it writes this information on the matchlog.txt file.
     * @return void
     * @param message String to be written, writeOnMatchLog boolean flag 
     * */
    @Override
    public void writeMessage(String message, boolean writeOnMatchLog) {
        writer.println(message);

        if (writeOnMatchLog) {
            try {
                PrintStream temp = new PrintStream(log);
                temp.println(message);
                temp.close();
            } catch (FileNotFoundException e) {
                ExceptionLogger.info(e);
            }
        
        }
    }

    /**
     * Function that is called when the main menu is shown.
     * Provides a list of commands an waits for the user's input.
     * @return void
     * @param nothing
     * */
    @Override
    public void ShowIntro() {
        writeMessage(Message.getWelcomeMessage(),false);
        while(playIntro){ //if true, the game configuration hasn't started yet, nor the match itself.
            command = readCommand(); //waits for user input.
            selectMainMenuResponse(command);
        }
    }

    @Override
    protected void selectMainMenuResponse(String command) {
        switch(command.toUpperCase()){
        case "INTRO": caseIntro();
                      break;
        case "START": caseExit();
                      playStart=true; //setting this to true avoids returning to the main menu
                      break;
        case "CREDITS": caseCredits();
                        break;
        case "EXIT": caseExit();
                     break;
        default: writeMessage(Message.getWelcomeMessage(),false);
                 break;
        }
    }

    /**
     * Function that is called when the user is configuring a match.
     * It gets the info of the match by calling getGameInfo and, to ensure confirmation, 
     * waits for the user's to say START to start the match with the chosen settings,
     * or RESTART to select new ones. If any command is wrong, a message is shown.
     * @return GameInfo with all the chosen settings of the match.
     * @param nothing
     * */
    @Override
    public GameInfo ShowStartMenu() {
        gameinfo = new GameInfo();
        do {
            getGameInfo();
            writeMessage("Type 'Start' to play 'Restart' reinsert the game infos or anything else to exit:", false);
            command = readCommand();
            switch (command.toUpperCase()) {
                case "START":
                    playIntro = false;
                    writeMessage("****The Game Starts Here****", false);
                    return gameinfo;
                case "RESTART":
                    gameinfo = new GameInfo();
                    reader.nextLine();
                    break;
                default:
                    writeMessage(Message.getWelcomeMessage(), false);
                    break;
            }
        } while ("RESTART".compareToIgnoreCase(command) == 0);
        return null;
    }

    @Override
    protected void getGameInfo() {
        getNumOfPlayers();
        getMap();
        getRules();
    }

    @Override
    protected void getRules() {
        writeMessage("Choose game mode 'Basic' or 'Advanced':", false);
        String rules = readCommand();
        switch (rules.toUpperCase()) {
            case "ADVANCED":
                gameinfo.setRules(new AdvancedGameRules());
                break;
            case "BASIC":
                gameinfo.setRules(new BasicGameRules());
                break;
            default:
                getRules();
                break;
        }
    }

    @Override
    protected void getMap() {
        writeMessage("Maps Available:", false);
        List<String> maps = viewAvailableMaps();
        writeMessage("Enter Map name:", false);
        String map = readCommand();
        while (!maps.contains(map)) {
            writeMessage("Wrong input try enter a new Map name:", false);
            map = readCommand();
        }
        gameinfo.setMapPath(map);
    }

    @Override
    protected void getNumOfPlayers() {
        Integer temp;
        writeMessage("Enter number of players from 2 to 8:", false);
        temp = readInt();
        while (temp < 2 || temp > 8) {
            writeMessage("Wrong Input retry to enter number of players from 2 to 8:", false);
            temp = readInt();
        }
        gameinfo.setNumberOfPlayers(temp);
    }

    /**
     * Function that is called when the menu of TooMuchItems is shown.
     * Gets the items list out of the TooMuchItemsEvent. 
     * Displays a list of all the items and when an item is chosen, waits for the user
     * to eliminate it or use it. If it uses it, it launches an exception.
     * @return updated list of items with the chosen one removed.
     * @param TooMuchItemsEvent containing the list of ownedItems plus the item picked up
     * @throws UseItemException
     * */
    @Override
    public List<Item> tooMuchItemMenu(TooMuchItemsEvent event) throws UseItemException {
        boolean validInput = false;
        int selection = 0;
        List<Item> itemsList = event.getPlayerItems();

        writeMessage("This is a list of your items. Choose the one you want to eliminate or use", false);

        writeMessage(event.getMessage(), true);
        while (!validInput) {
            try {
                // display a list of the items
                for (int i = 0; i < itemsList.size(); i++) {
                    writeMessage("[" + i + "]<--" + itemsList.get(i).getType(), false);
                }
                // while the input is wrong, keeps repeating the reading of an
                // integer
                selection = readInt();
                writeMessage("Insert '0' to use or '1' to remove the item or anything else to replay this menu :[" + selection + "]<--"
                        + itemsList.get(selection).getType(), false);
                if (readInt() == 0) {
                    validInput = true;
                    throw new UseItemException(itemsList.get(selection));
                }
                if (readInt() == 1) {
                    itemsList.remove(selection);
                    validInput = true;
                    return itemsList;
                }

            } catch (InputMismatchException | IndexOutOfBoundsException e) {
                ExceptionLogger.info(e);
                // logStream.log(Level.SEVERE, e.getMessage(), e);
                writeMessage("Wrong input! Choose again: ", false);

            }
        }
        // then communicate the choice to the player
        writeMessage("Item " + selection + "eliminated from your personal deck", false);
        return itemsList;
    }

    /**
     * Function that is called when the menu of Player's function is shown.
     * Shows the available actions to the user and wairs for an input.
     * Most functions are shown only if the user has the permissions
     * (e.g. attack is shown after a move). Returns the userinput to the controller.
     * @return userInput of the request chosen.
     * @param Player requesting the action.
     * */
    @Override
    public UserInput ShowPlayerMenu(Player player) {
        writeMessage("****Available Actions****", false);
        writeMessage("Help", false);
        showAvailableActions(player);
        command = readCommand();
        switch (command.toUpperCase()) {

            case "HELP":
                writeMessage(Message.getInGameMenuHelp(), false);
                return ShowPlayerMenu(player);

            case "MOVE":
                return getDestination();

            case "DRAW":
                return drawCheck(player);

            case "ITEM":
                return getSelectedItem(player);

            case "ATTACK":
                return attackCheck(player);

            case "END":
                return EndTurn(player);

            case "LOG":
                return new LogInput();

            case "EXIT":
                writeMessage(player.getPlayerID() + " has left the game", true);
                return new EndGameInput(logToString(log));

            default:
                writeMessage("Invalid command", false);
                return ShowPlayerMenu(player);
        }
    }

    @Override
    protected UserInput EndTurn(Player player) {
        if (player.hasAlreadyMoved() && (player.hasAlreadyDrawed() || player.alreadyAttacked() || player.getPosition() instanceof SafeSector)) {
            return new EndTurnInput(logToString(log));
        } else {
            writeMessage("You cannot end turn", false);
            return ShowPlayerMenu(player);
        }
    }

    @Override
    protected UserInput getSelectedItem(Player player) {
        if (player.hasAnyItem() && player instanceof HumanPlayer) {
            return showItemSelectionMenu(player);
        } else {
            if (player instanceof AlienPlayer)
                writeMessage("Alien cannot use items", false);
            else
                writeMessage("You don't have any item", false);
            return ShowPlayerMenu(player);
        }
    }

    @Override
    protected UserInput showItemSelectionMenu(Player player) {
        int selection;
        writeMessage("Enter number a valid item position (-1 to exit menu):", false);
        int i = 0;
        for (Item temp : player.getItems()) {
            writeMessage("[" + i + "]<--" + temp.getType(), false);
            i++;
        }
        selection = readInt();
        while (selection < 0 || selection >= player.getItems().size()) {
            if (selection == -1)
                return ShowPlayerMenu(player);
            writeMessage("Wrong Input retry to enter a valid item position:", false);
            selection = readInt();

        }
        if (player.getItems().get(selection) instanceof SpotLightItem) {
            SpotLightItem temp = (SpotLightItem) player.getItems().get(selection);
            temp.setSector(ShowSpotLightMenu());
            return new ItemInput(temp);
        } else {
            return new ItemInput(player.getItems().get(selection));
        }
    }

    @Override
    protected void showAvailableActions(Player player) {
        if (player.canIMove() && !player.alreadyAttacked())
            writeMessage("Move", false);
        if (((player.hasAttackItem() && player instanceof HumanPlayer) || player instanceof AlienPlayer) && !player.alreadyAttacked())
            writeMessage("Attack", false);
        if (player.hasAlreadyMoved() && (!player.hasAlreadyDrawed() || !player.alreadyAttacked() || player.getPosition() instanceof SafeSector))
            writeMessage("Draw", false);
        writeMessage("Item", false);
        writeMessage("Log", false);
        if (player.hasAlreadyMoved() && (player.hasAlreadyDrawed() || player.alreadyAttacked() || player.getPosition() instanceof SafeSector))
            writeMessage("End", false);
        writeMessage("Exit", false);
    }

    @Override
    protected UserInput getDestination() {
        String sectID;
        writeMessage(SystemEventsMessage.ENTERVALIDSECTOR.toString(), false);
        sectID = readCommand();
        while (!isValidID(sectID.toUpperCase().replaceAll("\\s", ""))) { //trim the string also between characters
            writeMessage(SystemEventsMessage.WRONGINPUTVALIDSECTOR.toString(), false);
            sectID = readCommand();

        }
        return new MoveInput(sectID.toUpperCase().replaceAll("\\s", ""));
    }

    /**
     * Function that is called when the menu of NoiseInAnySector is shown.
     * Waits for the user to insert a sector and checks if it's valid.
     * Writes the output to the user.
     * @return void
     * @param Player requesting the action.
     * */
    @Override
    public void ShowNoiseInAnySectorMenu(Player player) {
        String sectID;
        writeMessage(SystemEventsMessage.ENTERVALIDSECTOR.toString(), false);
        sectID = readCommand();
        while (!isValidID(sectID.toUpperCase().replaceAll("\\s", ""))) {
            writeMessage(SystemEventsMessage.WRONGINPUTVALIDSECTOR.toString(), false);
            sectID = readCommand();
        }
        writeMessage(player.getPlayerID() + " made noise in [" + sectID + "]", true);
    }

    /**
     * Function that is called when the menu of the Spotlight Card is shown.
     * Waits for the user to insert a sector and checks if it's valid.
     * Writes the output to the user and returns the sector chosen. It will be used
     * by the caller to show all the players near and into that sector.
     * @return String with the sector id
     * @param nothing
     * */
    @Override
    public String ShowSpotLightMenu() {
        String sectID;
        writeMessage(SystemEventsMessage.ENTERVALIDSECTOR.toString(), false);
        sectID = readCommand();
        while (!isValidID(sectID.toUpperCase().replaceAll("\\s", ""))) {
            writeMessage(SystemEventsMessage.WRONGINPUTVALIDSECTOR.toString(), false);
            sectID = readCommand();
        }
        return sectID;
    }

}
