package com.example.tpfinal_abigailfournier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    //Déclaration de variables
    ConstraintLayout hand,gameSurface;
    TextView[] handTextViews;
    TextView higher1, higher2, lower1, lower2,score,cardsLeft;
    Chronometer chronometer;
    ImageView undo;
    Game game;
    Ecouteur ec;
    long startTime;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();
        //Initiation de variables
        startTime = SystemClock.elapsedRealtime();

        game = new Game();
        game.setLastPlaceTime(startTime);

        hand = findViewById(R.id.hand);
        gameSurface = findViewById(R.id.gameSurface);
        handTextViews = new TextView[game.getHand().getnCardsMax()];
        higher1 = findViewById(R.id.higher1);
        higher2 = findViewById(R.id.higher2);
        lower1 = findViewById(R.id.lower1);
        lower2 = findViewById(R.id.lower2);
        undo = findViewById(R.id.undo);
        chronometer = findViewById(R.id.time);
        score = findViewById(R.id.score);
        cardsLeft = findViewById(R.id.cardsLeft);

        databaseHelper = DatabaseHelper.getInstance(this);

        ec = new Ecouteur();

        //formatage de Views
        updateScore();
        updateCardsLeft();
        startChronometer();
        createInitialHand();
        formatCardStacks();

        //bouton undo est initialement invisible
        undo.setVisibility(View.INVISIBLE);
        undo.setOnClickListener(ec);
    }
    private void updateCardsLeft(){
        cardsLeft.setText("Cards left : "+Integer.toString(game.getDeck().getNCards()));
    }
    private void updateScore() {
        score.setText("Score : "+ Integer.toString(game.getScore()));
    }

    private void startChronometer(){
        chronometer.setBase(startTime);
        chronometer.start();
    }
    private void showCardStack(TextView t, CardStack c){
        t.setText(Integer.toString(c.getTopCard().getNumber()));
        t.setBackgroundColor(c.getTopCard().getColor());
        t.setTextColor(c.getTopCard().getTextColor());
    }
    private void formatCardStacks(){
        formatCardStack(higher1, game.getHigher1());
        formatCardStack(higher2, game.getHigher2());
        formatCardStack(lower1, game.getLower1());
        formatCardStack(lower2, game.getLower2());
    }
    private void formatCardStack(TextView t, CardStack c){
        t.setText(Integer.toString(c.getTopCard().getNumber()));
        t.setBackgroundColor(c.getTopCard().getColor());
        t.setTextColor(c.getTopCard().getTextColor());
        t.setTextSize(45);
        t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        t.setGravity(Gravity.CENTER);
        t.setOnDragListener(ec);
    }
    private void createInitialHand(){
        for(int i=0;i<game.getHand().getnCardsMax();i++){
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
    private void showHand(){
        for(int i=0;i<game.getHand().getnCardsMax();i++){
            Card card = game.getHand().getCard(i);
            handTextViews[i].setText(Integer.toString(card.getNumber()));
            handTextViews[i].setBackgroundColor(card.getColor());
            handTextViews[i].setTextColor(card.getTextColor());
            handTextViews[i].setVisibility(View.VISIBLE);
        }
    }
    private void gameOver(){
        Intent gameOverIntent = new Intent(getApplicationContext(), GameOverActivity.class);
        startActivity(gameOverIntent);
        databaseHelper.addScore(game.getScore(), databaseHelper.getWritableDatabase());
        databaseHelper.close();
    }
    private class Ecouteur implements View.OnDragListener, View.OnTouchListener, View.OnClickListener{
        View cardView = null;
        ConstraintLayout parent = null;
        View sourcePickUp = null;
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
                    //Garde en cache l'information de la carte selectionner ainsi que le View entré en dernier
                    cardView = (View)dragEvent.getLocalState();
                    cardIndex = hand.indexOfChild(cardView);
                    break;

                case DragEvent.ACTION_DROP:
                    Boolean placed = false;
                    Card card = game.getHand().getCard(cardIndex);
                    //Garde le nom du stack concerné pour pouvoir update le score apres
                    //que la carte soit placé.
                    String stackName = null;

                    //Test le placement de la carte pour le stack concerné. Si le mouvement
                    //est valide la carte se place sur le stack.
                    if ((TextView)source == lower1){
                        stackName = "lower1";
                        game.setLastMove(stackName);
                        placed = game.getLower1().attemptPlaceCard(card);
                    }
                    if ((TextView)source == lower2){
                        stackName = "lower2";
                        game.setLastMove(stackName);
                        placed = game.getLower2().attemptPlaceCard(card);
                    }
                    if ((TextView)source == higher1){
                        stackName = "higher1";
                        placed = game.getHigher1().attemptPlaceCard(card);
                        game.setLastMove(stackName);
                    }
                    if ((TextView)source == higher2) {
                        stackName = "higher2";
                        placed = game.getHigher2().attemptPlaceCard(card);
                        game.setLastMove(stackName);
                    }
                    if(placed){
                        //Quand le placement est valide
                        game.getHand().placeCard(cardIndex);
                        game.updateScore(stackName);
                        updateScore();

                        int color = card.getColor();
                        int textColor = card.getTextColor();
                        String text = (String) ((TextView) cardView).getText();
                        ((TextView) source).setBackgroundColor(color);
                        ((TextView) source).setTextColor(color);
                        ((TextView) source).setTextColor(textColor);
                        ((TextView) source).setText(text);
                        sourcePickUp.setVisibility(View.INVISIBLE);
                        sourcePickUp = null;
                        if (game.fillHand()){
                            undo.setVisibility(View.INVISIBLE);
                            updateCardsLeft();
                            showHand();
                        }
                        else
                            undo.setVisibility(View.VISIBLE);
                        if(game.isGameOver()) {
                            gameOver();
                        }

                    }

                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    if(sourcePickUp!=null)
                        sourcePickUp.setVisibility(View.VISIBLE);
                case DragEvent.ACTION_DRAG_EXITED:
                    parent = null;
                    cardView = null;
                    break;
            }
            return true;
        }
        @Override
        public void onClick(View view){
            if(view == (View)undo){
                boolean isUndoValid = game.undo();
                if(isUndoValid){
                    switch(game.getLastMove()){
                        case "lower1":
                            showCardStack(lower1, game.getLower1());
                            break;
                        case "lower2":
                            showCardStack(lower2, game.getLower2());
                            break;
                        case "higher1":
                            showCardStack(higher1, game.getHigher1());
                            break;
                        case "higher2":
                            showCardStack(higher2, game.getHigher2());
                            break;
                    }
                    game.setLastMove(null);
                    game.getHand().setLastPlacedCard(null);
                    game.setScore(game.getLastScore());
                    updateScore();
                    undo.setVisibility(View.INVISIBLE);
                    showHand();
                }
            }
        }

    }
}
