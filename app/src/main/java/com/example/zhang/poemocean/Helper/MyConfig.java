package com.example.zhang.poemocean.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import com.example.zhang.poemocean.Url.Get_Random_one;

import java.text.Format;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyConfig {

    private Typeface tf;
    private Context ctx;

    public int NUM_LIS_PAGES = 123;
    public static int NUM_LIS_COUNT = 60;
    public int NUM_FIRST_PAGE_LIST = 10;


    public static final String URL_SINGLE_LINE = "https://api.apiopen.top/singlePoetry";

    public static final String URL_SEARCH_LIKE = "https://api.apiopen.top/likePoetry?name=";
    public static final String URL_POEM_LIST = "https://api.apiopen.top/getSongPoetry?page=%d&count=%d";

    public static final String URL_SEARCH_BY_NAME = "https://api.apiopen.top/searchPoetry?name=";
    public static final String URL_SEARCH_BY_SONG = "https://api.apiopen.top/getSongPoetry?page=%d&count=%d";
    public static final String URL_SEARCH_BY_TANG = "https://api.apiopen.top/getTangPoetry?page=%d&count=%d";
    public static final String URLSEARCH_BY_AUTHOR_NAME = "https://api.apiopen.top/searchAuthors?name=";

    public MyConfig(Context ctx) {
        this.ctx = ctx;
        this.tf = Typeface.createFromAsset(this.ctx.getAssets(), "fonts/font_ink.ttf");

    }

    public Typeface getTf() {
        return this.tf;
    }


    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }

    public static String getUrlPoemList(int page) {
        return String.format(URL_POEM_LIST, page, NUM_LIS_COUNT);
    }

    public static String getUrlPoemSong(int page) {
        return String.format(URL_SEARCH_BY_SONG, page, NUM_LIS_COUNT);
    }

    public static String getUrlPoemTang(int page) {
        return String.format(URL_SEARCH_BY_TANG, page, NUM_LIS_COUNT);
    }


}
