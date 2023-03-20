package com.example.tp2_abigailf;

import java.text.FieldPosition;

public class Shape {
    private float x1,x2,y1,y2;

    public Shape(float x,float y){
        this.x1 = x;
        this.y1 = y;
    }

    public void setEndPoint(float x,float y){
        this.x2 = x;
        this.y2 = y;
    }

    public float getX1() {
        return x1;
    }

    public float getX2() {
        return x2;
    }

    public float getY1() {
        return y1;
    }
    public float getY2() {
        return y2;
    }
    public float getRayon(){
        return (float)Math.sqrt((Math.pow(this.x1-this.x2,2)+Math.pow(this.y1-this.y2,2)));
    }
}
