package com.example.zhang.poemocean.person_page;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.PrivateCredentialPermission;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhang.poemocean.R;

import org.xutils.*;

public class Publish extends Activity {
    private DBOpenHelper dbOpenHelper;
    @ViewInject(R.id.felling)
    EditText felling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_publish);
        x.view().inject(this);
        dbOpenHelper = new DBOpenHelper(Publish.this, "db_content", null, 1);
    }

    @Event(value = {R.id.publish_cancel, R.id.publish_ok})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.publish_cancel:
                finish();
                break;
            case R.id.publish_ok:
                String content = felling.getText().toString();
                if (content.equals("")) {
                    Toast.makeText(Publish.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日 HH:mm");// HH:mm:ss
                    Date date = new Date(System.currentTimeMillis());
                    insertData(dbOpenHelper.getReadableDatabase(), content, simpleDateFormat.format(date));
                    Toast.makeText(Publish.this, "发表成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    private void insertData(SQLiteDatabase sqLiteDatabase, String content, String ptime) {
        ContentValues values = new ContentValues();
        values.put("content", content);
        values.put("ptime", ptime);
        sqLiteDatabase.insert("tb_content", null, values);
    }

    @Event(R.id.img_back)
    private void GoBack(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbOpenHelper != null)
            dbOpenHelper.close();
    }
}
