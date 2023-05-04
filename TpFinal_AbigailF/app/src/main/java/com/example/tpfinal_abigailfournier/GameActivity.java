package com.example.tpfinal_abigailfournier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    Deck deck;
    Hand hand;
    ConstraintLayout handConstraintLayout;
    TextView[] handTextViews;
    int nCardsInHands;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();
        handConstraintLayout = findViewById(R.id.hand);
        nCardsInHands = 8;
        handTextViews = new TextView[nCardsInHands];
        deck = new Deck();
        hand = new Hand();
        create_initial_hand();
    }
    private void create_initial_hand(){
        for(int i=0;i<nCardsInHands;i++){
            handTextViews[i] = (TextView) handConstraintLayout.getChildAt(i);
            Card card = deck.pick_random_card();
            hand.addCard(card, i);
            handTextViews[i].setText(Integer.toString(card.getNumber()));
            handTextViews[i].setBackgroundColor(card.getColor());
        }
    }
}