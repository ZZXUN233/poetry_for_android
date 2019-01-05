package com.example.zhang.poemocean.person_page;

import android.content.Context;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {
    final String CREATE_TABLE_SQL = "create table tb_content (_id integer primary key autoincrement,content,ptime)";

    public DBOpenHelper(Context context, String name, CursorFactory factory,
                        int version) {
        super(context, name, null, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        Log.i("动态", "--版本更新" + oldVersion + "-->" + newVersion);
    }

}
