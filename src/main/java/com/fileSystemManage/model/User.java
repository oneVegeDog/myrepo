package com.fileSystemManage.model;

import java.util.Date;

public class User {

    private String username;
    private String userId;
    private String registerDate;
    private Integer userStatue;

    public User(){}
    
    public User(String username, String ID, String registerDate, Integer userStatue) {
        this.username = username;
        this.userId = ID;
        this.registerDate = registerDate;
        this.userStatue = userStatue;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setID(String userId) {
        this.userId = userId;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public Integer getUserStatue() {
        return userStatue;
    }

    public void setUserStatue(Integer userStatue) {
        this.userStatue = userStatue;
    }
}
