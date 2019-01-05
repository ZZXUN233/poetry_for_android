package com.example.zhang.poemocean.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.zhang.poemocean.Class.Comments;
import com.example.zhang.poemocean.Class.Poem;

import java.util.ArrayList;


public class Db_comment extends Db_main {

    private SQLiteDatabase mydb;
    private static final String _TABLE = "comments";


    public Db_comment(Context ctx) {
        super(ctx);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        int version = db.getVersion();
        Log.i("db_version", version + "");
        try {

            db.execSQL("create table comments (" +
                    "id integer primary key autoincrement," +
                    "title text," +
                    "feelid text," +
                    "content text," +
                    "iflike text," +
                    "poem_title text," +
                    "poem_author text," +
                    "poem_content text" +
                    ")");
        } catch (SQLException e) {
            Log.i("comments_db", e.toString());
        }
    }

    public boolean insert(Comments comment) {
        boolean result = false;
//        if (ifExist(a_poem)) {
//            Log.i("poem", "数据已存在！");
//            return false;
//        }
        mydb = this.getReadableDatabase();
        if (mydb.isOpen()) {
            Log.d("DB", "数据库已经打开！");
        }
        ContentValues tableLine = new ContentValues();
        tableLine.put("title", comment.getTitle());
        tableLine.put("feelid", comment.getFeelid());
        tableLine.put("content", comment.getContent());
        tableLine.put("iflike", comment.getIflike());
        tableLine.put("poem_title", comment.getThePoem().getTitle());
        tableLine.put("poem_author", comment.getThePoem().getAuthors());
        tableLine.put("poem_content", comment.getThePoem().getContent());
        try {
            mydb.insert(this._TABLE, null, tableLine);
            Log.i("Db comment", "插入成功！");
            result = true;
        } catch (SQLException e) {
            Log.d("comment DB", e.getMessage());
            result = false;
        } finally {
            mydb.close();
            return result;
        }

    }

    public ArrayList<Poem> getPoems() {
        ArrayList<Poem> poems = new ArrayList<Poem>();
        mydb = getReadableDatabase();
        Cursor cursor = mydb.query(_TABLE, null, null, null, null, null, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return poems;
        } else {
            cursor.moveToFirst();
            poems.add(new Poem(
                    cursor.getString(cursor.getColumnIndex("poem_title")),
                    cursor.getString(cursor.getColumnIndex("poem_author")),
                    cursor.getString(cursor.getColumnIndex("poem_content"))
            ));
            while (cursor.moveToNext()) {
                poems.add(new Poem(
                        cursor.getString(cursor.getColumnIndex("poem_title")),
                        cursor.getString(cursor.getColumnIndex("poem_author")),
                        cursor.getString(cursor.getColumnIndex("poem_content"))
                ));
            }
            cursor.close();
            return poems;
        }
    }

    public ArrayList<Comments> getCommentByPoem(Poem thePoem) {
        ArrayList<Comments> theComments = new ArrayList<Comments>();
        mydb = getReadableDatabase();
        Cursor cursor = mydb.query(_TABLE, new String[]{"title", "feelid", "content", "iflike"},
                "poem_title=? and poem_author=? and poem_content=?",
                new String[]{thePoem.getTitle(), thePoem.getAuthors(), thePoem.getContent()}, null, null, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return theComments;
        } else {
            cursor.moveToFirst();
            theComments.add(new Comments(
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("feelid")),
                    cursor.getString(cursor.getColumnIndex("content")),
                    cursor.getString(cursor.getColumnIndex("iflike")), thePoem
            ));
            while (cursor.moveToNext()) {
                theComments.add(new Comments(
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("feelid")),
                        cursor.getString(cursor.getColumnIndex("content")),
                        cursor.getString(cursor.getColumnIndex("iflike")), thePoem
                ));
            }
            cursor.close();
            return theComments;
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
