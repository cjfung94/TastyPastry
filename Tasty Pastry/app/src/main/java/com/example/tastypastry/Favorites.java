package com.example.tastypastry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {

    ListView listView;
    private static DatabaseReference favoriteDatabase;
    private FirebaseAuth firebaseAuth; //goes into firebase authentication
    private String userID;
    private Button DeleteFromFavorites;

    //Create ArrayList + Adapter
    ArrayList<Profile> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ProfileAdapter adapter = new ProfileAdapter(this, arrayList);

        //Set ListView to ID
        listView = findViewById(R.id.listview_favorites);
        listView.setAdapter(adapter);
        firebaseAuth = FirebaseAuth.getInstance();
        favoriteDatabase = FirebaseDatabase.getInstance().getReference().child("UserList");
        userID = firebaseAuth.getCurrentUser().getUid();

        favoriteDatabase.child(userID).child("Favorites").addChildEventListener(new ChildEventListener() {
            Gson gson = new Gson();

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String json = new Gson().toJson(snapshot.getValue());
                Profile profile = gson.fromJson(json, Profile.class);
                Log.d("Favorites", "name" + profile.getName());
                adapter.add(profile);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //method parameter below: 'int i' gives position of element touched in listview
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Favorites.this, DisplayPastryRecipe.class);
                intent.putExtra("recipe", arrayList.get(i).getRecipe());
                intent.putExtra("ingredients", arrayList.get(i).getIngredients());
                intent.putExtra("pastryName", arrayList.get(i).getName());
                intent.putExtra("className", this.getClass().getSimpleName());
                startActivity(intent);
            }
        });

        //Nav Bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Favorites);
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
                        startActivity(new Intent(getApplicationContext(), Filter.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Favorites:
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