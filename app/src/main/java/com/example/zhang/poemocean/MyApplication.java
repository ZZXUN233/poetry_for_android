package com.example.zhang.poemocean;

import android.app.Application;

import org.xutils.*;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);

    }
}
