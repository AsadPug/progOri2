package com.example.annexe12;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
    public void ajouterInventeur(Inventeur e,SQLiteDatabase sqLiteDatabase){
        ContentValues cv = new ContentValues();
        cv.put("nom",e.getNom());
        cv.put("origine",e.getOrigine());
        cv.put("invention",e.getInvention());
        cv.put("annee",e.getAnnee());
        sqLiteDatabase.insert("inventeur",null,cv);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE inventeur (" +
                " _id       INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nom       TEXT," +
                " origine   TEXT," +
                " invention TEXT," +
                " annee     INTEGER)");
        ajouterInventeur(new Inventeur("Laszlo Biro","Hongrie","Stylo à bille",1938),sqLiteDatabase);
        ajouterInventeur(new Inventeur("Benjamin Franklin","Etats-Unis","Paratonnerre",1752),sqLiteDatabase);
        ajouterInventeur(new Inventeur("Mary Anderson","Etats-Unis","Essuie-glace",1903),sqLiteDatabase);
        ajouterInventeur(new Inventeur("Grace Hopper","Etats-Unis","Compilateur",1952),sqLiteDatabase);
        ajouterInventeur(new Inventeur("Benoit Rouquayrot","France","Scaphandre",1864),sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //sqLiteDatabase.execSQL();
    }
}
