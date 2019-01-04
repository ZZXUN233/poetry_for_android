package com.example.zhang.poemocean.Db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Db_user extends SQLiteOpenHelper {

    private static final String _DATABASE = "users.db";
    private SQLiteDatabase mydb;


    public Db_user(Context context) {
        super(context, _DATABASE, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        int version = db.getVersion();
        Log.i("db_version", version + "");
        try {

            db.execSQL("create table user (" +
                    "id integer primary key," +
                    "account text," +
                    "pwd text," +
                    "name text," +
                    "phone text," +
                    "email text" +
                    ")");
        } catch (SQLException e) {
            Log.i("user_db", e.toString());
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
