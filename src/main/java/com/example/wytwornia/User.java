package com.example.wytwornia;

public class User {

    private String login;
    private String password;
    private int admin; // 0 - normalny user, 1 - admin
    private int settings; // 11 - white theme + polski, 12 - whitetheme + angielski, 21 - dark theme + polski, 22 - dark theme + angielski


    public User(){
        this.login = "";
        this.password = "";
        this.admin = 0;
        this.settings = 11;
    }
    public User(String login, String password, int admin, int settings){
        this.login = login;
        this.password = password;
        this.admin = admin;
        this.settings = settings;
    }


}


