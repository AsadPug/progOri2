package com.example.tpfinal_abigailfournier;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    public static final int nCardsAtStart = 97;
    private ArrayList<Card> deck;
    private int nCards = this.nCardsAtStart;
    public Deck(){
        this.deck = new ArrayList<Card>(this.nCardsAtStart);
        this.createShuffledDeck();

    }
    private void createShuffledDeck(){
        for(int i = 1;i<=nCards;i++){
            this.deck.add(new Card(i));
        }
        Collections.shuffle(this.deck);
    }
    public Card pickRandomCard(){
        Card randCard = this.deck.get(nCards-1);
        this.deck.remove(nCards-1);
        this.nCards--;
        return randCard;
    }

    public int getNCards() {return nCards;}
}
