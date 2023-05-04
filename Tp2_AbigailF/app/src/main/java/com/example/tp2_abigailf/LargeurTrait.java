package com.example.tp2_abigailf;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class LargeurTrait extends Dialog {
    SeekBar seekBar;
    Button ok;
    TextView textView;
    String strokeWidthText;
    ConstraintLayout container;
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

        seekBar = findViewById(R.id.seekBar);
        ok = findViewById(R.id.ok);
        textView = findViewById(R.id.strokeWidthText);
        container = findViewById(R.id.container);
        ec = new Ecouteur();

        strokeWidthText = ("Stroke width : ".concat(Integer.toString(mainActivity.pen.getWidth())));
        textView.setText(strokeWidthText);
        seekBar.setProgress(mainActivity.pen.getWidth());

        ok.setOnClickListener(ec);
        seekBar.setOnSeekBarChangeListener(ec);
    }
    private class Ecouteur implements View.OnClickListener,SeekBar.OnSeekBarChangeListener{
        @Override

        public void onClick(View v){
            if (v==ok){
                mainActivity.pen.setWidth(seekBar.getProgress());
                mainActivity.largeurTraitActivity.hide();
            }

        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
            strokeWidthText = ("Stroke width : ".concat(Integer.toString(seekBar.getProgress())));
            textView.setText(strokeWidthText);
        }
    }
}
