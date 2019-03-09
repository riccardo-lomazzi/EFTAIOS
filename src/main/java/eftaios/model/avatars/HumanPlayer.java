package eftaios.model.avatars;

public class HumanPlayer extends Player {

    /**
     * 
     */
    private static final long serialVersionUID = -9162986708773338703L;

    public HumanPlayer(String id) {
    super(id);
    setMaxNumberOfMoves(1);
    }
    
    /**
     * Function that gets the Race of the player (Human in this case)
     * @return String race
     * @param nothing
     */
    @Override
    public String getRaceToString() {
        return "Human";
    }
}
