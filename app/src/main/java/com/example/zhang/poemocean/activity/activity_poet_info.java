package com.example.zhang.poemocean.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.zhang.poemocean.Class.Author;
import com.example.zhang.poemocean.Class.Poem;
import com.example.zhang.poemocean.Helper.MyConfig;
import com.example.zhang.poemocean.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class activity_poet_info extends Activity {

    @ViewInject(R.id.tex_author_name)
    private TextView tex_author_name;
    @ViewInject(R.id.tex_author_desc)
    private TextView tex_author_desc;

    private MyConfig myConfig;

    private Author getAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poet_info);
        x.view().inject(this);


        myConfig = new MyConfig(this);

        Intent mIntent = getIntent();

        getAuthor = new Author(mIntent.getStringExtra("name"),
                mIntent.getStringExtra("desc"));


        tex_author_name.setTypeface(myConfig.getTf());
        tex_author_name.setText(getAuthor.getName());

        tex_author_desc.setTypeface(myConfig.getTf());
        tex_author_desc.setText(getAuthor.getDesc());

    }
}
