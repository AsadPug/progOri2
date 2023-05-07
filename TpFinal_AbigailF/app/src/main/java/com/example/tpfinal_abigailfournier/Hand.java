package com.example.tpfinal_abigailfournier;

import androidx.dynamicanimation.animation.SpringAnimation;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand;
    private int nCardsMax;
    private Card lastPlacedCard;
    public Hand(){
        this.nCardsMax = 8;
        this.hand = new ArrayList<Card>(this.nCardsMax);
        this.lastPlacedCard = null;
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

    public void placeCard(int pos){
        this.lastPlacedCard = this.getCard(pos);
        this.removeCard(pos);
    }

    public boolean isTwoMissing(){return (this.nCardsMax-this.getNCards()>=2);}
    public Card getCard(int pos) {return this.hand.get(pos);}
    public int getNCards(){
        int counter = 0;
        for(int i=0;i<this.hand.size();i++){
            if(this.hand.get(i)!=null)counter++;
        }
        return counter;
    }

    public Card getLastPlacedCard() {return lastPlacedCard;}

    public void setLastPlacedCard(Card lastPlacedCard) {this.lastPlacedCard = lastPlacedCard;}

    public int getnCardsMax() {return nCardsMax;}
}
