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

    public Card getTopCard(){
        return this.stack.get(this.stack.size()-1);
    }
    private boolean isValidCard(Card card){
        boolean isValid = false;
        if(card.getNumber()<this.getTopCard().getNumber() && this.lower){
            isValid = true;
        }
        if(card.getNumber()>this.getTopCard().getNumber() && this.lower == false){
            isValid = true;
        }
        return isValid;
    }
    public boolean attemptPlaceCard(Card card){
        boolean placed = this.isValidCard(card);
        if(placed)
            this.stack.add(card);
        return placed;
    }
}
