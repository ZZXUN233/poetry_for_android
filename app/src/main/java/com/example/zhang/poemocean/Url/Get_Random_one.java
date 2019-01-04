package com.example.zhang.poemocean.Url;

import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

public class Get_Random_one extends Helper_GetUrl {
    public Get_Random_one(TextView tx) {
        super(tx);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        JsonParse(s);

    }

    private void JsonParse(String s) {
        System.out.println("下载到内容" + s);
        try {
            JSONObject obj = new JSONObject(s);
            JSONObject result = obj.getJSONObject("result");
            String content = result.getString("content");
//            String title = result.getString("title") + "——" + result.getString("authors") + "\n";
//            content = title + content.replace("|", "\n");
            content = content.replace("|", "\n");
            this.tv.setText(content);
        } catch (Exception e) {
            Log.d("json", e.getMessage());
            Log.d("json", "parse failed!");
        }


    }
}
