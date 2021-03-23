package com.example.tastypastry;

import android.app.ProgressDialog;
import android.os.Bundle;
<<<<<<< HEAD
import android.util.Log;
=======
import android.view.View;
>>>>>>> cb5a9f2a37a597b7e46c59db2caae692b67aa164
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mindorks.placeholderview.annotations.View;

=======
>>>>>>> cb5a9f2a37a597b7e46c59db2caae692b67aa164
public class DisplayPastryRecipe extends AppCompatActivity {

    private ImageView PastryImage;
    private Profile testProfile;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    private TextView pastryRecipe;
    Recipe displayRecipe = new Recipe();
    private String recipeInfo = displayRecipe.getThatRecipe();

//    @View(R.id.IngredientsList)
//    private TextView IngredientsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recipes);
        pastryRecipe = findViewById(R.id.PastryRecipe);
        pastryRecipe.setText("Hello" + displayRecipe.getThatRecipe());



       setContentView(R.layout.recipes);

    }

    public void openRecipe(View v) {
//        setContentView(R.layout.recipes);
//        IngredientsList.setText(testProfile.getIngredients());

    }


}