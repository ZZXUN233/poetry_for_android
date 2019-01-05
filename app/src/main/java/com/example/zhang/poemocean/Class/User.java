package com.example.zhang.poemocean.Class;

import com.example.zhang.poemocean.person_page.Publish;

public class User {
    private String account;
    private String pwd;
    private String name;
    private String email;

    private int likes;
    private int pubs;
    private int coms;

    public User(String account, String pwd, String name, String email) {
        this.account = account;
        this.pwd = pwd;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
    }

    public String getPwd() {
        return pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
