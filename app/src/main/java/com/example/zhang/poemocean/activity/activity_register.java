package com.example.zhang.poemocean.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zhang.poemocean.Db.Db_user;
import com.example.zhang.poemocean.Helper.MyConfig;
import com.example.zhang.poemocean.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class activity_register extends Activity {

    @ViewInject(R.id.main_title)
    private TextView tex_main_title;

    @ViewInject(R.id.account)
    private EditText et_account;

    @ViewInject(R.id.email)
    private EditText et_email;

    @ViewInject(R.id.password)
    private EditText et_password;

    @ViewInject(R.id.btn_register)
    private Button btn_register;

    private Db_user allUsers;
    private MyConfig myConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        x.view().inject(this);

        allUsers = new Db_user(this);
        myConfig = new MyConfig(this);
        init();
    }

    private void init() {

        //设置字体
        tex_main_title.setTypeface(myConfig.getTf());
        et_account.setTypeface(myConfig.getTf());
        et_email.setTypeface(myConfig.getTf());
        et_password.setTypeface(myConfig.getTf());
        btn_register.setTypeface(myConfig.getTf());

    }


}
