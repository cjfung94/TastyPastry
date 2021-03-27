package com.example.tastypastry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.HashMap;

public class DashBoardActivity extends Activity {
    private SwipePlaceHolderView testSwipe;
    private Context testContext;
    private static DatabaseReference mDatabase;
    Bundle extras;
    private String userID;
    private static DatabaseReference userDatabase;
    private static DatabaseReference favoriteDatabase;
    private FirebaseAuth firebaseAuth;
    Profile recipeProfile = new Profile();
    Users user = new Users();


    //Create Hashmap to save values inside of FireBase
    HashMap<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        extras = getIntent().getExtras();
        // Swiping stuff
        testSwipe = (SwipePlaceHolderView) findViewById(R.id.swipeView);
        testContext = getApplicationContext();

        testSwipe.getBuilder().setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor().setPaddingTop(20).setRelativeScale(0.01f));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("recipeList");
        userDatabase = FirebaseDatabase.getInstance().getReference();

        // Put the email into the map and into Database

//        userID = extras.getString("userID");
//        Log.d("UserID", " :" + userID);
//        map.put("Email", extras.getString("emailAddy"));
//        userDatabase.child("UserList").child(userID).updateChildren(map);


        mDatabase.addValueEventListener(new ValueEventListener() {
            Gson gson = new Gson();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapShot : snapshot.getChildren()) {
                    //Is there another way to do this or simplify this? Need for Json and Gson?
                    String json = new Gson().toJson(postSnapShot.getValue());
                    Profile profile = gson.fromJson(json, Profile.class);
                    testSwipe.addView(new SwipeFunction(testContext, profile, testSwipe));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Reading from database failed: " + error.getMessage());
            }
        });

        //NAVIGATION BAR:
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

    //SwipeFunction calls this method to add to database
    public void addRecipeToDatabase(Profile profile){
        //Get the current userID
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        //Add recipes to the current user's list in Database
        favoriteDatabase = FirebaseDatabase.getInstance().getReference().child("UserList").child(userID).child("Favorites");
        //Push creates a unique value for each, we don't need to check since we're going to delete it from the list
        //Remove addView from SwipeFunction SwipeIn/SwipeOut after testing is complete
        favoriteDatabase.push().setValue(profile);
    }

    //Add user to database
    public void createUserDatabase(String userId, String email){

        userDatabase = FirebaseDatabase.getInstance().getReference();
        Log.d("UserID", " :" + userId + " " + email);
        map.put("Email", email);
        userDatabase.child("UserList").child(userId).updateChildren(map);

    }
}
