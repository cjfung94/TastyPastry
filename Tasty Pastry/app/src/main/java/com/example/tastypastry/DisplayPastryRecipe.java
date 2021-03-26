package com.example.tastypastry;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DisplayPastryRecipe extends AppCompatActivity {

    private TextView pastryRecipe;
    Bundle extras;
    private String recipeInfo;
    private String ingredientsInfo;
    private TextView pastryRecipeName;
    private TextView ingredientsList;
    private String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes);

        //Get recipe info from SwipeFunction
        extras = getIntent().getExtras();
        recipeInfo = extras.getString("recipe");
        pastryRecipe = findViewById(R.id.instructions_list);
        pastryRecipe.setText(recipeInfo); // Sets it to the R ID

        //Get ingredients
        ingredientsInfo = extras.getString("ingredients");
        ingredientsList = findViewById(R.id.ingredients_list);
        ingredientsList.setText(ingredientsInfo); // Sets it to the R ID

        //Set Recipe Name
        pastryRecipeName = findViewById(R.id.PastryName);
        recipeName = extras.getString("pastryName");
        pastryRecipeName.setText(recipeName);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.Home:
                                startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
                                overridePendingTransition(0, 0);
                                return true;
                            case R.id.Filter:
                                startActivity(new Intent(getApplicationContext(), Filter.class));
                                overridePendingTransition(0, 0);
                                return true;
                            case R.id.Favorites:
                                startActivity(new Intent(getApplicationContext(), Favorites.class));
                                overridePendingTransition(0, 0);
                                return true;
                            case R.id.Settings:
                                startActivity(new Intent(getApplicationContext(), Settings.class));
                                overridePendingTransition(0, 0);
                                return true;
                        }
                        return false;
                    }
                });
    }

}