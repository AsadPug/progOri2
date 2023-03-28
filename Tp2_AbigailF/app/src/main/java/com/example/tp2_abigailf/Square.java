package com.example.tp2_abigailf;

public class Square{
    private float x1,x2,y1,y2;

    public Square(float x,float y){
        this.x1 = x;
        this.y1 = y;
    }

    public void setSecondPoint(float x, float y){
        this.x2 = x;
        this.y2 = y;
    }

    public float getX1() {
        float x = this.x1;
        if(this.x1>this.x2){
            x = this.x2;
        }
        return x;
    }
    public float getX2() {
        float x = this.x2;
        if(this.x1>this.x2){
            x = this.x1;
        }
        return x;
    }

    public float getY1() {
        float y = this.y1;
        if(this.y1>this.y2){
            y = this.y2;
        }
        return y;
    }
    public float getY2() {
        float y = this.y2;
        if(this.y1>this.y2){
            y = this.y1;
        }
        return y;
    }
}
