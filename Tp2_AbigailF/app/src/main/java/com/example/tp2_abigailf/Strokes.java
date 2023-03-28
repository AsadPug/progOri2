package com.example.tp2_abigailf;

import android.graphics.Path;
import android.util.Log;

public class Strokes {
    private Stroke[] strokes,strokeHistory;
    private int strokeCount,historyCount;
    private int capacity;
    public Strokes(){
        this.strokeCount = 0;
        this.capacity = 2;
        this.strokes = new Stroke[this.capacity];
    }
    public void addStroke(Stroke s){
        this.strokeCount+=1;
        this.strokeHistory = null;
        this.historyCount = 0;

        if (this.strokeCount > this.capacity) {
            this.capacity *= 1.618;
            Stroke[] oldStrokes = this.strokes;
            this.strokes = new Stroke[this.capacity];
            for(int i=0;i<oldStrokes.length;i++){
                this.strokes[i]= oldStrokes[i];
            }
        }

        this.strokes[this.strokeCount-1] = s;
    }
    public void removeLastStroke(){
        this.strokeCount -=1;
        this.strokes[this.strokeCount] = null;
    }
    public void undo(){
        if(strokeCount>0){
            if(this.strokeHistory == null) {
                this.strokeHistory = new Stroke[this.strokeCount];
                this.historyCount = 0;
            }
            this.historyCount+=1;
            this.strokeHistory[this.historyCount-1] = this.strokes[this.strokeCount-1];
            this.removeLastStroke();
        }
    }
    public void redo(){
        if(historyCount>0){
            this.strokeCount +=1;
            this.strokes[this.strokeCount-1] = this.strokeHistory[this.historyCount-1];
            this.historyCount-=1;
        }
    }

    public int getStrokeCount() {
        return strokeCount;
    }


    public Stroke[] getStrokes() { return strokes; }
    public Stroke at(int id){
        return this.strokes[id];
    }
}
