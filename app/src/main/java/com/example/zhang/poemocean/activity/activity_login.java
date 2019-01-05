package com.example.zhang.poemocean.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhang.poemocean.Db.Db_user;
import com.example.zhang.poemocean.Helper.MyConfig;
import com.example.zhang.poemocean.MainActivity;
import com.example.zhang.poemocean.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class activity_login extends Activity {

    @ViewInject(R.id.eye_check)
    private ImageView eye_check;
    @ViewInject(R.id.account)
    private EditText account;
    @ViewInject(R.id.password)
    private EditText password;
    @ViewInject(R.id.register)
    private TextView register;
    @ViewInject(R.id.forget_pass)
    private TextView forget_pass;
    @ViewInject(R.id.btn_register)
    private Button btn_login;

    @ViewInject(R.id.cb_remember_pass)
    private CheckBox cb_save;

    private int eyeChecked = 0;

    private Db_user user_db;
    private MyConfig myConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        x.view().inject(this);

        user_db = new Db_user(this);
        myConfig = new MyConfig(this);
        user_db.getReadableDatabase();
        init();
    }

    private void init() {
        btnListener myListener = new btnListener();
        eye_check.setOnClickListener(myListener);
        register.setOnClickListener(myListener);
        forget_pass.setOnClickListener(myListener);
        btn_login.setOnClickListener(myListener);

        register.setTypeface(myConfig.getTf());
        btn_login.setTypeface(myConfig.getTf());
        account.setTypeface(myConfig.getTf());
        password.setTypeface(myConfig.getTf());
        forget_pass.setTypeface(myConfig.getTf());
        cb_save.setTypeface(myConfig.getTf());


        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        String s_account = sp.getString("account", "");
        String s_pwd = sp.getString("pwd", "");
        if (s_account.length() > 0) {
            cb_save.setChecked(true);
            //如果上一次已经记住了登录，就直接跳转
//            Intent mIntent = new Intent(this, MainActivity.class);
//            startActivity(mIntent);
//            finish();
        }
        account.setText(s_account);
        password.setText(s_pwd);

    }


    private class btnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_register: {  //登录按钮
                    Intent mIntent = new Intent(v.getContext(), MainActivity.class);
                    String s_account = account.getText().toString();
                    String s_password = password.getText().toString();

                    SharedPreferences.Editor sp = getSharedPreferences("user", Context.MODE_PRIVATE).edit();

                    if (s_account.length() < 1 || s_password.length() < 1) {
                        Toast.makeText(activity_login.this, "请输入账号和密码！", Toast.LENGTH_LONG).show();

                    } else {
                        if (s_account.contentEquals("zzx") && s_password.equals("123")) {
                            Toast.makeText(activity_login.this, "登录成功！\nWelcome !" + s_account, Toast.LENGTH_LONG).show();
                            if (cb_save.isChecked()) {
                                sp.putString("account", s_account);
                                sp.putString("pwd", s_password);

                            } else {
                                sp.putString("account", "");
                                sp.putString("pwd", "");
                            }
                            sp.commit();

                            startActivity(mIntent);
                            finish();
                        } else {

                            Toast.makeText(activity_login.this, "密码或用户名错误！", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
                case R.id.forget_pass:  //忘记密码
                    makeToast("忘记密码！");
                    break;
                case R.id.register:  //注册用户
                    makeToast("注册用户！");
                    startActivity(new Intent(activity_login.this, activity_register.class));
                    break;
                case R.id.eye_check: {  //显示密码
                    if (eyeChecked == 1) {//
                        eye_check.setBackgroundResource(R.drawable.ic_eye);
                        eyeChecked = 0;
                        password.setTransformationMethod(new PasswordTransformationMethod());
                    } else { //显示密码
                        eye_check.setBackgroundResource(R.drawable.ic_eye_slash);
                        eyeChecked = 1;
                        password.setTransformationMethod(null);
                    }
                }
                break;
                default:
                    break;
            }
        }
    }

    public void makeToast(String flag) {
        Context context = getApplicationContext();
        CharSequence text = flag;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
