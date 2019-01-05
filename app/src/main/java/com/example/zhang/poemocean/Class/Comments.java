package com.example.zhang.poemocean.Class;

public class Comments {
    private String title;
    private String feelid;
    private String content;
    private String iflike;

    private Poem thePoem;
    private int UserId;

    public Comments(String title, String feelid, String content, String iflike, Poem thePoem) {
        this.title = title;
        this.feelid = feelid;
        this.content = content;
        this.iflike = iflike;
        this.thePoem = thePoem;

    }

    public Comments(String title, String feelid, String content, String iflike) {
        this.title = title;
        this.feelid = feelid;
        this.content = content;
        this.iflike = iflike;

    }

    public String getContent() {
        return content;
    }

    public String getFeelid() {
        return feelid;
    }

    public String getIflike() {
        return iflike;
    }

    public String getTitle() {
        return title;
    }

    public Poem getThePoem() {
        return thePoem;
    }

    public String getHeader() {
        return "<" + this.title + ">" + this.content;
    }
}
