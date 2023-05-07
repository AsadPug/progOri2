package com.example.tpfinal_abigailfournier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    Button play;
    TextView menuTitle, highscore;
    Ecouteur ec;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        play = findViewById(R.id.play);
        menuTitle = findViewById(R.id.menuTitle);
        highscore = findViewById(R.id.highscore);
        ec = new Ecouteur();
        databaseHelper = DatabaseHelper.getInstance(this);
        loadHighscore();
        play.setOnClickListener(ec);
    }
    private void loadHighscore(){
        int bestScore = databaseHelper.getBestScore(databaseHelper.getReadableDatabase());
        highscore.setText("HIGHSCORE : " + Integer.toString(bestScore));
        databaseHelper.close();
    }
    private class Ecouteur implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if(v.equals(play)){
                Intent gameIntent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(gameIntent);
            }
        }
    };
}