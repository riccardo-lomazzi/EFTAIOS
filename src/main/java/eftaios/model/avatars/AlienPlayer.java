package eftaios.model.avatars;

public class AlienPlayer extends Player {

    /**
     * 
     */
    private static final long serialVersionUID = -2761184810299449046L;

    public AlienPlayer(String id) {
    super(id);
    setMaxNumberOfMoves(2);
    }
    
    /**
     * Function that gets the Race of the player (Alien in this case)
     * @return String race
     * @param nothing
     */
    @Override
    public String getRaceToString() {
        return "Alien";
    }
}
