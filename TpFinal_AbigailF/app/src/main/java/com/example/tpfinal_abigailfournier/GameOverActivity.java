package com.example.tpfinal_abigailfournier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class GameOverActivity extends AppCompatActivity {
    LinearLayout leaderboard;
    DatabaseHelper databaseHelper;
    TextView menu;
    Ecouteur ec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        getSupportActionBar().hide();
        databaseHelper = DatabaseHelper.getInstance(this);
        leaderboard = findViewById(R.id.leaderboard);
        menu = findViewById(R.id.menu);

        ec = new Ecouteur();

        showLeaderboard();
        menu.setOnClickListener(ec);
    }
    private void showLeaderboard(){
        ArrayList<Integer> scores = databaseHelper.getScores(databaseHelper.getReadableDatabase());
        for(int i = 0;i<scores.size();i++){
            TextView score = new TextView(this);
            score.setTextColor(Color.BLACK);
            score.setHeight(75);
            score.setTextSize(20);
            score.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            score.setText(Integer.toString(scores.get(i)) + " pts");
            leaderboard.addView(score);
        }
        databaseHelper.close();
    }
    private class Ecouteur implements  View.OnClickListener {
        @Override
        public void onClick(View view) {
            //retourne au menu principale pour recommencer une partie si voulu
            if(view == (View)menu){
                Intent menuIntent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(menuIntent);
            }
        }
    }
}