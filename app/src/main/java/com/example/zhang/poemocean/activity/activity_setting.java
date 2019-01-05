package com.example.zhang.poemocean.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.zhang.poemocean.R;

import org.xutils.view.annotation.Event;
import org.xutils.x;

public class activity_setting extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        x.view().inject(this);


    }

    private void parseSettings() {
        SharedPreferences.Editor sp = getSharedPreferences("settings", Context.MODE_PRIVATE).edit();

    }

    @Event(R.id.img_back)
    private void GoBack(View view) {
        finish();
    }
}
