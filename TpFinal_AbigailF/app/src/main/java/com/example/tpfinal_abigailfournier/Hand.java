package com.example.tpfinal_abigailfournier;

import androidx.dynamicanimation.animation.SpringAnimation;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand;
    private int nCardsMax;
    public Hand(){
        this.nCardsMax = 8;
        this.hand = new ArrayList<Card>(this.nCardsMax);
    }

    public void addCard(Card card, int pos){
        this.hand.add(pos, card);
    }
    public void setCard(Card card, int pos){
        this.hand.set(pos, card);
    }
    public void removeCard(int pos){
        this.hand.set(pos,null);
    }
    public boolean isTwoMissing(){
        int counter = 0;
        boolean twoMissing = false;
        for(int i=0;i<this.hand.size();i++){
            if(this.hand.get(i)==null)counter++;
        }
        if(counter>=2)twoMissing = true;
        return twoMissing;
    }
    public Card getCard(int pos) {return this.hand.get(pos);}
    public int getnCardsMax() {return nCardsMax;}
}
