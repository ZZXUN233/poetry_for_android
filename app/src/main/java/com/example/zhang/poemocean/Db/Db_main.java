package com.example.zhang.poemocean.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Db_main extends SQLiteOpenHelper {

    private static final String DB = "app.db";


    public Db_main(Context ctx) {
        super(ctx, DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
