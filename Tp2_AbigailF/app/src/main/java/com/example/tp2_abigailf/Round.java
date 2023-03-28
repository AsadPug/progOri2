package com.example.tp2_abigailf;

public class Round {
    private float x1,x2,y1,y2;

    public Round(float x,float y){
        this.x1 = x;
        this.y1 = y;
    }
    public void setSecondPoint(float x, float y){
        this.x2 = x;
        this.y2 = y;
    }
    public float getX1() {
        return x1;
    }
    public float getY1() { return y1; }
    public float getRayon(){ return (float)Math.sqrt((Math.pow(this.x1-this.x2,2)+Math.pow(this.y1-this.y2,2))); }
}
