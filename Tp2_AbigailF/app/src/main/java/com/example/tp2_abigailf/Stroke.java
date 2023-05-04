package com.example.tp2_abigailf;

import android.graphics.Path;

public class Stroke {
    private Path path;
    private int color,width;
    private boolean isEraser;
    public Stroke (Path path,int color,int width,boolean isEraser){
        this.path = path;
        this.color = color;
        this.width = width;
        this.isEraser = isEraser;
    }

    public Path getPath() {
        return path;
    }
    public int getColor() {
        return color;
    }
    public int getWidth() { return width; }
    public boolean isEraser() { return isEraser;}

    public void setPath(Path path) { this.path = path; }
    public void setWidth(int width) { this.width = width; }

}
