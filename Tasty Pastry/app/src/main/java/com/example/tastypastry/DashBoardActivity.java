package com.example.tastypastry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

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
    private String userID;
    private String userEmail;
    private String nodeKey;
    private SwipePlaceHolderView signUpSwipe;
    private static DatabaseReference userDatabase;
    private static DatabaseReference favoriteDatabase;
    private FirebaseAuth firebaseAuth;

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
        extras = getIntent().getExtras();
        testContext = getApplicationContext();

        //values are here from signin/out if we need them

        // Swiping stuff
        testSwipe = (SwipePlaceHolderView) findViewById(R.id.swipeView);
        // Creates recipes seen
        createDisplayRecipes();

        // NAVIGATION BAR:
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.Home:
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

    // Sign in - Display user's list of recipes
    protected void createDisplayRecipes() {

        // Fix this part
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        testSwipe.getBuilder().setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor().setPaddingTop(20).setRelativeScale(0.01f));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("UserList").child(userID);

        // mDatabase.child("userListRecipe").addValueEventListener(new
        // ValueEventListener() {
        // Gson gson = new Gson();
        //
        // @Override
        // public void onDataChange(@NonNull DataSnapshot snapshot) {
        // for (DataSnapshot postSnapShot : snapshot.getChildren()) {
        // //Is there another way to do this or simplify this? Need for Json and Gson?
        // nodeKey = postSnapShot.getKey();
        // String json = new Gson().toJson(postSnapShot.getValue());
        // Profile profile = gson.fromJson(json, Profile.class);
        // Log.d("DashBoardActivity", " image " + profile.getImage());
        // testSwipe.addView(new SwipeFunction(testContext, profile, testSwipe,
        // nodeKey));
        // }
        // }
        //
        // @Override
        // public void onCancelled(@NonNull DatabaseError error) {
        // System.out.println("Reading from database failed: " + error.getMessage());
        // }
        // });

        // Utilizing ChildEventListener instead of ValueEvent
        // mDatabase.addChildEventListener(new ChildEventListener() {
        // Gson gson = new Gson();
        // @Override
        // public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String
        // previousChildName) {
        // for (DataSnapshot childSnap : snapshot.getChildren())
        // {
        // nodeKey = childSnap.getKey();
        // String json = new Gson().toJson(childSnap.getValue());
        // Profile profile = gson.fromJson(json, Profile.class);
        // Log.d("DashBoardActivity", " image " + profile.getImage());
        // testSwipe.addView(new SwipeFunction(testContext, profile, testSwipe,
        // nodeKey));
        // };
        //
        // }
        //
        // @Override
        // public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String
        // previousChildName) {
        //
        // }
        //
        // @Override
        // public void onChildRemoved(@NonNull DataSnapshot snapshot) {
        //
        // }
        //
        // @Override
        // public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String
        // previousChildName) {
        //
        // }
        //
        // @Override
        // public void onCancelled(@NonNull DatabaseError error) {
        //
        // }
        // });
        // }
        mDatabase.child("userListRecipe").addListenerForSingleValueEvent(new ValueEventListener() {
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

        // firebaseAuth = FirebaseAuth.getInstance();
        // firebaseAuth.fetchSignInMethodsForEmail(userEmail).addOnCompleteListener(new
        // OnCompleteListener<SignInMethodQueryResult>() {
        // @Override
        // public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
        // boolean isUserThere = task.getResult().getSignInMethods().isEmpty();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("recipeList");
        mDatabase.addValueEventListener(new ValueEventListener() {
            Gson gson = new Gson();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapShot : snapshot.getChildren()) {
                    // Is there another way to do this or simplify this? Need for Json and Gson?
                    String json = new Gson().toJson(postSnapShot.getValue());
                    Profile profile = gson.fromJson(json, Profile.class);
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

    // SwipeFunction calls this method to add to database | Instantiate everything
    public void addRecipeToDatabase(Profile profile) {
        // Get the current userID

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        // Add recipes to the current user's list in Database
        favoriteDatabase = FirebaseDatabase.getInstance().getReference().child("UserList").child(userID)
                .child("Favorites");
        // Push creates a unique value for each, we don't need to check since we're
        // going to delete it from the list
        // Remove addView from SwipeFunction SwipeIn/SwipeOut after testing is complete
        favoriteDatabase.push().setValue(profile);

    }

    // Delete from user's display list after a left or right swipe
    public void deleteFromUserListRecipe(String nodeKey) {
        Log.d("nodekey", "is " + nodeKey);
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        favoriteDatabase = FirebaseDatabase.getInstance().getReference().child("UserList").child(userID);
        favoriteDatabase.child("userListRecipe").child(nodeKey).removeValue();

    }

}
