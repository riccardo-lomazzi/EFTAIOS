package eftaios.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import eftaios.ExceptionLogger;
import eftaios.controller.AttackInput;
import eftaios.controller.DrawInput;
import eftaios.controller.UserInput;
import eftaios.model.avatars.Player;
import eftaios.model.board.SafeSector;
import eftaios.model.decks.drawables.Item;
import eftaios.model.events.TooMuchItemsEvent;
import eftaios.view.cli.Message;

public  class ClientMenu {
    
    /**
     * A class for formatting game menus and writing on console.
     */
    
    protected Scanner reader;   

    protected PrintStream writer;
    protected boolean playIntro = true;
    protected GameInfo gameinfo;
    protected String command;
    protected boolean playStart = true;
    protected InputStream inputStream;
    protected File log;
    protected boolean chooseGame = false;
    protected boolean playerMenuIsOn = false;
    private Date date;

    public ClientMenu() {
    }

    /**
     * Create a new client menu with the specified stream to write messages it
     * is used to handle a command line type of menu
     * 
     * @param inputStream
     *            the stream to read commands
     * @param output
     *            the stream to write responses
     */
    public ClientMenu(InputStream inputStream, OutputStream output) {
        this.inputStream = inputStream;
        this.writer = new PrintStream(output);
        createLog();
    }

    protected void createLog() {
        date = new Date();
        String temp = date.toString().replaceAll("\\s", "").replaceAll(":", "");
        log = new File(".\\clientMatchLogs\\matchLog" + temp + ".txt");
        try {
            log.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            ExceptionLogger.info(e);
        }
    }

    protected String readCommand() {
        if (reader != null)
            reader.reset();
        try {
            reader = new Scanner(inputStream);
            command = reader.nextLine();
        } catch (InputMismatchException e) {
            ExceptionLogger.info(e);
            writeMessage("Wrong Input type again: " + e.getMessage(), false);
            reader.nextLine();
            return readCommand();
        }
        /*
         * spaces must be removed for a grater range of possible commands
         */
        return command.replaceAll("\\s", "");
    }

    protected Integer readInt() {
        if (reader != null)
            reader.reset();
        Integer number = -1;
        try {
            reader = new Scanner(inputStream);
            number = reader.nextInt();
        } catch (InputMismatchException e) {
            ExceptionLogger.info(e);
            writeMessage("Wrong Input type again: " + e.getMessage(), false);
            reader.nextLine();
            return readInt();
        }
        return number;
    }

    protected boolean isAnHexMap(File file) {
        return file.isFile() && ".hexmap".equals(file.getName().substring(file.getName().lastIndexOf(".")));
    }

    /**
     * Check if the given string is a valid id for a game sector
     * 
     * @param sectID
     *            the string to be checked
     * @return true if the string is a valid identifier for a sector
     */
    public static boolean isValidID(String sectID) {
        /*
         * the sector identifier can be of two of three characters long i.e.:A1
         * A12 all other lengths are sufficient to prove the string as an
         * invalid sector identifier
         */
        try {
            switch (sectID.length()) {

                case 2:
                    return isCharValid(sectID.charAt(0)) && isIntBetween(Integer.parseInt("" + sectID.charAt(1)), 1, 9);

                case 3:
                    return isCharValid(sectID.charAt(0)) && isIntBetween(Integer.parseInt("" + sectID.charAt(1)), 1, 1)
                            && isIntBetween(Integer.parseInt("" + sectID.charAt(2)), 0, 4);

                default:
                    return false;
            }
        } catch (NumberFormatException e) {
            /*
             * this error occurs when the char at 1 or at 2 or both are not
             * integer and thus the string is not a valid sector identifier
             */
            ExceptionLogger.info(e);
            return false;
        }
    }

    protected static boolean isCharValid(Character c) {
        return c <= 87 && c >= 65;
    }

    protected static boolean isIntBetween(int x, int min, int max) {
        return x >= min && x <= max;
    }

    protected UserInput attackCheck(Player player) {
        if (player.hasAlreadyMoved() && !player.alreadyAttacked() && !player.hasAlreadyDrawed())
            return new AttackInput();
        else {
            writeMessage("unable to attack", false);
            return ShowPlayerMenu(player);
        }
    }

    protected UserInput drawCheck(Player player) {
        if (player.hasAlreadyMoved() && !player.alreadyAttacked() && !player.hasAlreadyDrawed() || player.getPosition() instanceof SafeSector)
            return new DrawInput();
        else {
            writeMessage("unable to draw", false);
            return ShowPlayerMenu(player);
        }
    }

    /**
     * @return the list of maps found in the root folder of this project
     */
    public List<String> viewAvailableMaps() {
        List<String> maps = new ArrayList<String>(10);
        File mapFolder = new File("./hexmaps/");
        File[] listOfFiles = mapFolder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (isAnHexMap(file)) {
                    String mapName = file.getName().substring(0, file.getName().lastIndexOf("."));
                    writeMessage(mapName, false);
                    maps.add(mapName);
                }
            }
        }
        return maps;
    }
      
   
    /**
     * Function that returns the playStart value 
     * @return boolean
     * @param nothing
     */
    public boolean PlayStart() {
        return playStart;
    }
    

    protected void caseIntro() {
        writeMessage(Message.getIntroMessage(), false);
    }
     //load credits
    protected void caseCredits() {
        writeMessage(Message.getCreditsMessage(), false);
    }

    //load exit
    protected void caseExit() {
        playIntro=false; //by putting playIntro false, the menu are not repeated anymore
        playStart=false;  //this avoids the start of the player menu
    }
    
    /**
     * Function that writes a message on the inputStream, and set its visibility according to the boolean flag
     * if true = it will write on the matchlog.txt file, visibile by everyone. It won't do that if false. 
     * @return void
     * @param String message to be written, messageVisibleToEveryone flag for writing on the matchlog.txt file
     */
    public  void writeMessage(String message, boolean messageVisibleToEveryone){}
    
    /**
     * Function that shows an intro message 
     * @return void
     * @param nothing
     */
    public  void ShowIntro(){}

    /**
     * Function that shows the start menu of the player with all the possible functions, and
     * captures an userInput command to be sent to the controller 
     * @return UserInput to be executed
     * @param currentPlayer requesting the menu
     */
    public  UserInput ShowPlayerMenu(Player player) {
        return null;
    }
    
    /**
     * Function that captures the settings of the match decided by the user. Returns a GameInfo wrapper
     * of all those infos. 
     * @return GameInfo variable containing number of players, map to be loaded, type of rules to be applied
     * @param nothing
     */
    public  GameInfo ShowStartMenu(){
        return null;
    }

    /**
     * Function that's invoked when the player uses a NoiseInAnySectorCard and captures the Sector written by the user 
     * @return void
     * @param player that has used the card Noise in any sector
     */
    public  void ShowNoiseInAnySectorMenu(Player currentPlayer){
        
    }

    /**
     * Function that contains all the SpotlightCard info when used: 
     * players in the decided sector and the others around it 
     * @return String containing the discovered players
     * @param nothing
     */
    public  String ShowSpotLightMenu(){
        return null;
    }

    /**
     * Function that opens up a menu when to player has too many items and has to eliminate one 
     * @return updated List of ownedItems of the user
     * @param the GameEvent containing the ownedItems of the user
     * @throws UseItemException when the player uses the item instead of removing it
     */
    public  List<Item> tooMuchItemMenu(TooMuchItemsEvent event)  throws UseItemException{
        return null;
    }
    
    protected  void selectMainMenuResponse(String command){}

    protected  void getGameInfo(){}
    
    protected  void getRules(){}
    
    protected  void getMap(){}
    
    protected  void getNumOfPlayers(){}
    
    protected  UserInput EndTurn(Player player){
        return null;
    }
    
    protected  UserInput getSelectedItem(Player player){
        return null;
    }
    
    protected  UserInput showItemSelectionMenu(Player player){
        return null;
    }
    
    protected  void showAvailableActions(Player player){}
    
    protected  UserInput getDestination(){
        return null;
    }


    public void closeScanner() {
        if (reader != null) {
            reader.close();
        }
    }

    public boolean ChooseGame() {
        return chooseGame;
    }

    public boolean playerMenuIsOn() {
        return playerMenuIsOn;
    }

    public void ShowPlayerMenu(Player connectedPlayer, BigInteger gameID) {
    }

    /**
     * clear the log file related to this client so that the same line are not
     * written more then one time on the server log file
     */
    public void clearLog() {
        log.delete();
        try {
            log.createNewFile();
        } catch (IOException e) {
            ExceptionLogger.info(e);
        }
    }

    protected List<String> logToString(File log) {
        List<String> temp = new ArrayList<String>();
        Scanner reader;
        try {
            reader = new Scanner(log);
            while (reader.hasNextLine()) {
                temp.add(reader.nextLine());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            ExceptionLogger.info(e);
        }
        return temp;
    }

    /**
     * @return a string array containing the lines of the log file
     */
    public List<String> getLogToString() {
        return logToString(log);
    }

    /**
     * Show the in game menu of a player
     * @param connectedPlayer the player connected to this client
     */
    public void ShowOnlinePlayerMenu(Player connectedPlayer) {
    }

}
