package com.example.zhang.poemocean.person_page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.content.DialogInterface.OnClickListener;
import android.widget.TextView;

import com.example.zhang.poemocean.R;

import org.xutils.*;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

public class Homepage extends Activity {

    @ViewInject(R.id.myname)
    TextView myname;
    private ListView listView;
    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_homepage);
        x.view().inject(this);

        init();
    }

    private void init() {

        final ArrayList<Map<String, String>> contentList = new ArrayList<Map<String, String>>();
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

        listView = (ListView) findViewById(R.id.lis_pub);
        Collections.reverse(contentList);
        String from[] = {"content", "ptime"};
        int to[] = {R.id.tv_content, R.id.tv_time};
        final SimpleAdapter adapter = new SimpleAdapter(Homepage.this, contentList, R.layout.item_background, from, to);

        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int position, long id) {
                final String posString = String.valueOf(position + 1);
                // TODO Auto-generated method stub
                //显示弹窗
                AlertDialog.Builder dialog01 = new AlertDialog.Builder(Homepage.this);
                dialog01.setMessage("删除该动态？");
                dialog01.setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //dbOpenHelper.getWritableDatabase().delete("tb_content", null,null);
                        String text = contentList.get(position).get("content");
//                        String state = text.substring(text.indexOf("=") + 1, text.indexOf(","));
                        dbOpenHelper.getWritableDatabase().delete("tb_content", "content=?", new String[]{text});

                        // TODO Auto-generated method stub
                        //Log.i("position",state);
                        //Log.i("id",idsString);
                        //finish();
//                        contentList.remove(position);//选择行的位置
                        adapter.notifyDataSetChanged();

                    }

                });
                dialog01.setNegativeButton("取消", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        //Toast.makeText(Homepage.this, "已取消", 0).show();
                    }
                });
                dialog01.create().show();

                return false;
            }
        });


    }

    @Event(value = {R.id.back, R.id.myname})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.myname:

                AlertDialog.Builder dialog02 = new AlertDialog.Builder(Homepage.this);
                final View view = LayoutInflater.from(this).inflate(R.layout.item_list, null);
                dialog02.setView(view);
                //dialog02.setTitle("閺囧瓨鏁奸弰鐢敌?);
                dialog02.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        //Toast.makeText(Homepage.this, "瀹歌弓绻氱€涳拷", 0).show();
                        EditText editText = (EditText) view.findViewById(R.id.et_name);
                        String et_myname = editText.getText().toString().trim();
                        //Log.i("name", et_myname);
                        myname.setText(et_myname);
                    }
                });
                dialog02.setNegativeButton("取消", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        //Toast.makeText(MainActivity.this, "瀹告彃褰囧☉锟?, 0).show();

                    }
                });
                dialog02.show();
                //dialog02.create();

                break;
        }
    }

 /*   @Event(value = {R.id.back})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }*/

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
