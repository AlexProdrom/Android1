package com.example.liis.listviewtutorial;

import java.io.Serializable;

/**
 * Created by Liis on 14/03/2017.
 */

public class UserAccount implements Serializable {


    private  String userName;
    private  String userType;
    private boolean active;

    public  UserAccount(String name, String type, boolean active){
        this.userName = name;
        this.userType = type;
        this.active=active;
    }

    public  UserAccount(String name, String type){
        this.userName = name;
        this.userType = type;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public  boolean isActive(){
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString(){
        return  this.userName + " ("+this.userType+")";
    }



}
