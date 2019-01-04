package com.example.zhang.poemocean.Url;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.zhang.poemocean.Class.Author;
import com.example.zhang.poemocean.R;
import com.example.zhang.poemocean.activity.activity_poet_info;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Get_Author extends Helper_GetUrl {

    private ArrayList<Author> authors = new ArrayList<Author>();

    public Get_Author(TextView tx) {
        super(tx);
    }

    public Get_Author(Context ctx, ListView lis) {
        super(ctx, lis);
    }

    @Override
    protected String doInBackground(String... arg0) {
        return super.doInBackground(arg0);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        JsonParse(s);
    }

    private void JsonParse(String s) {
        try {
            JSONObject obj = new JSONObject(s);
            JSONArray ja = obj.getJSONArray("result");
            ArrayList<String> author_list = new ArrayList<String>();

            for (int i = 0; i < ja.length(); i++) {
                JSONObject j_b = ja.getJSONObject(i);
                author_list.add(j_b.getString("name"));
                this.authors.add(new Author(j_b.getString("name"), j_b.getString("desc")));
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.ctx, R.layout.support_simple_spinner_dropdown_item, author_list);
            this.myLis.setAdapter(adapter);

            this.myLis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i("itemClick", id + "Click!");
                    Intent mIntent = new Intent(ctx, activity_poet_info.class);
                    mIntent.putExtra("name", authors.get((int) id).getName());
                    mIntent.putExtra("desc", authors.get((int) id).getDesc());
                    ctx.startActivity(mIntent);
                }
            });


        } catch (Exception e) {
            Log.i("json", e.getMessage());
        }
    }
}
