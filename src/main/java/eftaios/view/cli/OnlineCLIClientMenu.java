package eftaios.view.cli;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import eftaios.controller.CreateGameInput;
import eftaios.controller.SocketConnectToGameInput;
import eftaios.controller.UserInput;
import eftaios.network.socket.OnlineGameInfo;
import eftaios.view.GameInfo;

public class OnlineCLIClientMenu extends LocalCLIClientMenu {
    
    public OnlineCLIClientMenu(InputStream inputStream, OutputStream output) {
        super(inputStream, output);
    }
    
    @Override
    public void ShowIntro() {
        writeMessage(Message.getWelcomeMessage()+"\n'Game' to select or create a new game", false);
        while (playIntro) {
            command = readCommand();
            selectMainMenuResponse(command);
        }
    }
    
    @Override
    protected void selectMainMenuResponse(String command) {

        switch (command.toUpperCase()) {
        case "INTRO":
            caseIntro();
            break;
        case "START":
            playIntro = false;
            playStart = true;
            break;
        case "CREDITS":
            caseCredits();
            break;
        case "GAME":
            caseExit();
            chooseGame=true;
            playStart = true;
            break;
        case "EXIT":
            caseExit();
            break;
        default:
            writeMessage(Message.getWelcomeMessage(), false);
            break;
        }
    }

    public UserInput ShowOnlineStartMenu(List<OnlineGameInfo> matches) {

        writeMessage("***Wait Here for a game to start***:", false);
        writeMessage(
                "Type 'new' to create a new game or 'join' to select a game :", false);
        command = readCommand();
        switch (command.toUpperCase()) {
        case "NEW":
            return createGame();

        case "JOIN":
            return connectToAGame(matches);

        default:
            return ShowOnlineStartMenu(matches);
        }
    }

    private UserInput createGame() {
        gameinfo = new GameInfo();
        getMap();
        getRules();
        gameinfo.setNumberOfPlayers(8);
        return new CreateGameInput(gameinfo);
    }

    private UserInput connectToAGame(List<OnlineGameInfo> matches) {
        Integer temp;
        writeMessage("Enter the number of the game to join or -1 to exit:", false);
        for (int i = 0; i < matches.size(); i++) {
            writeMessage("[" + i + "]<--"+"Map:"+matches.get(i).getMapPath()+" Rules:"+matches.get(i).getRules().getClass().getSimpleName(), false);
        }
        temp = readInt();
        while (temp < 0 || temp > matches.size()) {
            if (temp == -1) {
                break;
            }
            writeMessage("Wrong Input retry to enter a number:", false);
            temp = readInt();
        }
        if (temp == -1)
            return null;
        else
            return new SocketConnectToGameInput(temp.intValue());
    }
    
    @Override
    public void writeMessage(String message, boolean writeOnMatchLog) {
        writer.println(message);
        /*if(writeOnMatchLog)
            matchLogWriter.println(message);*/
    }


}
