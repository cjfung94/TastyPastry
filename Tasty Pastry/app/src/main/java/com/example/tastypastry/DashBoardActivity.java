package com.example.tastypastry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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
        
        // Put the email into the map and into Database
        userID = extras.getString("userID");
        Log.d("UserID", " :" + userID);
        map.put("Email", extras.getString("emailAddy"));

        mDatabase.child("UserList").updateChildren(map);
        mDatabase.child("UserList").child(userID).updateChildren(map);

        mDatabase.addValueEventListener(new ValueEventListener() {
            Gson gson = new Gson();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapShot : snapshot.getChildren()) {
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
}
