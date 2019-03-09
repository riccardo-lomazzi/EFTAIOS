package eftaios.model.managers;

import java.io.Serializable;

import eftaios.model.avatars.AlienPlayer;
import eftaios.model.avatars.HumanPlayer;
import eftaios.model.avatars.Player;
import eftaios.model.gamerules.Rules;

public class RulesManager implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1467402625456325720L;
    private Rules rules;

    public RulesManager(Rules rules) {
        this.rules = rules;
    }
    
     /**
      * Function that sets the rules used in the current Match
      * @param rules to be set
      * @return nothing
      */
    public void createRules(Rules rules){
        this.rules = rules;
    }
    
    /**
     * Function that returns the value of canAlienIncreaseSpeed
     * @param nothing
     * @return true if the alien can increase speed (AdvancedGameRules), 
     * false if it can't (BasicGameRules)
     */
    public boolean canAlienIncreaseSpeed(){

        return rules.canAlienIncreaseSpeed();
    }

    /**
<<<<<<< HEAD
     * Function that checks if a player can attack in his position. He can do that only
     * if he's an alien or a human with an attack card (and the rules are Advanced)
     * @param player that requests the attack
     * @return if the player can attack (so he's an alien, or he has an in item card and the items must be usable)
     * then returns true, else false
=======
     * @param player
     * @return if the player can attack (so he's an alien, or he has an in item
     *         card and the items must be usable) then returns true, else false
>>>>>>> SocketJegher
     */
    public boolean canIAttack(Player player) {
        return (rules.areItemsUsable() && player.hasAttackItem() && player instanceof HumanPlayer) || player instanceof AlienPlayer;
    }
    /**
     * Function that return the rules used
     * @param nothing
     * @return setted Rules
     */
    public Rules getRules() {
        return rules;
    }

    /**
     * Function that returns the value of areItemUsable
     * @param nothing
     * @return true if players can use items (AdvancedGameRules), 
     * false if they can't (BasicGameRules)
     */
    public boolean areItemUsable() {
        return rules.areItemsUsable();
    }

}
