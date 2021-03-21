package com.example.tastypastry;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayPastryRecipe extends AppCompatActivity {

    private ImageView PastryImage;
    private Profile testProfile;
    private ProgressDialog progressDialog;

//    @View(R.id.IngredientsList)
//    private TextView IngredientsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recipes);


       setContentView(R.layout.recipes);

    }

    public void openRecipe(View v) {
//        setContentView(R.layout.recipes);
//        IngredientsList.setText(testProfile.getIngredients());

    }
}