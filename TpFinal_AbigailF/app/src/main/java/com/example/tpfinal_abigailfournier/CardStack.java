package com.example.tpfinal_abigailfournier;

import java.util.ArrayList;

public class CardStack {
    private ArrayList<Card> stack;
    private boolean lower;
    public CardStack(boolean lower){
        this.stack = new ArrayList<Card>();
        this.lower = lower;
        if(lower)this.stack.add(new Card(98));
        else this.stack.add(new Card(0));
    }
    public boolean isValidCard(Card card){
        boolean isValid = false;
        int nCard = card.getNumber();
        int nCardStack = this.getTopCard().getNumber();
        if((nCard < nCardStack||nCard - nCardStack == 10) && this.lower){
            isValid = true;
        }
        if((nCard > nCardStack || nCardStack - nCard == 10) && !this.lower){
            isValid = true;
        }
        return isValid;
    }
    public boolean attemptPlaceCard(Card card){
        boolean placeValid = this.isValidCard(card);
        if(placeValid)
            this.stack.add(card);
        return placeValid;
    }
    public Card getTopCard(){
        return this.stack.get(this.stack.size()-1);
    }
    public Card getSecondTopCard(){
        return this.stack.get(this.stack.size()-2);
    }
    public void removeTopCard(){
        this.stack.remove(this.stack.size()-1);
    }
}
