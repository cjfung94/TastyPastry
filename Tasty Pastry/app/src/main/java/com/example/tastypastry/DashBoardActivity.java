package com.example.tastypastry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class DashBoardActivity extends Activity {
    private Button logout;
    private SwipePlaceHolderView testSwipe;
    private Context testContext;
    private static DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        // Swiping stuff
        testSwipe = (SwipePlaceHolderView) findViewById(R.id.swipeView);
        testContext = getApplicationContext();

        testSwipe.getBuilder().setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor().setPaddingTop(20).setRelativeScale(0.01f));
        mDatabase = FirebaseDatabase.getInstance().getReference();
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

    }
}
