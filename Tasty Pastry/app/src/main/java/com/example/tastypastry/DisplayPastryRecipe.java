package com.example.tastypastry;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mindorks.placeholderview.annotations.View;

public class DisplayPastryRecipe extends AppCompatActivity {

    private ImageView PastryImage;
    private Profile testProfile;
    private ProgressDialog progressDialog;

    @View(R.id.IngredientsList)
    private TextView IngredientsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes);
        ImageView pi = (ImageView) findViewById(R.id.PastryImage);
        pi.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v){
                //openRecipe();
                progressDialog.setMessage("HELLO..");
            }
        });
    }

    public void openRecipe() {

//        IngredientsList.setText(testProfile.getIngredients());
    }
}