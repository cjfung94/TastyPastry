package com.example.tastypastry;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mindorks.placeholderview.annotations.View;

public class DisplayPastryRecipe extends AppCompatActivity {

    private ImageView PastryImage;
    private Profile testProfile;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    private TextView pastryRecipe;
    Recipe displayRecipe = new Recipe();
    Bundle extras;
    private String recipeInfo;

//    @View(R.id.IngredientsList)
//    private TextView IngredientsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes);
        extras = getIntent().getExtras();
        recipeInfo = extras.getString("recipe");
        pastryRecipe = findViewById(R.id.PastryRecipe);
        pastryRecipe.setText(recipeInfo);
        Log.d("Recipe", "is " + recipeInfo);





    }
    public void openRecipe(View v) {
//        setContentView(R.layout.recipes);
//        IngredientsList.setText(testProfile.getIngredients());

    }


}