package com.example.tp2_abigailf;

import android.graphics.Path;
import android.util.Log;

public class Strokes {
    private Stroke[] strokes;
    private int strokeCount;
    private int capacity;
    public Strokes(){
        this.strokeCount = 0;
        this.capacity = 2;
        this.strokes = new Stroke[this.capacity];
    }
    public void addStroke(Stroke s){
        this.strokeCount+=1;
        if (this.strokeCount > this.capacity) {
            this.capacity *= 1.618;
            Stroke[] oldStrokes = this.strokes;
            this.strokes = new Stroke[this.capacity];
            for(int i=0;i<oldStrokes.length;i++){
                this.strokes[i]= oldStrokes[i];
            }
        }
        this.strokes[strokeCount-1] = s;
    }

    public int getStrokeCount() {
        return strokeCount;
    }


    public Stroke[] getStrokes() { return strokes; }
    public Stroke at(int id){
        return this.strokes[id];
    }
}
