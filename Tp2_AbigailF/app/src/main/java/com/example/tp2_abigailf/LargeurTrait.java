package com.example.tp2_abigailf;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class LargeurTrait extends Dialog {
    SeekBar seekBar;
    Button ok;
    TextView textView;
    Ecouteur ec;
    MainActivity mainActivity;
    public LargeurTrait(@NonNull Context context) {
        super(context);
        mainActivity = (MainActivity) context;
        seekBar = findViewById(R.id.seekBar);
        ok = findViewById(R.id.ok);
        textView = findViewById(R.id.strokeWidthText);
        //textView.setText("Stroke width : " + Integer.toString(mainActivity.pen.getWidth()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_largeur_trait);
    }
    private class Ecouteur implements View.OnClickListener{
        @Override
        public void onClick(View v){

        }
    }
}
