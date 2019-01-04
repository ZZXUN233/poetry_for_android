package com.example.zhang.poemocean.Url;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Helper_GetUrl extends AsyncTask<String, Void, String> {

    ImageView iv;
    TextView tv;
    TextView tv_content;
    TextView tv_note;
    String PicUrl = "";
    String contenUrl = "";
    String noteUrl = "";

    ListView myLis;
    Context ctx;

    private int type;


    private List<String> lis_names = new ArrayList<String>();

    private List<View> views = new ArrayList<View>();

    public Helper_GetUrl(TextView tx) {
        this.type = 0;
        this.tv = tx;
    }

    public Helper_GetUrl(Context ctx, ListView lis) {
        this.ctx = ctx;
        this.myLis = lis;
    }


    //后台执行的线程，获取网络资源
    @Override
    protected String doInBackground(String... arg0) {
        try {
            Log.i("AsyncTask test", Thread.currentThread().getName());
            URL url = new URL(arg0[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String content = buffer.toString();
            return content;
        } catch (Exception e) {
            Log.i("AsyncTask", "download failed!");
            System.out.println(e.getMessage());
//            Log.i("AsyncTask", e.getMessage());

        }
        return null;
    }


    //获得结果后在主线程中运行
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("test", Thread.currentThread().getName());
    }

    //负责解析获取到的Json数据并写入到对应的控件中显示


}
