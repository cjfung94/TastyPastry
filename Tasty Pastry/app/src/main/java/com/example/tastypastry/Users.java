package com.example.tastypastry;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class Users {
    private String email;
    private String userId;
    private FirebaseAuth firebaseAuth;

//    public Users () {
//        firebaseAuth = FirebaseAuth.getInstance();
//        userId = firebaseAuth.getCurrentUser().getUid();
//    }

    public void setEmail(String email){
        this.email = email;
        Log.d("Users", "is " + email);
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
