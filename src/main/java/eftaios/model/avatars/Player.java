package eftaios.model.avatars;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import eftaios.model.board.Sector;
import eftaios.model.decks.drawables.AttackItem;
import eftaios.model.decks.drawables.DefenseItem;
import eftaios.model.decks.drawables.Item;

public abstract class Player implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 3626149822099708587L;
    private static final int MAXOWNEDITEMS = 3;
    protected final String playerID;
    protected int maxNumberOfMoves;
    protected int movesThisTurn;
    protected List<Item> ownedItems;
    protected Sector position;
    protected Sector turnStartingPosition;
    protected List<Sector> adjacentSectors; 
    protected boolean alreadyAttacked=false;
    protected boolean sedated=false;
    protected boolean alreadyDrawed=false;
    protected boolean alreadyMoved=false;


    public Player(String playerID) {
    this.playerID = playerID;
    this.ownedItems=new ArrayList <Item>(MAXOWNEDITEMS);
    }
    
    /**
     * Function that returns the alreadyDrawed value 
     * @return boolean
     * @param nothing
     */
    public boolean hasAlreadyDrawed() {
        return alreadyDrawed;
    }

    /**
     * Function that sets the alreadyDrawed value 
     * @return void
     * @param alreadyDrawed boolean flag to set
     */
    public void setAlreadyDrawed(boolean alreadyDrawed) {
        this.alreadyDrawed = alreadyDrawed;
    }

    /**
     * Function that returns the alreadyMoved value 
     * @return boolean
     * @param nothing
     */
    public boolean hasAlreadyMoved() {
        return alreadyMoved;
    }

    /**
     * Function that sets the alreadyMoved value 
     * @return void
     * @param alreadyDrawed boolean flag to set
     */
    public void setAlreadyMoved(boolean alreadyMoved) {
        this.alreadyMoved = alreadyMoved;
    }
    
    /**
     * Function that returns the playerID value 
     * @return String
     * @param nothing
     */
    public String getPlayerID() {
        return playerID;
    }

    /**
     * Function that returns the current Position of the player 
     * @return Sector variable
     * @param nothing
     */
    public Sector getPosition() {
      return position;
    }

    /**
     * Function that sets the Position for the player 
     * @return void
     * @param Sector position to be set
     */
    public void setPosition(Sector position) {
      this.position = position;
    }
    
    /**
     * Function that sets the max number of moves per turn according to the selected Rules
     * @return void
     * @param maximum number of moves, stored as an int
     */
    public void setMaxNumberOfMoves(int maxNumberOfMoves) {
      this.maxNumberOfMoves = maxNumberOfMoves;
    }

    /**
     * Function that compares the number of moves done in the turn 
     * with the maximum allowed according to the rules,
     * and returns the boolean result  
     * @return boolean
     * @param nothing
     */
    public boolean canIMove() {
        return maxNumberOfMoves>movesThisTurn;
    }
    
    /**
     * Function that adds an Item to the item list 
     * @return void
     * @param alreadyDrawed boolean flag to set
     */
    public void addItem(Item item){
        ownedItems.add(item);
    }

    /**
     * Function that checks if the player has AttackItems in the ownedItems array 
     * @return boolean
     * @param nothing
     */
    public boolean hasAttackItem(){
         for(Item item:ownedItems)
             if (item instanceof AttackItem)
                 return true;
         return false;
    }
    

    /**
     * Function that checks if the player has DefenceItems in the ownedItems array 
     * @return boolean
     * @param nothing
     */
    public boolean hasDefenseItem(){

        for(Item item:ownedItems)
            if (item instanceof DefenseItem)
                return true;
        return false;
    }
    
    /**
     * Function that removes an Items from the ownedItems deck and returns it 
     * @return Item to be removed, null if Item not found
     * @param Item to be removed
     */
    public Item removeItem(Item item){
        /*
         * search the array for the first item of the same instance
         * of the argument's item and remove it from the owned items
         * and return it
         */
        for(Item owned:ownedItems)
            if (owned.getType().equals(item.getType())){
                return ownedItems.remove(ownedItems.indexOf(owned));
            }
        return null;
    }
    
    /**
     * Function that returns the alreadyAttacked value 
     * @return boolean
     * @param nothing
     */
    public boolean alreadyAttacked(){
        return alreadyAttacked;
    }
    
    /**
     * Function that sets the alreadyAttacked flag for the player 
     * @return void
     * @param alreadyAttacked flag to be set
     */
    public void setAlreadyAttacked(boolean alreadyAttacked){
        this.alreadyAttacked=alreadyAttacked;
    }
    /**
     * Function that sets the moves available for the current turn of the player 
     * @return void
     * @param available turn moves to be set
     */
    public void setMovesThisTurn(int movesThisTurn){
        this.movesThisTurn=movesThisTurn;
    }
    
    /**
     * Function that increments the number of the moves available for the player's current turn 
     * @return void
     * @param nothing
     */
    public void incrementMovesThisTurn(){
        movesThisTurn++;
    }

    /**
     * Function that decrements the number of the moves available for the player's current turn 
     * @return void
     * @param nothing
     */
    public void decrementMovesThisTurn() {
        movesThisTurn--;
    }
    
    /**
     * Function that returns the isSedated value 
     * @return boolean
     * @param nothing
     */
    public boolean isSedated() {
        return sedated;
    }
    
    /**
     * Function that sets the sedated value of the player after he's been hit with a sedative card
     * @return void
     * @param sedated flag to be set
     */
    public void setSedated(boolean sedated) {
        this.sedated = sedated;
    }

    /**
     * Function used when a new ownedItems array must be set
     * @return void
     * @param itemsList to be set
     */
    public void giveItems(List<Item> itemsList) {
        ownedItems=itemsList;
    }
    
    /**
     * Function that returns a race with the value Unknown
     * @return String
     * @param nothing
     */
    public String getRaceToString() {
        return "Unknown";
    }
    
    /**
     * Function that returns a list of adjacent sectors
     * @return List of adjacent Sectors
     * @param nothing
     */
    public List<Sector> getAdjacentSectors(){
        return adjacentSectors;
    }
    
    /**
     * Function that returns the player info in string format
     * @return String with infos
     * @param nothing
     */
    public String getInfo(){
        return  "Player ID: " + getPlayerID() + 
                "\nRace: " +getRaceToString() +
                "\nPosition: ["+getPosition().getCompleteId()+"]" +
                "\nNear Sectors:"+showAdjacentSectors();
    }

    /**
     * Function that returns the near sectors of the current player's position and gets their ids
     * @return String
     * @param nothing
     */
    public String showAdjacentSectors() {
        String nearSectors="";
        for(Sector sector: adjacentSectors) {
            nearSectors = nearSectors.concat(" ["+sector.getCompleteId()+"]<--"+sector.getDescription()+" ");
        }
        return nearSectors;
    }
    
    /**
     * Function that sets the adjacent Sectors of a player's position
     * @return void
     * @param List of adjacent Sectors
     */
    public void setAdjacentSectors(List<Sector> adjaSectors) {
        this.adjacentSectors = adjaSectors;
    }

    /**
     * Function that checks if a player has an Item
     * @return boolean
     * @param Item item
     */
    public boolean hasItem(Item item) {
        for(Item owned:ownedItems) {
            if(owned.getType().equals(item.getType()))
                return true;
        }
        return false;
    }

    /**
     * Function that checks if a player has any Item in the ownedItems array
     * @return boolean
     */
    public boolean hasAnyItem() {
        return !ownedItems.isEmpty();
    }

    /**
     * Function that returns the ownedItems list 
     * @return List of Items
     * @param nothing
     */
    public List<Item> getItems() {
        return ownedItems;
    }
    /**
     * Function that returns the turn's starting Sector of a player 
     * @return Sector
     * @param nothing
     */
    public Sector getTurnStartingPosition() {
        return turnStartingPosition;
    }

    /**
     * Function that set the player's starting position for the current turn
     * @return void
     * @param Sector starting position
     */
    public void setTurnStartingPosition(Sector turnStartingPosition) {
        this.turnStartingPosition = turnStartingPosition;
    }

    /**
     * Function that returns the maximum amount of the ownedItems 
     * @return int
     * @param nothing
     */
    public static int getMaxOwnedItems() {
        return MAXOWNEDITEMS;
    }
    
    /**
     * Function generates a random HashCode based on player's id 
     * to be used in socket communication for identifying the player
     * @return int hascode
     * @param nothing
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((playerID == null) ? 0 : playerID.hashCode());
        return result;
    }

    /**
     * Function that overrides the equals 
     * @return boolean
     * @param nothing
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Player other = (Player) obj;
        if (playerID == null) {
            if (other.playerID != null)
                return false;
        } else if (!playerID.equals(other.playerID))
            return false;
        return true;
    }

    
}
