package com.example.zhang.poemocean.Db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.zhang.poemocean.Class.Poem;

import java.util.ArrayList;

public class Db_readed extends Db_poems {


    private static final String _TABLE = "myPoems";
    private static final String DB_NAME = "readLog.db";
    private SQLiteDatabase mydb;

    public Db_readed(Context context) {
        super(context, DB_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
    }

    @Override
    public boolean insert(Poem a_poem) {

        Log.i("DB 最近阅读", "插入数据成功！");
        return super.insert(a_poem);
    }

    @Override
    public ArrayList<Poem> getAllRecords() {
        mydb = this.getReadableDatabase();
        ArrayList<Poem> AllPoems = new ArrayList<Poem>();

        Cursor cursor = mydb.query(_TABLE,
                null, null, null, null, null, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return AllPoems;
        } else {
            cursor.moveToLast();
            AllPoems.add(new Poem(
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("author")),
                    cursor.getString(cursor.getColumnIndex("content"))
            ));
            while (cursor.moveToPrevious()) {
                AllPoems.add(new Poem(
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("author")),
                        cursor.getString(cursor.getColumnIndex("content"))
                ));
            }
            cursor.close();
            return AllPoems;
        }
    }

    public int getSize() {
        mydb = getReadableDatabase();
        Cursor cursor = mydb.query(_TABLE, null, null, null, null, null, null);
        return cursor.getCount();
    }

    public boolean delAll() {
        mydb = getWritableDatabase();
        try {
            mydb.execSQL("delete from " + _TABLE);
        } catch (SQLException e) {
            Log.i("Db", e.toString());
            return false;
        }
        return true;
    }

    @Override
    public boolean ifExist(Poem aPoem) {
        return super.ifExist(aPoem);
    }

    @Override
    public int queryOne(Poem aPoem) {
        return super.queryOne(aPoem);
    }

    @Override
    public boolean delOne(Poem aPoem) {
        return super.delOne(aPoem);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
