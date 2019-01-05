package com.example.zhang.poemocean.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhang.poemocean.Class.Comments;
import com.example.zhang.poemocean.Class.Poem;
import com.example.zhang.poemocean.Db.Db_comment;
import com.example.zhang.poemocean.Db.Db_poems;
import com.example.zhang.poemocean.Helper.MyConfig;
import com.example.zhang.poemocean.R;
import com.example.zhang.poemocean.Url.Get_Author;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_poem_detail extends Activity {

    @ViewInject(R.id.poem_title)
    private TextView title;

    @ViewInject(R.id.poem_author)
    private TextView author;

    @ViewInject(R.id.poem_content)
    private TextView content;

    @ViewInject(R.id.img_like)
    private ImageView img_like;

    @ViewInject(R.id.lis_comments)
    private ListView lis_comments;

    private MyConfig myConfig;

    private Poem getPoem;


    private boolean ifLiked = false;

    private Db_poems myCollections;
    public Dialog pub_comment;
    private Db_comment myComment;

    private String feelsId;
    private String iflike;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poem_detail);
        x.view().inject(this);
        myConfig = new MyConfig(this);
        myCollections = new Db_poems(this);
        myComment = new Db_comment(this);


        Intent mIntent = getIntent();
        getPoem = new Poem(mIntent.getStringExtra("title"),
                mIntent.getStringExtra("authors"),
                mIntent.getStringExtra("content"));

        init();
        initDialog();

    }

    private void init() {
        //初始化配置，数据库


        //初始化诗歌的评论
        initLisComments();

        //获得内容后开始判断是否已经收藏
        if (myCollections.ifExist(this.getPoem)) {
            Log.i("details", "已经收藏的诗歌");
            img_like.setImageResource(R.drawable.pg_heart_chosed);
            ifLiked = true;
        } else {
            img_like.setImageResource(R.drawable.pg_heart);
            ifLiked = false;
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

    private void initLisComments() {
        ArrayList<Comments> getCommentsByPoem = myComment.getCommentByPoem(getPoem);

        String[] from = {"emojiId", "title", "comment"};
        int[] to = {R.id.img_emoji_item, R.id.item_title, R.id.item_comment};
        TypedArray meojiIds = getResources().obtainTypedArray(R.array.emojis);
        List<Map<String, Object>> comments = new ArrayList<Map<String, Object>>();

        if (getCommentsByPoem.size() == 0) {
            HashMap<String, Object> temp = new HashMap<String, Object>();
            temp.put("emojiId", R.drawable.emoji_10);
            temp.put("title", "还没有人发表感想");
            temp.put("comment", "少侠，何不留下你的感想！");
            comments.add(temp);
        } else {
            for (Comments tem : getCommentsByPoem) {
                HashMap<String, Object> temp = new HashMap<String, Object>();
                temp.put("emojiId", meojiIds.getResourceId(Integer.valueOf(tem.getFeelid()), 0));
                temp.put("title", tem.getTitle());
                temp.put("comment", tem.getContent());
                comments.add(temp);
            }
        }
        lis_comments.setAdapter(new SimpleAdapter(this, comments, R.layout.item_comments, from, to));

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


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        init();
    }

    private void initDialog() {
        pub_comment = new Dialog(activity_poem_detail.this);
        LayoutInflater inflater = LayoutInflater.from(activity_poem_detail.this);
        final View dialog_view = inflater.inflate(R.layout.dialog_speech, null);
        pub_comment.setContentView(dialog_view);
        pub_comment.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Log.i("dialog", "on show!");
                final TextView tex_title = dialog_view.findViewById(R.id.tex_my_title);
                Spinner sp_feels = dialog_view.findViewById(R.id.sp_feels);
                final Switch sw_iflike = dialog_view.findViewById(R.id.cb_like);
                final TextView tex_content = dialog_view.findViewById(R.id.speech_content);
                Button btn_pub = dialog_view.findViewById(R.id.btn_pub);
                Button btn_cancel = dialog_view.findViewById(R.id.btn_cancel);

                //init switch

                if (myCollections.queryOne(getPoem) != -1) {
                    sw_iflike.setChecked(true);
                } else {
                    sw_iflike.setChecked(false);
                }

                //init spinner
                String[] from = {"imgId"};
                int[] to = {R.id.img_emojis};
                TypedArray imgIds = getResources().obtainTypedArray(R.array.emojis);
                List<Map<String, Object>> date = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < imgIds.length(); i++) {
                    HashMap<String, Object> temp = new HashMap<String, Object>();
                    temp.put("imgId", imgIds.getResourceId(i, 0));
                    date.add(temp);
                }
                SimpleAdapter adapter = new SimpleAdapter(activity_poem_detail.this, date, R.layout.item_spinner_comment, from, to);
                sp_feels.setAdapter(adapter);

                sp_feels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        feelsId = String.valueOf(position);
                        Log.i("spinner emoji id=", feelsId);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        feelsId = String.valueOf(0);
                    }
                });

                //发布按钮的实现

                btn_pub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = tex_title.getText().toString();
                        String content = tex_content.getText().toString();
                        boolean result = false;
                        Log.i("btn_pub", "clicked!");
                        if (sw_iflike.isChecked()) {
                            myCollections.insert(getPoem);
                            iflike = "1";

                        } else {
                            iflike = "0";
                            myCollections.delOne(getPoem);
                        }
                        if (title.length() > 0 && content.length() > 0) {
                            result = myComment.insert(new Comments(
                                    tex_title.getText().toString(), feelsId,
                                    tex_content.getText().toString(), iflike, getPoem));
                        } else {
                            Toast.makeText(activity_poem_detail.this, "必须输入标题和内容！", Toast.LENGTH_SHORT).show();
                        }
                        if (result) {
                            tex_title.setText("");
                            tex_content.setText("");
                            pub_comment.dismiss();
                        }
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tex_title.setText("");
                        tex_content.setText("");
                        pub_comment.dismiss();
                    }
                });
            }
        });
    }

    @Event(R.id.img_speech)
    private void DialogSpeech(View v) {
        pub_comment.show();
    }


    @Event(R.id.img_like)
    private void checkLike(View v) {
        try {
            if (!ifLiked) {
                myCollections.insert(this.getPoem);
                img_like.setImageResource(R.drawable.pg_heart_chosed);
                Toast.makeText(this, "收藏成功！", Toast.LENGTH_SHORT).show();
                ifLiked = true;
            } else {
                myCollections.delOne(this.getPoem);
                ifLiked = false;
                img_like.setImageResource(R.drawable.pg_heart);
                Toast.makeText(this, "取消收藏成功！", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Log.i("poem insert!", e.toString());
        } finally {
            myCollections.close();
        }
    }

    @Event(R.id.img_poet_info)
    private void show_poet_info(View view) {
        new Get_Author(activity_poem_detail.this).execute(MyConfig.URLSEARCH_BY_AUTHOR_NAME + getPoem.getAuthors());

    }

    @Event(R.id.img_back)
    private void back(View view) {
        this.myCollections.close();
        finish();
    }
}
