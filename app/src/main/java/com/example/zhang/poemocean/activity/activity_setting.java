package com.example.zhang.poemocean.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.zhang.poemocean.R;

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
}
