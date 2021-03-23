package com.example.tastypastry;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.View;


public class Recipe{


    private String thatRecipe;

    private TextView pastryText;



    //Testing to see if I can just call this function so it won't be void
    protected void setThatRecipe(String thatRecipe){
        this.thatRecipe = thatRecipe;
    }

    protected String getThatRecipe(){
        return thatRecipe;
    }

    protected void setRecipeFromProfile(Profile profile){
        thatRecipe = profile.getRecipe();
        Log.d("Recipe", "Info : " + thatRecipe);
    }
}
