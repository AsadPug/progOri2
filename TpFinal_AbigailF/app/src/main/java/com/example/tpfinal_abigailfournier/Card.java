package com.example.tpfinal_abigailfournier;

import android.graphics.Color;

import androidx.core.graphics.ColorUtils;

public class Card {
    private int number;
    private int color,textColor;
    public Card(int number){
        this.number = number;
        //formule qui génere un nombre de 1 à 360 qui représenter le hue des cartes
        //Quand le nombre est 97 -> hue est 360
        float cardHue = (float)(number / 97.0)*360;
        //génere un hue opposé pour le text, carte bleu -> text orange
        float textHue = (cardHue+180)%360;
        this.color = generateColor(cardHue);
        this.textColor = generateColor(textHue);
    }
    private int generateColor(float hueFactor){
        float[] hsl= new float[3];
        ColorUtils.colorToHSL(Color.RED,hsl);
        hsl[0]=hueFactor;
        hsl[1] = (float)1;
        hsl[2] = (float)0.45;
        return ColorUtils.HSLToColor(hsl);
    }

    public int getNumber() {return number;}

    public int getColor() {return color;}

    public int getTextColor() {return textColor;}
}
