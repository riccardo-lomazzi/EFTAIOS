package eftaios.model.events;

public enum SystemEventsMessage {
     WRONGPLAYER("Wrong Player! Synchronizing game..."),
     ENTERVALIDSECTOR("Enter number a valid Sector ID"),
     WRONGINPUTVALIDSECTOR("Wrong Input retry to enter a valid SectorID");
     
     private String message;
     
     SystemEventsMessage(String message){
         this.message=message;
     }
     /**
      * Function that overrides the ToString method and returns a defined value of the enum
      * @return String
      * @param /
      */
     @Override
     public String toString(){
         return message;
     }
}
