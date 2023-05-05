package com.example.tpfinal_abigailfournier;

import android.graphics.Color;

import androidx.core.graphics.ColorUtils;

public class Card {
    private int number;
    private int color,textColor;
    public Card(int number){
        this.number = number;
        this.color = generateColor();
        this.textColor = generateTextColor();
    }
    private int generateColor(){
        float hueFactor = (float)(number / 97.0)*360;
        float[] hsl= new float[3];
        ColorUtils.colorToHSL(Color.RED,hsl);
        hsl[0]=hueFactor;
        hsl[1] = (float)1;
        hsl[2] = (float)0.45;
        return ColorUtils.HSLToColor(hsl);
    }
    private int generateTextColor(){
        float hueFactor = (float)(((number / 97.0)*360+180)%360);
        float[] hsl= new float[3];
        ColorUtils.colorToHSL(Color.RED,hsl);
        hsl[0]=hueFactor;
        hsl[1] = (float)1.0;
        hsl[2]= (float)0.45;
        return ColorUtils.HSLToColor(hsl);
    }

    public int getNumber() {return number;}

    public int getColor() {return color;}

    public int getTextColor() {return textColor;}
}
