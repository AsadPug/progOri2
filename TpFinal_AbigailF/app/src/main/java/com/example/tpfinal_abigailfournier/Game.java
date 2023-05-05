package com.example.tpfinal_abigailfournier;

public class Game {
    private Deck deck;
    private Hand hand;
    private CardStack higher1,higher2,lower1,lower2;

    public Game(){
        this.deck = new Deck();
        this.hand = new Hand();
        this.higher1 = new CardStack(false);
        this.higher2 = new CardStack(false);
        this.lower1 = new CardStack(true);
        this.lower2 = new CardStack(true);

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
            for(int i=0;i<this.hand.getnCardsMax();i++){
                if(this.hand.getCard(i)==null)
                    this.hand.setCard(deck.pickRandomCard(),i);
            }
            fillingHand = true;
        }
        return fillingHand;
    }
    public Hand getHand() {return hand;}

    public Deck getDeck() {return deck;}

    public CardStack getHigher1() {return higher1;}

    public CardStack getHigher2() {return higher2;}

    public CardStack getLower1() {return lower1;}

    public CardStack getLower2() {return lower2;}
}
