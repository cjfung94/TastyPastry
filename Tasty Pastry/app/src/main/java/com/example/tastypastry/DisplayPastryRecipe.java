package com.example.tastypastry;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DisplayPastryRecipe extends AppCompatActivity {

    private TextView pastryRecipe;
    Bundle extras;
    private String recipeInfo;
    private String ingredientsInfo;
    private TextView pastryRecipeName;
    private TextView ingredientsList;
    private String recipeName;
    private String nodeKey;
    private ImageButton recipeButton;
    private ImageButton shareButton;
    DashBoardActivity dashBoardActivity;

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
        //Log.d("DisplayPastryRecipe", "ingredientsInfo" + ingredientsInfo);
        ingredientsList.setText(ingredientsInfo); // Sets it to the R ID

        //Set Recipe Name
        pastryRecipeName = findViewById(R.id.PastryName);
        recipeName = extras.getString("pastryName");
        pastryRecipeName.setText(recipeName);

        //Get NodeKey
        nodeKey = extras.getString("key");
        Log.d("Display", "profile" + extras.getString("key"));

        //Button
        recipeButton = (ImageButton) findViewById(R.id.addToFavorites);

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

    public void addToFavorites(View view) {
        recipeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                dashBoardActivity = new DashBoardActivity();
                dashBoardActivity.favoriteStar(nodeKey);

            }

        });
    }

    public void shareRecipe(View view)
    {
        shareButton = (ImageButton)findViewById(R.id.shareRecipe);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent,"Share using:"));
    }
}