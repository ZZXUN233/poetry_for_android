package com.example.zhang.poemocean.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.zhang.poemocean.Helper.MyConfig;
import com.example.zhang.poemocean.R;
import com.example.zhang.poemocean.Url.Get_Author;
import com.example.zhang.poemocean.Url.Get_Poem_Lis;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


public class activity_poemList extends Activity {
    @ViewInject(R.id.sp_chooses)
    private Spinner item_chose;

    @ViewInject(R.id.poem_list)
    private ListView poem_list;

    @ViewInject(R.id.img_search)
    private ImageView img_search;

    @ViewInject(R.id.tex_input)
    private TextView tex_input;

    @ViewInject(R.id.img_back)
    private ImageView img_back;

    private String[] items = {"模糊搜索", "诗经", "唐朝", "宋朝", "作者"};

    private MyConfig myConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poem_list);
        x.view().inject(this);
        init();


    }

    private void init() {

        myConfig = new MyConfig(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity_poemList.this, R.layout.support_simple_spinner_dropdown_item, items);
        item_chose.setAdapter(adapter);
        int page = (int) ((Math.random() * 9 + 1) * 100);
        Log.i("page", page + "");
        new Get_Poem_Lis(this, poem_list).execute(MyConfig.getUrlPoemList(page));

        item_chose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int page = (int) ((Math.random() * 9 + 1) * 100);
                switch ((int) id) {
                    case 0://模糊搜索
                        new Get_Poem_Lis(activity_poemList.this, poem_list).execute(MyConfig.getUrlPoemList(page));
                        break;
                    case 1://诗经搜索
                        break;
                    case 2://唐朝
                        new Get_Poem_Lis(activity_poemList.this, poem_list).execute(MyConfig.getUrlPoemTang(page));
                        break;
                    case 3://宋朝
                        new Get_Poem_Lis(activity_poemList.this, poem_list).execute(MyConfig.getUrlPoemSong(page));
                        break;
                    case 4://作者搜索
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                int page = (int) ((Math.random() * 9 + 1) * 100);
                new Get_Poem_Lis(activity_poemList.this, poem_list).execute(MyConfig.getUrlPoemList(page));

            }
        });


    }

    @Event(R.id.img_search)
    private void search(View v) {
        String input = tex_input.getText().toString();
        int item_chosed = item_chose.getSelectedItemPosition();
        Log.i("spinner", items[item_chosed]);
        if (input.length() == 0) {
            Toast.makeText(this, "请输入搜索内容！", Toast.LENGTH_SHORT).show();
        }
        switch (item_chosed) {
            case 0://模糊搜索

                new Get_Poem_Lis(activity_poemList.this, poem_list).execute(MyConfig.URL_SEARCH_LIKE + input);
                break;
            case 1://诗经搜索
                new Get_Poem_Lis(activity_poemList.this, poem_list).execute(MyConfig.URL_SEARCH_BY_NAME + input);
                break;
            case 2://唐朝
                new Get_Poem_Lis(activity_poemList.this, poem_list).execute(MyConfig.URL_SEARCH_BY_NAME + input);
                break;
            case 3://宋朝
                new Get_Poem_Lis(activity_poemList.this, poem_list).execute(MyConfig.URL_SEARCH_BY_NAME + input);
                break;
            case 4://作者搜索
                new Get_Author(activity_poemList.this, poem_list).execute(MyConfig.URLSEARCH_BY_AUTHOR_NAME + input);
                break;
            default:
                break;
        }
    }

    @Event(R.id.img_back)
    private void back(View v) {
        finish();
    }

}
