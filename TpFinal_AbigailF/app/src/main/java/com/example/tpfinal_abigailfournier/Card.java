package com.example.tpfinal_abigailfournier;

import android.graphics.Color;

public class Card {
    private int number;
    private int color;
    public Card(int number){
        this.number = number;
        this.color = Color.RED;
    }

    public int getNumber() {return number;}

    public int getColor() {return color;}
}
