package com.fajicskills.chatkitapp.model;

import java.io.Serializable;

/**
 * Created by angebagui on 15/03/2017.
 */

public class User implements Serializable{

    private String name;

    private String avatar;

    public User() {

    }

    public User(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
