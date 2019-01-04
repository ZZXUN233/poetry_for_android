package com.example.zhang.poemocean.Class;

import android.util.Log;

public class Poem {

    private String title;
    private String authors;
    private String content;

    public Poem(String title, String authors, String content) {
        this.title = title;
        this.authors = authors;
        this.content = content;

    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthors() {
        return this.authors;
    }

    public String getHead() {
        return this.title + "[" + this.authors + "]";
    }

    public String getContent() {
//        String con = this.content.replace("，", ",|");
        String con = this.content.replace("|", "\n");
//        Log.i("poem", con);
        return con;
    }

    public boolean ifEquel(Poem aPoem) {
        if (this.title.equals(aPoem.getTitle())) {
            if (this.authors.equals(aPoem.getAuthors())) {
                if (this.content.equals(aPoem.getContent())) {
                    return true;
                }
            }
        }
        return false;
    }
}
