package com.example.zhang.poemocean.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhang.poemocean.Class.User;
import com.example.zhang.poemocean.Db.Db_user;
import com.example.zhang.poemocean.Helper.MyConfig;
import com.example.zhang.poemocean.R;

import org.xutils.view.annotation.Event;
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

    @ViewInject(R.id.eye_check)
    private ImageView eye_check;

    private Db_user allUsers;
    private MyConfig myConfig;

    private int eyeChecked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        x.view().inject(this);

        allUsers = new Db_user(this);
        myConfig = new MyConfig(this);
        et_account.setText(getIntent().getStringExtra("account"));
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

    private boolean add_user() {
        String account = et_account.getText().toString();
        String email = et_email.getText().toString();
        String pwd = et_password.getText().toString();
        try {
            if (account.length() < 1) {
                Toast.makeText(activity_register.this, "请输入用户名！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (email.length() < 1) {
                Toast.makeText(activity_register.this, "请输入邮箱！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (pwd.length() < 6) {
                Toast.makeText(activity_register.this, "密码长度不少于6位！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (allUsers.ifNameUsed(account)) {
                Toast.makeText(activity_register.this, "用户名已被注册！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (allUsers.ifEmailUsed(email)) {
                Toast.makeText(activity_register.this, "邮箱已被注册！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (allUsers.insert(new User(account, pwd, email))) {
                Toast.makeText(activity_register.this, "用户注册成功！", Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(activity_register.this, activity_login.class);
                mIntent.putExtra("account", account);
                mIntent.putExtra("pwd", pwd);
                startActivity(mIntent);
                return true;

            }
        } catch (Exception e) {
            Toast.makeText(activity_register.this, "出现了莫名奇妙的错误！", Toast.LENGTH_SHORT).show();
        }
        return false;

    }

    @Event(R.id.eye_check)
    private void eyeCheck(View view) {
        if (eyeChecked == 1) {//
            eye_check.setBackgroundResource(R.drawable.ic_eye);
            eyeChecked = 0;
            et_password.setTransformationMethod(new PasswordTransformationMethod());
        } else { //显示密码
            eye_check.setBackgroundResource(R.drawable.ic_eye_slash);
            eyeChecked = 1;
            et_password.setTransformationMethod(null);
        }
    }

    @Event(R.id.btn_register)
    private void register(View view) {
        if (add_user()) {
            finish();
        } else {
            Toast.makeText(activity_register.this, "出现了莫名奇妙的错误！", Toast.LENGTH_SHORT).show();
        }
    }
}
