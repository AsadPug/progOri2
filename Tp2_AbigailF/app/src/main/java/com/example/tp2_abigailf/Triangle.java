package com.example.tp2_abigailf;

public class Triangle {
    private float x1,x2,y1,y2,x3,y3;

    public Triangle(float x,float y){
        this.x1 = x;
        this.y1 = y;
    }

    public void setSecondPoint(float x, float y){
        this.x2 = x;
        this.y2 = y;
    }
    public void setThirdPoint(float x,float y){
        this.x3 = x;
        this.y3 = y;
    }

    public float getX1() {
        return x1;
    }
    public float getX2() {
        return x2;
    }
    public float getX3() { return x3; }

    public float getY1() {
        return y1;
    }
    public float getY2() {
        return y2;
    }
    public float getY3() { return y3; }


}
