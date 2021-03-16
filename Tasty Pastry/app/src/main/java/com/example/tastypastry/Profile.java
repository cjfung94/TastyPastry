package com.example.tastypastry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    //Serialize reads json file and binds to model
    //Expose makes variable readable to gson
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("url")
    @Expose
    private String imageUrl;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getImage(){
        return imageUrl;
    }

    public void setImage(String imageUrl){
        this.imageUrl = imageUrl;
    }

}
