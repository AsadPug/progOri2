package com.example.tp2_abigailf;

public class Pen {
    private float x,y,width;
    public Pen(float width){
        this.x=0;
        this.y=0;
        this.width=width;
    }

    public float getWidth() {return width;}
    public float getX() {return x;}
    public float getY() {return y;}

    public void setWidth(float width) {this.width = width;}
    public void setX(float x) {this.x = x;}
    public void setY(float y) {this.y = y;}
}
