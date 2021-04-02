package com.example.tastypastry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class Filter extends AppCompatActivity {

    private EditText filterEditText;
    private String filterText;
    private Button filterButton;
    private static DatabaseReference filterDatabase;
    private FirebaseAuth firebaseAuth;
    private String userId;
    private String ingredients;
    private static DatabaseReference userDatabase;
    private boolean recipeExist;
    private String nodeKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getUid();
        filterDatabase = FirebaseDatabase.getInstance().getReference().child("UserList").child(userId);
        userDatabase = FirebaseDatabase.getInstance().getReference().child("UserList");

        // Assign EditText to a ID
        filterEditText = (EditText) findViewById(R.id.filter_editText);
        filterButton = (Button) findViewById(R.id.filter_button);
        Log.d("Filter", "onCreate " );


        //Click button to search
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchIngredient();
                //Move back to dashboard
//                Intent intent = new Intent(Filter.this, DashBoardActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

        //Navigation Bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.Filter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Filter:
                        return true;
                    case R.id.Favorites:
                        startActivity(new Intent(getApplicationContext(), Favorites.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Settings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    //Creating the search function
    public void searchIngredient(){
        //Get string from EditText and sets it to lowercase
        userDatabase.child(userId).child("filterList").removeValue();
        filterText = filterEditText.getText().toString().toLowerCase();
        //Go through database and look for ingredients
        Log.d("Filter", "inside of searchIngredients")  ;
        userDatabase.child(userId).child("userListRecipe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Gson gson = new Gson();

                for (DataSnapshot filterSnap : snapshot.getChildren())
                {

                    //Assign snapshot child to a Profile class
                    nodeKey = filterSnap.getKey();
                    String json = new Gson().toJson(filterSnap.getValue());
                    Profile filterProfile = gson.fromJson(json, Profile.class);
                    ingredients = filterProfile.getIngredients();
                    Log.d("Filter", "contains it" + ingredients );
                    //Assign the string and put ingredients inside of the string


                    //Check if string matches our ingredients
                    if (ingredients.contains(filterText))
                    {
                        Log.d("Filter", "contains it" );
                        //Add the profile to the filterList in Database
                        filterDatabase.child("filterList").child(nodeKey).setValue(filterProfile);
                        recipeExist = true;
                    }
                    else{
                        Toast.makeText(Filter.this, "No results found", Toast.LENGTH_SHORT).show();
                        recipeExist = false;
                    }
                }

                //Leave page if it exists, if not, stay on Filter page
                if (recipeExist){
                    Intent intent = new Intent(Filter.this, DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}