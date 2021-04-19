package com.example.tastypastry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private EditText filterEditText2;
    private EditText filterEditText3;
    private String filterText;
    private String filterText2;
    private String filterText3;
    private Button filterButton;
    private static DatabaseReference filterDatabase;
    private FirebaseAuth firebaseAuth;
    private String userId;
    private String ingredients;
    private static DatabaseReference userDatabase;
    private boolean recipeExist;
    private String nodeKey;
    private String className;
    DashBoardActivity dashBoardActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getUid();
        filterDatabase = FirebaseDatabase.getInstance().getReference().child("UserList").child(userId).child("filterList");
        userDatabase = FirebaseDatabase.getInstance().getReference().child("UserList");
        className = this.getClass().getSimpleName();


        // Assign EditText to a ID
        filterEditText = (EditText) findViewById(R.id.filter_editText1);
        filterEditText2 = (EditText) findViewById(R.id.filter_editText2);
        filterEditText3 = (EditText) findViewById(R.id.filter_editText3);
        filterButton = (Button) findViewById(R.id.filter_button);
        Log.d("Filter", "onCreate " );
        userDatabase.child(userId).child("filterList").removeValue();


        //Click button to search
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchIngredient();
                Intent intent = new Intent(Filter.this, DashBoardActivity.class);
                intent.putExtra("className",className);
                startActivity(intent);

                            Log.d("Filter", "in second data onchange" + ingredients );

//                filterDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        Log.d("snapshotondataonchange", "snapshotondatachange" + snapshot);
//
//                        if (snapshot.hasChildren()) {
//                            Intent intent = new Intent(Filter.this, DashBoardActivity.class);
//                            intent.putExtra("className",className);
//                            startActivity(intent);
//                            Log.d("Filter", "in second data onchange" + ingredients );
//                            finish();
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
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
                        Intent intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("className", this.getClass().getSimpleName());
                        startActivity(intent);
                        overridePendingTransition(0, 0);
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

        filterText = filterEditText.getText().toString().toLowerCase();
        filterText2 = filterEditText2.getText().toString().toLowerCase();
        filterText3 = filterEditText3.getText().toString().toLowerCase();
        Log.d("filter text","filtertext2" + filterText2);
        Log.d("filter text","filtertext3" + filterText3);

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
                    ingredients = filterProfile.getIngredients().toLowerCase();
                    Log.d("Filter", "contains it" + ingredients );
                    //Assign the string and put ingredients inside of the string

                    //Check if string matches our ingredients
                    if (ingredients.contains(filterText)&&ingredients.contains(filterText2)&&ingredients.contains(filterText3))
                    {
                        Log.d("Filter", "contains it" );
                        //Add the profile to the filterList in Database
                        filterDatabase.child(nodeKey).setValue(filterProfile);
                        recipeExist = true;
                    }
//                    else{
//                        Toast.makeText(Filter.this, "No results found", Toast.LENGTH_SHORT).show();
//                        recipeExist = false;
//                    }
                }

                //Leave page if it exists, if not, stay on Filter page
//                if (filterDatabase.hasChildren()){
//                    Intent intent = new Intent(Filter.this, DashBoardActivity.class);
//                    intent.putExtra("className",className);
//                    startActivity(intent);
//                    finish();
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}