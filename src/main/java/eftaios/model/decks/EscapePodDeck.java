package eftaios.model.decks;

import java.util.ArrayList;
import java.util.Random;

import eftaios.model.decks.drawables.Card;
import eftaios.model.decks.drawables.GreenEscapePodCard;
import eftaios.model.decks.drawables.RedEscapePodCard;

public class EscapePodDeck extends CardDeck {

    /**
     * 
     */
    private static final long serialVersionUID = -6186965990373329250L;

    public EscapePodDeck() {
    createDeck();
    }

    @Override
    protected void createDeck() {
       deck=new ArrayList<Card>(6);
       boolean hasItem=false;
       //Deck creation
       for(int i = 0; i<3;i++){
         addCard(new GreenEscapePodCard(hasItem));
         addCard(new RedEscapePodCard(hasItem));
       }
       //randomizing deck
       shuffleDeck(deck,new Random());
    }

}
