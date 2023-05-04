package com.example.tpfinal_abigailfournier;

import java.util.ArrayList;

public class Hand {
    ArrayList<Card> hand;
    int nCardsMax;
    public Hand(){
        this.nCardsMax = 8;
        this.hand = new ArrayList<Card>(this.nCardsMax);
    }
    public void addCard(Card card, int pos){
        this.hand.add(pos, card);
    }
    public void removeCard(int pos){
        this.hand.set(pos,null);
    }
    public Card getCard(int pos) {
        return this.hand.get(pos);
    }

}
