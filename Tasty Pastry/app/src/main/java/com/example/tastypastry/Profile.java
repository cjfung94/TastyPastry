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

<<<<<<< HEAD
    @SerializedName("recipe")
    @Expose
    private String recipe;
=======
//    @SerializedName("ingredients")
//    @Expose
//    private String ingredients;
>>>>>>> cb5a9f2a37a597b7e46c59db2caae692b67aa164


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getImage(){
        return imageUrl;
    }
<<<<<<< HEAD

    public void setImage(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public String getRecipe() { return recipe;}

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
=======
//
//    public void setImage(String imageUrl){
//        this.imageUrl = imageUrl;
//    }
//
//    public String getIngredients() { return ingredients;}
//
//    public void setIngredients(String ingredients) {
//        this.ingredients = ingredients;
//    }
>>>>>>> cb5a9f2a37a597b7e46c59db2caae692b67aa164
}
