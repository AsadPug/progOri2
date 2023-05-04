package com.example.tp2_abigailf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EcouteurCanvas ecCanvas;
    EcouteurOutils ecOutils;
    ConstraintLayout principal;
    Surface surface;
    Strokes strokes;
    Pen pen;
    Paint paint;
    int[] colors,toolImages;
    HashMap<String, Integer> tools;
    LinearLayout colorPalette,toolBox;

    ImageView currentColorView,currentToolView;
    Button colorButtons [];
    LargeurTrait largeurTraitActivity;
    int currentColor,currentTool,backgroundColor,colorAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        principal = findViewById(R.id.principal);
        pen = new Pen(25);
        surface = new Surface (this);
        ecCanvas = new EcouteurCanvas();
        ecOutils = new EcouteurOutils();
        strokes = new Strokes();
        largeurTraitActivity = new LargeurTrait(this);
        backgroundColor = Color.WHITE;
        currentColor = Color.BLACK;
        colorAmount = 8;
        currentColorView = findViewById(R.id.currentColor);
        makeColorButtons();
        makeToolButtons();
        currentToolView = findViewById(R.id.currentTool);
        currentTool = tools.get("Pencil");

        surface.setLayoutParams(new ConstraintLayout.LayoutParams(convertirDpEnPixels(355),convertirDpEnPixels(605)));
        surface.setBackgroundColor(backgroundColor);

        principal.addView(surface);

        surface.setOnTouchListener(ecCanvas);
    }

    public void makeToolButtons(){
        toolBox = findViewById(R.id.toolBox);

        tools = new HashMap<>();
        tools.put("Pencil", 0);
        tools.put("Eraser", 1);
        tools.put("ColorPicker", 2);
        tools.put("Bucket", 3);
        tools.put("StrokeSize", 4);
        tools.put("Redo", 5);
        tools.put("Undo", 6);
        tools.put("Round", 7);
        tools.put("Square", 8);
        tools.put("Triangle",9);

        toolImages = new int[10];
        toolImages[0] = R.drawable.pencil;
        toolImages[1] = R.drawable.eraser;
        toolImages[2] = R.drawable.colorpicker;
        toolImages[3] = R.drawable.bucket;
        toolImages[4] = R.drawable.strokesize;
        toolImages[5] = R.drawable.redo;
        toolImages[6] = R.drawable.undo;
        toolImages[7] = R.drawable.round;
        toolImages[8] = R.drawable.square;
        toolImages[9] = R.drawable.triangle;

        for(int i=0;i<toolBox.getChildCount();i++){
            toolBox.getChildAt(i).setOnClickListener(ecOutils);
            ((ImageView)toolBox.getChildAt(i)).setImageResource(toolImages[i]);
        }
    }
    public void makeColorButtons(){
        colorButtons = new Button[8];
        colorPalette = findViewById(R.id.colorPalette);

        colors = new int[8];
        colors[0] = Color.BLACK;
        colors[1] = Color.WHITE;
        colors[2] = Color.RED;
        colors[3] = Color.parseColor("#FFA500");
        colors[4] = Color.YELLOW;
        colors[5] = Color.GREEN;
        colors[6] = Color.BLUE;
        colors[7] = Color.parseColor("#A020F0");

        for(int i =0;i<colorAmount;i++){
            colorButtons[i] = (Button)colorPalette.getChildAt(i);
            colorButtons[i].setBackgroundColor(colors[i]);
            colorButtons[i].setOnClickListener(ecOutils);
        }
    }
    public int convertirDpEnPixels(int dp){
        return Math.round(dp * this.getResources().getDisplayMetrics().density);
    }

    private class Surface extends View {
        Bitmap bitmapImage;
        public Surface(Context context) {
            super(context);
            paint = new Paint();// arrondir les contours
            paint.setColor(currentColor);
            paint.setStrokeWidth(pen.getWidth());
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStyle(Paint.Style.STROKE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            for(int i=0;i<strokes.getStrokeCount();i++){
                if(strokes.at(i).isEraser()){
                    paint.setColor(backgroundColor);
                    paint.setStrokeWidth(strokes.at(i).getWidth()*(float)2);
                }

                else{
                    paint.setColor(strokes.at(i).getColor());
                    paint.setStrokeWidth(strokes.at(i).getWidth());
                }

                canvas.drawPath(strokes.at(i).getPath(),paint);
            }

        }
        public Bitmap getBitmapImage() {

            this.buildDrawingCache();
            bitmapImage = Bitmap.createBitmap(this.getDrawingCache());
            this.destroyDrawingCache();

            return bitmapImage;
        }
    }
    private class EcouteurCanvas implements View.OnTouchListener {
        private Path p;
        private Square square;
        private Round round;
        private Triangle triangle;
        private int nTrianglePoints;
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if(currentTool == tools.get("Pencil")|| currentTool == tools.get("Eraser")){ this.stroke(event); }
            else if(currentTool == tools.get("ColorPicker")){ this.colorPicker(event); }
            else if(currentTool == tools.get("Bucket")){ this.bucket(); }
            else if(currentTool == tools.get("Square")){ this.square(event); }
            else if(currentTool == tools.get("Round")){ this.round(event); }
            else if(currentTool == tools.get("Triangle")){this.triangle(event);}
            if(currentTool != tools.get("Triangle")&&nTrianglePoints!=0){
                strokes.removeLastStroke();
                nTrianglePoints=0;
            }
            surface.invalidate();
            return true;
        }
        private void stroke(MotionEvent event){
            pen.setX(event.getX());
            pen.setY(event.getY());
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    p = new Path();
                    boolean isEraser=false;
                    if(currentTool == tools.get("Eraser"))
                        isEraser = true;
                    Stroke s = new Stroke(p,currentColor,pen.getWidth(),isEraser);
                    strokes.addStroke(s);
                    p.moveTo(pen.getX(),pen.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    p.lineTo(pen.getX(),pen.getY());
                    strokes.getStrokes()[strokes.getStrokeCount()-1].setPath(p);
                    break;
            }
        }
        private void colorPicker(MotionEvent event){
            if(event.getAction() == MotionEvent.ACTION_UP){
                Bitmap b = surface.getBitmapImage();
                currentColor = b.getPixel((int)event.getX(),(int)event.getY());
                currentColorView.setBackgroundColor(currentColor);
                currentTool = tools.get("Pencil");
                currentToolView.setImageResource(toolImages[currentTool]);
            }
        }
        private void bucket(){
            backgroundColor = currentColor;
            surface.setBackgroundColor(backgroundColor);
        }
        private void square(MotionEvent event){
            p = new Path();
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    square = new Square(event.getX(), event.getY());
                    square.setSecondPoint(event.getX(),event.getY());
                    Stroke s = new Stroke(p,currentColor,pen.getWidth(),false);
                    strokes.addStroke(s);
                    break;
                case MotionEvent.ACTION_MOVE:
                    square.setSecondPoint(event.getX(),event.getY());
                    p.addRect(square.getX1(), square.getY1(), square.getX2(), square.getY2(), Path.Direction.CW);
                    strokes.at(strokes.getStrokeCount()-1).setPath(p);
                    break;
                case MotionEvent.ACTION_UP:
                    square.setSecondPoint(event.getX(),event.getY());
                    p.addRect(square.getX1(), square.getY1(), square.getX2(), square.getY2(), Path.Direction.CW);
                    strokes.at(strokes.getStrokeCount()-1).setPath(p);
                    break;
            }
        }
        private void round(MotionEvent event){
            p = new Path();
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    round = new Round(event.getX(), event.getY());
                    round.setSecondPoint(event.getX(),event.getY());
                    Stroke s = new Stroke(p,currentColor,pen.getWidth(),false);
                    strokes.addStroke(s);
                    break;
                case MotionEvent.ACTION_MOVE:
                    round.setSecondPoint(event.getX(),event.getY());
                    p.addCircle(round.getX1(), round.getY1(), round.getRayon(), Path.Direction.CW);
                    strokes.at(strokes.getStrokeCount()-1).setPath(p);
                    break;
                case MotionEvent.ACTION_UP:
                    round.setSecondPoint(event.getX(),event.getY());
                    p.addCircle(round.getX1(), round.getY1(), round.getRayon(), Path.Direction.CW);
                    strokes.at(strokes.getStrokeCount()-1).setPath(p);
                    break;
            }
        }
        private void triangle(MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                if(nTrianglePoints == 0){
                    p= new Path();
                    triangle = new Triangle(event.getX(),event.getY());
                    Stroke s = new Stroke(p,currentColor,pen.getWidth(),false);
                    strokes.addStroke(s);
                }
                nTrianglePoints += 1;
                p.addCircle(event.getX(), event.getY(), 5, Path.Direction.CW);
                strokes.at(strokes.getStrokeCount()-1).setPath(p);
                if(nTrianglePoints == 2)triangle.setSecondPoint(event.getX(), event.getY());
                if(nTrianglePoints == 3){
                    p = new Path();
                    nTrianglePoints=0;
                    triangle.setThirdPoint(event.getX(), event.getY());
                    p.moveTo(triangle.getX1(),triangle.getY1());
                    p.lineTo(triangle.getX2(),triangle.getY2());
                    p.lineTo(triangle.getX3(),triangle.getY3());
                    p.lineTo(triangle.getX1(),triangle.getY1());
                    strokes.at(strokes.getStrokeCount()-1).setPath(p);
                }

            }
        }


    }
    private class EcouteurOutils implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if(v.getParent() == colorPalette){
                if(v instanceof Button){
                    for(int i=0;i<colorAmount;i++)
                        if(v == colorPalette.getChildAt(i))
                            currentColor = colors[i];
                    currentColorView.setBackgroundColor(currentColor);
                }
            }
            else if(v.getParent() == toolBox){
                for(int i=0;i<toolBox.getChildCount();i++){
                    if(toolBox.getChildAt(i)==v)
                        if(i == tools.get("Undo"))strokes.undo();
                        else if(i == tools.get("Redo"))strokes.redo();
                        else if (i == tools.get("StrokeSize"))largeurTraitActivity.show();
                        else currentTool = i;
                }
                surface.invalidate();
                currentToolView.setImageResource(toolImages[currentTool]);
            }
        }
    }
}


