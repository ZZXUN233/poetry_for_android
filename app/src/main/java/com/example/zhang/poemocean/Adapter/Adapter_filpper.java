package com.example.zhang.poemocean.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.zhang.poemocean.R;

public class Adapter_filpper extends BaseAdapter {

    Context ctx;
    int[] images;
    LayoutInflater inflater;

    public Adapter_filpper(Context context, int[] myImages) {
        this.ctx = context;
        this.images = myImages;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        Log.i("getItem", position + "");
        return position;
    }

    @Override
    public long getItemId(int position) {
//        Log.i("getItemId", position + "");
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.flipper_items, null);
        ImageView imgs = (ImageView) view.findViewById(R.id.flipper_img);
        imgs.setImageResource(images[i]);
        return view;
    }


}
