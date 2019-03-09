package eftaios.view.cli;

public class Message {
    
    /**
     * Class containing functions that returns default strings, such as menus or warnings.
     * Since they're predefined, it's best to keep these functions static.
     * */
    
    private Message() {
    }
    
    /**
     * Function that is called for the main menu available commands..
     * @return String 
     * @param nothing
     * */
    public static String getWelcomeMessage(){
        return "***ESCAPE FROM THE ALIENS IN THE OTHER SPACE***\n"
                + "Type a command from the menu:\n"
                + "'Intro' to read the game intro\n"
                + "'Start' to set up a new game\n"
                + "'Credits' to see the credits\n"
                + "'Exit' to exit the game";
    }
    
    /**
     * Function that is called to show the Intro (this text is written on the back of the original game).
     * @return String 
     * @param nothing
     * */
    public static String getIntroMessage(){
        return "For the last four years, medical équipe led by doctor Antonio Merz has been working on spaceship\n"
                + "SELVA to find the cure for the Bellavita’s disease, a brain-degenerative illness that has been \n"
                + "killing people on Earth by the millions. In the last few weeks research had begun showing positive \n"
                + "results thorough the implantation of alien spores in the cerebral cortex of a selected number of \n"
                + "guinea pigs. On the morning of the 26th of July chief medical attendant Fabrizio Miraggio was put \n"
                + "in quarantine following an incident with one of the experimental animals: security personnel \n"
                + "had to forcefully block him while he was trying to devour Bibsy-332, an experimental monkey \n"
                + "carrying the spores. ON THE 28TH OF JULY ALL COMMUNICATION WITH SPACESHIP SELVA CEASED. \n"
                + "Press enter to go back to main menu...";
    }
    
    /**
     * Function that is called to show the Credits 
     * @return String 
     * @param nothing
     * */
    public static String getCreditsMessage() {
        return "Based on a SANTA RAGIONE board game this computer game was brought you\n"
                + "by Andrea Jegher and Riccardo Lomazzi\n"
                + "Press enter to go back to main menu...";
    }

    /**
     * Function that is called to show the help menu 
     * @return String 
     * @param nothing
     * */
    public static String getInGameMenuHelp() {
        return "Help -view this screen\n"
                + "Move -To move in another location es: A8 , B12\n"
                + "Attack -type Attack\n"
                + "Draw -Drawa card\n"
                + "End -End your turn\n"
                + "Item -See your items\n"
                + "Log -See match logs\n"
                + "Exit -exit the game\n";
    }
}
