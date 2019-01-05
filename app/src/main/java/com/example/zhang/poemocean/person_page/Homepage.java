package com.example.zhang.poemocean.person_page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.example.zhang.poemocean.R;

import org.xutils.*;
import org.xutils.view.annotation.Event;

public class Homepage extends Activity {
    ArrayList<Map<String, String>> contentList = new ArrayList<Map<String, String>>();
    private ListView listView;
    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_homepage);
        x.view().inject(this);
        dbOpenHelper = new DBOpenHelper(Homepage.this, "db_content", null, 1);
        Cursor cursor = dbOpenHelper.getReadableDatabase().rawQuery("select * from tb_content", null);
        if (cursor.getCount() == 0) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("content", "还没有发布过内容，点击\"笔\"的图标开始进行创作！");
            map.put("ptime", "2066年-6月-6日");
        }
        while (cursor.moveToNext()) {
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String ptime = cursor.getString(cursor.getColumnIndex("ptime"));
            Map<String, String> map = new HashMap<String, String>();
            map.put("content", content);
            map.put("ptime", ptime);
            contentList.add(map);
        }
        init();
    }

    private void init() {
        listView = (ListView) findViewById(R.id.lis_pub);
        Collections.reverse(contentList);
        String from[] = {"content", "ptime"};
        int to[] = {R.id.tv_content, R.id.tv_time};
        SimpleAdapter adapter = new SimpleAdapter(Homepage.this, contentList, R.layout.item_background, from, to);

        listView.setAdapter(adapter);

//        listView.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//                                    long arg3) {
//                // TODO Auto-generated method stub
//                Intent intent = new Intent(Homepage.this, null);
//                startActivity(intent);
//            }
//
//        });


    }

    @Event(value = {R.id.back})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    @Event(R.id.img_edit)
    private void do_edit(View view) {
        startActivity(new Intent(Homepage.this, Publish.class));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbOpenHelper != null)
            dbOpenHelper.close();
    }
}
