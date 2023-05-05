package com.example.tpfinal_abigailfournier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.ColorUtils;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    ConstraintLayout hand,gameSurface;
    TextView[] handTextViews;
    TextView higher1, higher2, lower1, lower2;
    int nCardsInHands;
    Game game;
    Ecouteur ec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();
        hand = findViewById(R.id.hand);
        gameSurface = findViewById(R.id.gameSurface);
        nCardsInHands = 8;
        handTextViews = new TextView[nCardsInHands];
        higher1 = findViewById(R.id.higher1);
        higher2 = findViewById(R.id.higher2);
        lower1 = findViewById(R.id.lower1);
        lower2 = findViewById(R.id.lower2);
        game = new Game();
        ec = new Ecouteur();
        create_initial_hand();
        format_card_stack(higher1, game.getHigher1());
        format_card_stack(higher2, game.getHigher2());
        format_card_stack(lower1, game.getLower1());
        format_card_stack(lower2, game.getLower2());
    }
    private void format_card_stack(TextView t,CardStack c){
        t.setText(Integer.toString(c.getTopCard().getNumber()));
        t.setBackgroundColor(c.getTopCard().getColor());
        t.setTextColor(c.getTopCard().getTextColor());
        t.setTextSize(45);
        t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        t.setGravity(Gravity.CENTER);
        t.setOnDragListener(ec);
    }
    private void create_initial_hand(){
        for(int i=0;i<nCardsInHands;i++){
            handTextViews[i] = (TextView) hand.getChildAt(i);
            Card card = game.getHand().getCard(i);
            handTextViews[i].setText(Integer.toString(card.getNumber()));
            handTextViews[i].setBackgroundColor(card.getColor());
            handTextViews[i].setTextColor(card.getTextColor());
            handTextViews[i].setTextSize(45);
            handTextViews[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            handTextViews[i].setGravity(Gravity.CENTER);
            handTextViews[i].setOnTouchListener(ec);
        }
    }
    private void show_hand(){
        for(int i=0;i<nCardsInHands;i++){
            Card card = game.getHand().getCard(i);
            handTextViews[i].setText(Integer.toString(card.getNumber()));
            handTextViews[i].setBackgroundColor(card.getColor());
            handTextViews[i].setTextColor(card.getTextColor());
            handTextViews[i].setVisibility(View.VISIBLE);
        }
    }
    private class Ecouteur implements View.OnDragListener, View.OnTouchListener{
        View cardView = null;
        ConstraintLayout parent = null;
        View sourceDrop = null,sourcePickUp = null;
        int cardIndex;
        @Override
        public boolean onTouch(View source, MotionEvent motionEvent) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(source);
            int sdkVersion = Build.VERSION.SDK_INT;

            if(sdkVersion <= 24){
                source.startDrag(null, shadowBuilder, source, 0);
            }
            else
                source.startDragAndDrop(null, shadowBuilder, source,0);

            source.setVisibility(View.INVISIBLE);
            sourcePickUp = source;

            return true;
        }
        @Override
        public boolean onDrag(View source, DragEvent dragEvent) {
            switch (dragEvent.getAction()){
                case DragEvent.ACTION_DRAG_ENTERED:
                    cardView = (View)dragEvent.getLocalState();
                    cardIndex = hand.indexOfChild(cardView);
                    sourceDrop = source;
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    if(cardView!=null && source == sourceDrop) {
                        Boolean placed = false;
                        Card card = game.getHand().getCard(cardIndex);

                        if ((TextView)sourceDrop == lower1)
                            placed = game.getLower1().attemptPlaceCard(card);
                        if ((TextView)sourceDrop == lower2)
                            placed = game.getLower2().attemptPlaceCard(card);
                        if ((TextView)sourceDrop == higher1)
                            placed = game.getHigher1().attemptPlaceCard(card);
                        if ((TextView)sourceDrop == higher2)
                            placed = game.getHigher2().attemptPlaceCard(card);

                        if(placed){
                            int color = card.getColor();
                            int textColor = card.getTextColor();
                            String text = (String) ((TextView) cardView).getText();
                            game.getHand().removeCard(cardIndex);
                            ((TextView) source).setBackgroundColor(color);
                            ((TextView) source).setTextColor(color);
                            ((TextView) source).setTextColor(textColor);
                            ((TextView) source).setText(text);
                            sourcePickUp.setVisibility(View.INVISIBLE);
                            sourcePickUp = null;
                            if (game.fillHand())
                                show_hand();
                        }
                    }
                    else if(sourcePickUp!=null)
                        sourcePickUp.setVisibility(View.VISIBLE);
                    break;


                case DragEvent.ACTION_DRAG_EXITED:
                    parent = null;
                    cardView = null;
                    break;
            }
            return true;
        }

    }
}
