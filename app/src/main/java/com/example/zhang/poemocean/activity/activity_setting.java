package com.example.zhang.poemocean.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhang.poemocean.Db.Db_readed;
import com.example.zhang.poemocean.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class activity_setting extends Activity {

    @ViewInject(R.id.tex_size)
    private TextView tex_size;
    private Db_readed readLog;

    private int logSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        x.view().inject(this);
        init();
    }

    private void init() {
        readLog = new Db_readed(this);
        logSize = readLog.getSize();
        tex_size.setText("本地有" + logSize + "条最近阅读记录！");
    }


    private void parseSettings() {
        SharedPreferences.Editor sp = getSharedPreferences("settings", Context.MODE_PRIVATE).edit();
    }

    @Event(R.id.tex_clean)
    private void clear(View view) {
        if (readLog.delAll()) {
            Toast.makeText(activity_setting.this, logSize + "条记录已被删除！", Toast.LENGTH_SHORT).show();
            init();
        } else {
            Toast.makeText(activity_setting.this, "清除缓存失败！", Toast.LENGTH_SHORT).show();
        }
    }

    @Event(R.id.img_back)
    private void GoBack(View view) {
        finish();
    }

}
