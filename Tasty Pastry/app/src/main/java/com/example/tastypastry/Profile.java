package com.example.tastypastry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    //Serialize reads json file and binds to model
    //Expose makes variable readable to gson - if we use gson
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("image")
    @Expose
    private String imageUrl;

    @SerializedName("recipe")
    @Expose
    private String recipe;

    @SerializedName("ingredients")
    @Expose
    private String ingredients;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return imageUrl;
    }

    public void setImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getIngredients() {
        return ingredients;
    }
    public void setIngredients(String recipe) {
        this.ingredients = ingredients;
    }

}

