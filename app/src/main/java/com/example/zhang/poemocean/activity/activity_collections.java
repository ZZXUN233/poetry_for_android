package com.example.zhang.poemocean.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.zhang.poemocean.Class.Poem;
import com.example.zhang.poemocean.Db.Db_poems;
import com.example.zhang.poemocean.MainActivity;
import com.example.zhang.poemocean.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_collections extends Activity {

    private Db_poems myCollections;
    private ArrayList<Poem> myPoems;

    @ViewInject(R.id.list_collections)
    private ListView lis_collections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        x.view().inject(this);
        init();
    }

    private void init() {
        myCollections = new Db_poems(this);
        myPoems = myCollections.getAllRecords();
        ArrayList<String> poem_heads = new ArrayList<String>();
        if (myPoems.size() == 0) {
            poem_heads.add("还没有收藏，请进入诗歌详情收藏!");
        } else {
//            for (Poem tem : myPoems) {
//                poem_heads.add(tem.getHead());
//            }
            for (int i = myPoems.size() - 1; i >= 0; i--) {
                poem_heads.add(myPoems.get(i).getHead());

            }
        }

        String[] from = {"title", "authors"};
        int[] to = {R.id.item_poem_title, R.id.item_poem_author};
        List<Map<String, Object>> date = new ArrayList<Map<String, Object>>();
        for (Poem tem : myPoems) {
            HashMap<String, Object> temp = new HashMap<String, Object>();
            temp.put("title", tem.getTitle());
            temp.put("authors", tem.getAuthors());
            date.add(temp);
        }
        SimpleAdapter adapter = new SimpleAdapter(activity_collections.this, date, R.layout.item_poem_list, from, to);
        lis_collections.setAdapter(adapter);

        lis_collections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("itemClick", id + "Click!");
                Intent mIntent = new Intent(activity_collections.this, activity_poem_detail.class);
                mIntent.putExtra("title", myPoems.get((int) id).getTitle());
                mIntent.putExtra("authors", myPoems.get((int) id).getAuthors());
                mIntent.putExtra("content", myPoems.get((int) id).getContent());
                startActivity(mIntent);
            }
        });
//        ArrayAdapter<String> collections = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, poem_heads);
//        lis_collections.setAdapter(collections);
//        lis_collections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.i("itemClick", id + "Click!");
//                Intent mIntent = new Intent(activity_collections.this, activity_poem_detail.class);
//                mIntent.putExtra("title", myPoems.get((int) id).getTitle());
//                mIntent.putExtra("authors", myPoems.get((int) id).getAuthors());
//                mIntent.putExtra("content", myPoems.get((int) id).getContent());
//                startActivity(mIntent);
//            }
//        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        init();
    }

    @Event(R.id.img_back)
    private void back(View v) {
        finish();
    }
}
