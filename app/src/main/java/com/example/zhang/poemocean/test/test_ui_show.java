package com.example.zhang.poemocean.test;

import android.app.Activity;
import android.os.Bundle;

import com.example.zhang.poemocean.R;

public class test_ui_show extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = getIntent().getIntExtra("id", -1);

        switch (id) {
            case 1:
                setContentView(R.layout.poem_list);
                break;
            case 2:
                setContentView(R.layout.poem_detail);
                break;
            case 3:
                setContentView(R.layout.poet_info);
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            default:
                setContentView(R.layout.poem_detail);
                break;
        }
    }
}
