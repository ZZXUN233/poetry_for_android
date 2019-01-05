package com.example.zhang.poemocean.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.zhang.poemocean.Class.Poem;

import java.util.ArrayList;

public class Db_poems extends SQLiteOpenHelper {
    private SQLiteDatabase mydb;
    private static final String _TABLE = "myPoems";
    private static final String DB_name = "myPoems.db";


    public Db_poems(Context context) {
        super(context, DB_name, null, 1);

    }

    public Db_poems(Context ctx, String DB_name) {
        super(ctx, DB_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        int version = db.getVersion();
        Log.i("db_version", version + "");
        try {

            db.execSQL("create table myPoems (" +
                    "id integer primary key autoincrement," +
                    "title text," +
                    "author text," +
                    "content text" +
                    ")");
        } catch (SQLException e) {
            Log.i("poems_db", e.toString());
        }
    }


    public boolean insert(Poem a_poem) {
        boolean result = false;
        if (ifExist(a_poem)) {
            Log.i("poem", "数据已存在！");
            return false;
        }
        mydb = this.getReadableDatabase();
        if (mydb.isOpen()) {
            Log.d("DB", "数据库已经打开！");
        }
        ContentValues tableLine = new ContentValues();
        tableLine.put("title", a_poem.getTitle());
        tableLine.put("author", a_poem.getAuthors());
        tableLine.put("content", a_poem.getContent());
        try {
            mydb.insert(this._TABLE, null, tableLine);
            result = true;
        } catch (SQLException e) {
            Log.d("DB", e.getMessage());
            result = false;
        } finally {
            mydb.close();
            return result;
        }
    }

    public ArrayList<Poem> getAllRecords() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Poem> AllPoems = new ArrayList<Poem>();

        Cursor cursor = db.query(_TABLE,
                null, null, null, null, null, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return AllPoems;
        } else {
            cursor.moveToFirst();
            AllPoems.add(new Poem(
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("author")),
                    cursor.getString(cursor.getColumnIndex("content"))
            ));
            while (cursor.moveToNext()) {
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

    public boolean ifExist(Poem aPoem) {
        int flag = queryOne(aPoem);
        if (flag == -1) {
            return false;
        }
        return true;
    }

    public int queryOne(Poem aPoem) {
        SQLiteDatabase db = this.getReadableDatabase();
        int id = -1;
        Cursor cursor = null;
        cursor = db.query(_TABLE, new String[]{"id"},
                "title=? and author=? and content=?",
                new String[]{aPoem.getTitle(), aPoem.getAuthors(), aPoem.getContent()},
                null, null, null);
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
            Log.i("Query One", id + "");
        }
        return id;
    }

    public boolean delOne(Poem aPoem) {
        SQLiteDatabase db = getWritableDatabase();
        int flag = this.queryOne(aPoem);
        Boolean result = false;
        if (flag == -1) {
            Log.i("DB delOne", "数据不存在或数据库为空！");
            return false;
        } else {
            try {
                result = db.delete(_TABLE, "id=" + flag, null) > 0;
                Log.i("DB poem", "删除一条记录成功！");
            } catch (Exception e) {
                result = false;
                Log.i("DB_poems 删除数据", e.toString());
            } finally {
                db.close();
                return result;
            }
        }
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
