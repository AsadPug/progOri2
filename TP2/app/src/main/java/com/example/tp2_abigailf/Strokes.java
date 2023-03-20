package com.example.tp2_abigailf;

import android.graphics.Path;
import android.util.Log;

public class Strokes {
    private Stroke[] strokes;
    private int strokeCount;
    public Strokes(int strokeCount){
        this.strokeCount = strokeCount;
        this.strokes = new Stroke[this.strokeCount];
    }
    public void addStroke(Stroke s){
        this.strokeCount+=1;
        Stroke[] oldStrokes = this.strokes;
        this.strokes = new Stroke[this.strokeCount];
        for(int i=0;i<oldStrokes.length;i++){
            this.strokes[i]= oldStrokes[i];
        }
        this.strokes[strokeCount-1] = s;
    }

    public int getStrokeCount() {
        return strokeCount;
    }

    public Stroke getStrokeAtId(int id){
        return this.strokes[id];
    }
}
