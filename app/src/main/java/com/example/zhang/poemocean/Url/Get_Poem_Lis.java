package com.example.zhang.poemocean.Url;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.zhang.poemocean.Db.Db_readed;
import com.example.zhang.poemocean.R;
import com.example.zhang.poemocean.activity.activity_poem_detail;
import com.example.zhang.poemocean.Class.Poem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Get_Poem_Lis extends Helper_GetUrl {

    private ArrayList<Poem> Poems = new ArrayList<Poem>();
    private Db_readed readed;

    public Get_Poem_Lis(Context ctx, ListView lis) {
        super(ctx, lis);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        JsonParse(s);

    }

    private void JsonParse(String s) {
        readed = new Db_readed(ctx);
        Log.i("DB 最近阅读", "打开数据库成功！");
        try {
            JSONObject obj = new JSONObject(s);
            JSONArray ja = obj.getJSONArray("result");
            ArrayList<String> poem_list = new ArrayList<String>();

            for (int i = 0; i < ja.length(); i++) {
                JSONObject j_b = ja.getJSONObject(i);
                String temp = j_b.getString("title") + "[" + j_b.getString("authors") + "]";
                Poem tem = new Poem(j_b.getString("title"), j_b.getString("authors"), j_b.getString("content"));
                this.Poems.add(tem);
                poem_list.add(temp);
            }

            if (poem_list.size() == 0) {
                poem_list.add("没有找到相关内容！");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.ctx, R.layout.support_simple_spinner_dropdown_item, poem_list);
            this.myLis.setAdapter(adapter);

            this.myLis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i("itemClick", id + "Click!");
                    Intent mIntent = new Intent(ctx, activity_poem_detail.class);
                    mIntent.putExtra("title", Poems.get((int) id).getTitle());
                    mIntent.putExtra("authors", Poems.get((int) id).getAuthors());
                    mIntent.putExtra("content", Poems.get((int) id).getContent());
                    readed.insert(Poems.get((int) id));
                    ctx.startActivity(mIntent);
                }
            });


        } catch (Exception e) {
            Log.i("json", e.getMessage());
        }


    }
}
