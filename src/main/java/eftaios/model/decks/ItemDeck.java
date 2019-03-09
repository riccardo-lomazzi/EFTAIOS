package eftaios.model.decks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import eftaios.model.decks.drawables.AdrenalineItem;
import eftaios.model.decks.drawables.AttackItem;
import eftaios.model.decks.drawables.DefenseItem;
import eftaios.model.decks.drawables.Item;
import eftaios.model.decks.drawables.SedativeItem;
import eftaios.model.decks.drawables.SpotLightItem;
import eftaios.model.decks.drawables.TeleportItem;

public class ItemDeck implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -1369341286022545750L;
    private static final int ITEMDECKSIZE = 12;
    private List<Item>deck;
    private List<Item>discarded;

    public ItemDeck(){
    createDeck();
    createDiscarededDeck();
    }

    private void createDeck(){
       deck=new ArrayList<Item>(ITEMDECKSIZE);
       //Item deck creation
       addItem(new DefenseItem());
       addItem(new SedativeItem());
       for(int i = 0 ;i<2; i++){
         addItem(new AttackItem());
         addItem(new AdrenalineItem());
         addItem(new TeleportItem());
         addItem(new SpotLightItem());
         addItem(new SedativeItem());
       }
       //randomizing deck
       shuffleDeck(deck,new Random());
    }

    private void createDiscarededDeck(){
    discarded=new ArrayList<Item>(ITEMDECKSIZE);
    }

    private void addItem(Item item){
    deck.add(item);
    }

    private void shuffleDeck(List<Item> deck,Random randomSeed){
       for(int i = deck.size()-1;i>0;i--){
       Collections.swap(deck, randomSeed.nextInt(i+1), i);
       }
    }

    /**
     * Function that returns an item
     * If the itemDeck doesn't exists it creates one by calling the createDeck method
     * If it's not empty, it removes the first of the stack (LIFO)
     * else the itemDeck is empty, it's filled with discarded cards
     * and then first one is removed
     * @return Item drawed
     * @param nothing
     */
    public Item drawItem(){
        
     if(deck==null)
       createDeck();
     
     if(!deck.isEmpty())
        return deck.remove(0);
     else 
       //when the main itemDeck is empty it is filled with discarded cards
       if((discarded!=null)||(!discarded.isEmpty())){
           fillMainDeck();
           return deck.remove(0);
       }
    return null;
    }

    private void fillMainDeck() {
        for(Item item:discarded){
            deck.add(discarded.remove(discarded.indexOf(item)));
        }
    }

    /**
     * Function that discards an  Item by putting it into the discardedItems array
     * and shuffles it
     * @return void
     * @param discardedItem
     */
    public void discardItem(Item item){
    //when a player discard or uses an item it's added to the discarded deck
    discarded.add(item);
    shuffleDeck(discarded,new Random());
    }
}
