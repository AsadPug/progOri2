package com.example.tpfinal_abigailfournier;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static DatabaseHelper instance;
    public static DatabaseHelper getInstance(Context context){
        if(instance == null){
            instance = new DatabaseHelper(context.getApplicationContext());

        }
        return instance;
    }
    public DatabaseHelper(Context context) {
        super(context,"db",null,1);
    }

    public void addScore(int score, SQLiteDatabase sqLiteDatabase){
        ContentValues cv = new ContentValues();
        cv.put("score",score);
        sqLiteDatabase.insert("highscores",null,cv);
    }
    public ArrayList<Integer> getScores(SQLiteDatabase sqLiteDatabase){
        ArrayList<Integer> scores = new ArrayList<Integer>();
        Cursor c = sqLiteDatabase.rawQuery("SELECT score FROM highscores ORDER BY score DESC", null);
        while(c.moveToNext()){
            scores.add(c.getInt(0));
        }
        c.close();
        return scores;
    }
    public int getBestScore(SQLiteDatabase sqLiteDatabase){
        int bestScore = 0;
        Cursor c = sqLiteDatabase.rawQuery("SELECT score FROM highscores ORDER BY score DESC", null);
        c.moveToFirst();
        if(c.getCount() != 0){
            bestScore = c.getInt(0);
        }

        c.close();
        return bestScore;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
            "CREATE TABLE IF NOT EXISTS highscores (" +
                " _id       INTEGER PRIMARY KEY AUTOINCREMENT," +
                " score     INTEGER" +
            ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
