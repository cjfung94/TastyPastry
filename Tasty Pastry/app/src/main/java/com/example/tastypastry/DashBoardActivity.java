package com.example.tastypastry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashBoardActivity extends Activity {
    private SwipePlaceHolderView testSwipe;
    private Context testContext;
    private static DatabaseReference mDatabase;
    Bundle extras;
    private static String userID;
    private String userEmail;
    private String nodeKey;
    private String className;
    private SwipePlaceHolderView signUpSwipe;
    private DatabaseReference userDatabase;
    private DatabaseReference favoriteDatabase;
    private DatabaseReference userListDatabase;
    private static FirebaseAuth firebaseAuth;
    private ImageButton TutorialLight;
    private boolean undo;
    //Static is used so that all variables can be shared and used in this class
    private static Profile currentProfile;
    private static String currentNodeKey;

    Profile recipeProfile = new Profile();

    // List of Profile class
    List<Profile> profileList = new ArrayList<Profile>();

    // Create Hashmap to save values inside of FireBase
    HashMap<String, Object> map = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        firebaseAuth = FirebaseAuth.getInstance();
        userDatabase = FirebaseDatabase.getInstance().getReference();
        testContext = getApplicationContext();
        userID = firebaseAuth.getCurrentUser().getUid();


        undo = false;


        //values are here from signin/out if we need them

        // Swiping stuff
        testSwipe = (SwipePlaceHolderView) findViewById(R.id.swipeView);
        // Creates recipes seen
        //if else statement with class name
        //Use putExtra and getString to get className -> class.simpleName() I think from the previous class
        //Do it from MainActivity and Filter, inside the intent section

        createDisplayRecipes();

        // NAVIGATION BAR:
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Home);
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
                        currentNodeKey = null;
                        currentProfile = null;
                        startActivity(new Intent(getApplicationContext(), Filter.class));
                        overridePendingTransition(0, 0);

                        return true;
                    case R.id.Favorites:
                        currentNodeKey = null;
                        currentProfile = null;
                        startActivity(new Intent(getApplicationContext(), Favorites.class));
                        overridePendingTransition(0, 0);

                        return true;
                    case R.id.Settings:
                        currentNodeKey = null;
                        currentProfile = null;
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        TutorialLight = findViewById(R.id.tut_lightbulb);
        TutorialLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, TutorialActivity.class);
                //might need to put extra class name if changes made to DashboardActivity - null parameter?
                intent.putExtra("className", this.getClass().getSimpleName());
                startActivity(intent);

            }
        });
    }
    // Sign in - Display user's list of recipes
    protected void createDisplayRecipes() { // Put inside parameter, String className
        //If else statement using the className
        // if (className.equals("Filter")
        //Lines 111 -> mDatabase.child("FilterList") because that's what you want
        //else
        // it will be mDatabase.child("userListRecipe")
        // Fix this part
//        testSwipe = (SwipePlaceHolderView) findViewById(R.id.swipeView);
        //firebaseAuth = FirebaseAuth.getInstance();
        //userID = firebaseAuth.getCurrentUser().getUid();
        extras = getIntent().getExtras();
        className = extras.getString("className");

            testSwipe.getBuilder().setDisplayViewCount(3).setIsUndoEnabled(true)
                    .setSwipeDecor(new SwipeDecor().setPaddingTop(20).setRelativeScale(0.01f));
            mDatabase = FirebaseDatabase.getInstance().getReference().child("UserList").child(userID);

            if (extras.getString("className").equals("Filter")) {
                mDatabase = mDatabase.child("filterList");
                Log.d("dashboard", "if" );
            } else {
                mDatabase = mDatabase.child("userListRecipe");
                Log.d("dashboard", "else");
            }


            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Gson gson = new Gson();
                    for (DataSnapshot postSnapShot : snapshot.getChildren()) {
                        // Is there another way to do this or simplify this? Need for Json and Gson?
                        nodeKey = postSnapShot.getKey();
                        String json = new Gson().toJson(postSnapShot.getValue());
                        Profile profile = gson.fromJson(json, Profile.class);
                        Log.d("DashBoardActivity", " image " + profile.getIngredients());
                        testSwipe.addView(new SwipeFunction(testContext, profile, testSwipe, nodeKey));
                        // profile.setKey(nodeKey);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }

    // Sign Up
    // Create Copy of List of Recipes for every user logged
    protected void setUpUserRecipe(String userID) {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("recipeList");
        mDatabase.addValueEventListener(new ValueEventListener() {
            Gson gson = new Gson();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapShot : snapshot.getChildren()) {
                    // Is there another way to do this or simplify this? Need for Json and Gson?
                    String json = new Gson().toJson(postSnapShot.getValue());
                    Profile profile = gson.fromJson(json, Profile.class);
                    profile.setKey(postSnapShot.getKey());
                    profileList.add(profile);
                }
                // Put the list inside the database
                mDatabase = FirebaseDatabase.getInstance().getReference().child("UserList").child(userID);
                mDatabase.child("userListRecipe").setValue(profileList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Reading from database failed: " + error.getMessage());
            }
        });
    }

    // SwipeFunction calls this method to add to Favorites | Instantiate everything
    //ADD TO FAVORITES
    public void addRecipeToDatabase(Profile profile) {
        // Get the current userID

        //firebaseAuth = FirebaseAuth.getInstance();
        //userID = firebaseAuth.getCurrentUser().getUid();

        // Add recipes to the current user's list in Database
        favoriteDatabase = FirebaseDatabase.getInstance().getReference().child("UserList").child(userID)
                .child("Favorites");
        // Push creates a unique value for each, we don't need to check since we're
        // going to delete it from the list
        // Remove addView from SwipeFunction SwipeIn/SwipeOut after testing is complete
        favoriteDatabase.child(profile.getKey()).setValue(profile);

        //Set Current Profile
        currentProfile = profile;
    }

    // Delete from user's display list after a left or right swipe
    public void deleteFromUserListRecipe(String nodeKey, Profile profile) {
        Log.d("nodekey", "is " + nodeKey);
       // firebaseAuth = FirebaseAuth.getInstance();
       // userID = firebaseAuth.getCurrentUser().getUid();

        userListDatabase = FirebaseDatabase.getInstance().getReference().child("UserList").child(userID);
        userListDatabase.child("userListRecipe").child(nodeKey).removeValue();

        //Set Current Node Key
        currentNodeKey = nodeKey;
        currentProfile = profile;

    }


    public void Undo(View view) {
        //undo = true;

        Log.d("Current ", "node is: " + currentNodeKey);
        Log.d("Current", "profile is: " + currentProfile);
        if (currentProfile != null && currentNodeKey != null){
            testSwipe.undoLastSwipe();
            //Use CurrentNode Key and Current Profile to add back to userList & remove from Favorites
            userListDatabase = FirebaseDatabase.getInstance().getReference().child("UserList").child(userID);
            userListDatabase.child("userListRecipe").child(currentNodeKey).setValue(currentProfile);

            //Remove from favorites
            favoriteDatabase = FirebaseDatabase.getInstance().getReference().child("UserList").child(userID)
                    .child("Favorites");
            favoriteDatabase.child(currentNodeKey).removeValue();
        }
    }


    public boolean getUndo(){
        return undo;
    }

}
