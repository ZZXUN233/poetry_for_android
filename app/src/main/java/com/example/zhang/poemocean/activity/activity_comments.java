package com.example.zhang.poemocean.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.zhang.poemocean.Class.Comments;
import com.example.zhang.poemocean.Db.Db_comment;
import com.example.zhang.poemocean.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_comments extends Activity {

    private Db_comment myComments;

    @ViewInject(R.id.list_comments)
    private ListView list_comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        x.view().inject(this);

        myComments = new Db_comment(this);
        init();
    }

    private void init() {
        final ArrayList<Comments> getCommentsAll = myComments.GetAllComents();

        String[] from = {"emojiId", "title", "comment"};
        int[] to = {R.id.img_emoji_item, R.id.item_title, R.id.item_comment};
        TypedArray meojiIds = getResources().obtainTypedArray(R.array.emojis);
        List<Map<String, Object>> comments = new ArrayList<Map<String, Object>>();

        if (getCommentsAll.size() == 0) {
            HashMap<String, Object> temp = new HashMap<String, Object>();
            temp.put("emojiId", R.drawable.emoji_10);
            temp.put("title", "你还没有发表过评论！");
            temp.put("comment", "现在就去浏览诗歌，发表感想吧！");
            comments.add(temp);
        } else {
            for (Comments tem : getCommentsAll) {
                HashMap<String, Object> temp = new HashMap<String, Object>();
                temp.put("emojiId", meojiIds.getResourceId(Integer.valueOf(tem.getFeelid()), 0));
                temp.put("title", tem.getTitle());
                temp.put("comment", tem.getContent());
                comments.add(temp);
            }
        }
        list_comments.setAdapter(new SimpleAdapter(this, comments, R.layout.item_comments, from, to));

        list_comments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(activity_comments.this, activity_poem_detail.class);
                mIntent.putExtra("title", getCommentsAll.get((int) id).getThePoem().getTitle());
                mIntent.putExtra("authors", getCommentsAll.get((int) id).getThePoem().getAuthors());
                mIntent.putExtra("content", getCommentsAll.get((int) id).getThePoem().getContent());
                startActivity(mIntent);
            }
        });

//        ArrayList<String> headers = new ArrayList<String>();
//        if (getCommentsByPoem.size() == 0) {
//            headers.add("还没有感想，你可以发表自己的感想！");
//        } else
//            for (Comments tem : getCommentsByPoem) {
//                headers.add(tem.getHeader());
//            }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, headers);
//        lis_comments.setAdapter(adapter);
    }

    @Event(R.id.img_back)
    private void GoBack(View view) {
        finish();
    }
}
