package com.example.tastypastry;

import java.util.List;

public class Users {
    private String email;
    private String userId;
    public Users (String email, String userId){
        this.email = email;
        this.userId = userId;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }
}
