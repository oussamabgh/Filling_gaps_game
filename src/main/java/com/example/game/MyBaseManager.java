package com.example.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashraf on 02/05/2018.
 */

public class MyBaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "WordsDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "words";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_WORD = "word";


    MyBaseManager (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                " " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT word_pk PRIMARY KEY AUTOINCREMENT," +
        " " + COLUMN_WORD + " varchar(4) NOT NULL);";
        sqLiteDatabase.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }


    void addWords(List words) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        for (Object word : words) {
            contentValues.put(COLUMN_WORD, (String) word);
            db.insert(TABLE_NAME, null, contentValues);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    Cursor getAllWords() {
        Cursor cursor ;
        SQLiteDatabase db = getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return cursor;
    }


}
