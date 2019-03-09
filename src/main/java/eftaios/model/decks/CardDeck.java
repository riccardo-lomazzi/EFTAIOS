package eftaios.model.decks;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import eftaios.model.decks.drawables.Card;

public abstract class CardDeck implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -2977158094015703159L;
    protected List<Card> deck;

    /**
     * Function that extracts a card from the selected deck
     * if the deck is empty, it creates it
     * finally it returns the card in the first position
     * @return Item/Card to be removed
     * @param nothing
     */
    public Card drawCard(){
       if((deck==null)||(deck.isEmpty())) 
       createDeck();
    return deck.remove(0);
    }

    protected void addCard(Card card) {
        
       deck.add(card);
    }

    protected void shuffleDeck(List<Card> deck,Random randomSeed){
       for(int i = deck.size()-1;i>0;i--){
       Collections.swap(deck, randomSeed.nextInt(i+1), i);
       }
    }

    protected abstract void createDeck();
    
}
