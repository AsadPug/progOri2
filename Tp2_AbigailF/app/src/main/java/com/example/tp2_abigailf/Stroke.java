package com.example.tp2_abigailf;

import android.graphics.Path;

public class Stroke {
    private Path path;
    private int color;
    private boolean isEraser;
    public Stroke (Path path,int color,boolean isEraser){
        this.path = path;
        this.color = color;
        this.isEraser = isEraser;
    }

    public Path getPath() {
        return path;
    }

    public int getColor() {
        return color;
    }

    public boolean isEraser() { return isEraser;}
}
