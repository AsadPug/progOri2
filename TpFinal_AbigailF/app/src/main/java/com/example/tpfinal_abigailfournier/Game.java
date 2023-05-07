package com.example.tpfinal_abigailfournier;

import android.os.SystemClock;

public class Game {
    private Deck deck;
    private Hand hand;
    private CardStack higher1,higher2,lower1,lower2;
    private String lastMove;
    private long lastPlaceTime;
    private int score,lastScore;
    public Game(){
        this.deck = new Deck();
        this.hand = new Hand();
        this.higher1 = new CardStack(false);
        this.higher2 = new CardStack(false);
        this.lower1 = new CardStack(true);
        this.lower2 = new CardStack(true);
        this.lastMove = null;
        this.score = 0;
        this.lastScore = 0;
        this.pick_initial_hand();
    }
    private void pick_initial_hand() {
        for (int i = 0; i < this.hand.getnCardsMax(); i++) {
            this.hand.addCard(deck.pickRandomCard(), i);
        }
    }
    public boolean fillHand(){
        boolean fillingHand = false;
        if(this.hand.isTwoMissing()){
            this.lastMove = null;
            this.hand.setLastPlacedCard(null);
            for(int i=0;i<this.hand.getnCardsMax();i++){
                if(this.hand.getCard(i)==null)
                    this.hand.setCard(deck.pickRandomCard(),i);
            }
            fillingHand = true;
        }
        return fillingHand;
    }
    public void updateScore(String stackName){
        Card c1,c2;
        CardStack cs = this.getCardStack(stackName);
        c1 = cs.getTopCard();
        c2 = cs.getSecondTopCard();
        int nCardsPlaced = Deck.nCardsAtStart - this.deck.getNCards()-this.hand.getNCards();
        long placeTime = SystemClock.elapsedRealtime();
        double nSecSinceLastPlace = (placeTime- this.lastPlaceTime)/1000;
        double worstNSec = 10;
        if (nSecSinceLastPlace > worstNSec)
            nSecSinceLastPlace = worstNSec;
        //nSecMultiplier : valeure de 1 à 2, 1 étant 10 secondes ou plus et 2 étant 0 secondes
        double nSecMultiplier = -(nSecSinceLastPlace / worstNSec)+2.0;

        int distanceBetweenNumber = Math.abs(c1.getNumber() - c2.getNumber());
        int worstDistance = 20;
        if( distanceBetweenNumber>worstDistance)
            distanceBetweenNumber = worstDistance;
        //nSecMultiplier : valeure de 1 à 2, 1 étant 20 nombres ou plus et 2 étant 0 nombres de différence
        double distanceMultiplier = -(distanceBetweenNumber / worstDistance)+2.0;

        this.lastScore = this.score;
        this.lastPlaceTime = placeTime;
        this.score += (nCardsPlaced*100) * nSecMultiplier * distanceMultiplier;
        System.out.println(nCardsPlaced);
        System.out.println(nSecMultiplier);
        System.out.println(distanceMultiplier);
    }

    public boolean undo(){
        boolean undoValid = (this.lastMove !=null && this.hand.getLastPlacedCard() != null);
        if(undoValid){

            this.getCardStack(this.lastMove).removeTopCard();

            for(int i=0;i<this.hand.getnCardsMax();i++){
                if(this.hand.getCard(i)==null){
                    this.hand.setCard(this.hand.getLastPlacedCard(),i);
                    break;
                }
            }
        }
        return undoValid;
    }
    public Hand getHand() {return hand;}

    public Deck getDeck() {return deck;}

    public CardStack getCardStack(String stackName){
        CardStack s = null;
        switch(stackName){
            case "lower1":
                s = this.getLower1();
                break;
            case "lower2":
                s = this.getLower2();
                break;
            case "higher1":
                s = this.getHigher1();
                break;
            case "higher2":
                s = this.getHigher2();
                break;
        }
        return s;
    }

    public CardStack getHigher1() {return higher1;}

    public CardStack getHigher2() {return higher2;}

    public CardStack getLower1() {return lower1;}

    public CardStack getLower2() {return lower2;}

    public String getLastMove() {return lastMove;}
    public int getScore() {return score;}
    public int getLastScore() {return lastScore;}
    public void setScore(int score) {this.score = score;}

    public void setLastMove(String lastMove) {this.lastMove = lastMove;}

    public void setLastPlaceTime(long lastPlaceTime) {this.lastPlaceTime = lastPlaceTime;}
}
