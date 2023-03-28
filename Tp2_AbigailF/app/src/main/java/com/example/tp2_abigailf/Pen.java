package com.example.tp2_abigailf;

public class Pen {
    private float x,y;
    private int width;
    public Pen(int width){
        this.x=0;
        this.y=0;
        this.width=width;
    }

    public int getWidth() {return width;}
    public float getX() {return x;}
    public float getY() {return y;}

    public void setWidth(int width) {this.width = width;}
    public void setX(float x) {this.x = x;}
    public void setY(float y) {this.y = y;}
}
