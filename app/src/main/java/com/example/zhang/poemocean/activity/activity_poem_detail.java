package com.example.zhang.poemocean.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhang.poemocean.Class.Poem;
import com.example.zhang.poemocean.Db.Db_poems;
import com.example.zhang.poemocean.Helper.MyConfig;
import com.example.zhang.poemocean.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.sql.SQLException;

public class activity_poem_detail extends Activity {

    @ViewInject(R.id.poem_title)
    private TextView title;

    @ViewInject(R.id.poem_author)
    private TextView author;

    @ViewInject(R.id.poem_content)
    private TextView content;

    @ViewInject(R.id.img_like)
    private ImageView img_like;

    private MyConfig myConfig;

    private Poem getPoem;

    private Db_poems myCollections;

    private boolean ifLiked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poem_detail);
        x.view().inject(this);
        init();

    }

    private void init() {
        //初始化配置，数据库
        myConfig = new MyConfig(this);
        myCollections = new Db_poems(this);
        Intent mIntent = getIntent();
        getPoem = new Poem(mIntent.getStringExtra("title"),
                mIntent.getStringExtra("authors"),
                mIntent.getStringExtra("content"));

        //获得内容后开始判断是否已经收藏
        if (myCollections.ifExist(this.getPoem)) {
            Log.i("details", "已经收藏的诗歌");
            img_like.setImageResource(R.drawable.pg_heart_chosed);
            ifLiked = true;
        }

        title.setTypeface(myConfig.getTf());
        title.setText(getPoem.getTitle());
        author.setText(getPoem.getAuthors());
        author.setTypeface(myConfig.getTf());

        String Con = getPoem.getContent();
//        int lineCount = myConfig.appearNumber(Con, "，") + myConfig.appearNumber(Con, "。");
        content.setMaxLines(myConfig.appearNumber(Con, "。"));
        content.setText(getPoem.getContent());
        content.setTypeface(myConfig.getTf());
    }

    @Event(R.id.img_like)
    private void checkLike(View v) {
        try {
            if (!ifLiked) {
                if (myCollections.insert(this.getPoem)) {
                    img_like.setImageResource(R.drawable.pg_heart_chosed);
                    Toast.makeText(this, "收藏成功！", Toast.LENGTH_SHORT).show();
                    ifLiked = true;
                }
            } else {
                if (myCollections.delOne(this.getPoem)) {
                    ifLiked = false;
                    img_like.setImageResource(R.drawable.pg_heart);
                    Toast.makeText(this, "取消收藏成功！", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Log.i("poem insert!", e.toString());
        } finally {
            myCollections.close();
        }
    }

    @Event(R.id.img_back)
    private void back(View view) {
        this.myCollections.close();
        finish();
    }
}
