package com.example.zhang.poemocean.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.zhang.poemocean.Class.User;

public class Db_user extends Db_main {

    private SQLiteDatabase mydb;
    private String _TABLE = "Users";


    public Db_user(Context context) {
        super(context);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        int version = db.getVersion();
        Log.i("db_version", version + "");
        try {

            db.execSQL("create table user (" +
                    "id integer primary key autoincrement," +
                    "account text," +
                    "pwd text," +
                    "name text," +
                    "email text" +
                    ")");
        } catch (SQLException e) {
            Log.i("user_db", e.toString());
        }
    }


    public boolean insert(User user) {
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
        tableLine.put("account", user.getAccount());
        tableLine.put("pwd", user.getPwd());
        tableLine.put("name", user.getName());
        tableLine.put("email", user.getEmail());
        try {
            mydb.insert(this._TABLE, null, tableLine);
            Log.i("Db_user comment", "插入成功！");
            result = true;
        } catch (SQLException e) {
            Log.d("User DB", e.getMessage());
            result = false;
        } finally {
            mydb.close();
            return result;
        }
    }

    public boolean ifNameUsed(String name) {
        mydb = getReadableDatabase();
        Cursor cursor = mydb.query(_TABLE, new String[]{"name"}, "name = ?",
                new String[]{name}, null, null, null);
        if (cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ifEmailUsed(String email) {
        mydb = getReadableDatabase();
        Cursor cursor = mydb.query(_TABLE, new String[]{"id"}, "email = ?",
                new String[]{email}, null, null, null);
        if (cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ifPwdEqual(String name, String pwd) {
        mydb = getReadableDatabase();
        Cursor cursor = mydb.query(_TABLE, new String[]{"pwd"}, "name = ?",
                new String[]{name}, null, null, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            String savedPwd = cursor.getString(cursor.getColumnIndex("pwd"));
            if (pwd.equals(savedPwd)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
