package com.example.myapplication;

public class User {
    String password;
    String username;

    public User(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
