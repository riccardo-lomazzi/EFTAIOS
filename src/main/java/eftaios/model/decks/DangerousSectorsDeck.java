package eftaios.model.decks;

import java.util.ArrayList;
import java.util.Random;

import eftaios.model.decks.drawables.Card;
import eftaios.model.decks.drawables.NoiseInAnySectorCard;
import eftaios.model.decks.drawables.NoiseInYourSectorCard;
import eftaios.model.decks.drawables.SilenceCard;

public class DangerousSectorsDeck extends CardDeck {

    /**
     * 
     */
    private static final long serialVersionUID = -4840227039969459416L;
    private static final int CARDWITHOBJECT = 4;
    private static final int NOISECARDNUM = 10;
    private static final int DANGEROUSDECKSIZE = 25;
    private static final int SILENCECARDNUM = DANGEROUSDECKSIZE - NOISECARDNUM;

    public DangerousSectorsDeck() {
    createDeck();
    }

    @Override
    protected void createDeck() {
      deck=new ArrayList<Card>(DANGEROUSDECKSIZE);
      boolean hasItem=true;
      for(int i = 0; i<NOISECARDNUM;i++){
      if(i<CARDWITHOBJECT)
        hasItem=true;
      else 
        hasItem=false;
      addCard(new NoiseInYourSectorCard(hasItem));
      addCard(new NoiseInAnySectorCard(hasItem));
      }
      for(int i = 0; i<SILENCECARDNUM;i++)
      addCard(new SilenceCard(false));
      //randomizing deck
      shuffleDeck(deck,new Random());
    }

}
