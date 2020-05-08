package com.model;

/**
 * @author zzx
 * 2020/4/29 21:10
 */
public class Manager {
    private String userName;
    private String password;

    public Manager() {
    }

    public Manager(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
