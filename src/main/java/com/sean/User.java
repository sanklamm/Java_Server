package com.sean;

/**
 * Created by sean on 11.07.17.
 */
public class User {
    String username;
    String password;
    String userDir;

    public User(String username, String password, String userDir) {
        this.username = username;
        this.password = password;
        this.userDir = userDir;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserDir() {
        return userDir;
    }

    public void setUserDir(String userDir) {
        this.userDir = userDir;
    }
}
