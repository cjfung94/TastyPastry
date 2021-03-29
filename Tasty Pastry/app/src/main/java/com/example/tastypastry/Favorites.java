package com.example.tastypastry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Favorites extends AppCompatActivity {

    ListView listView;
    private static DatabaseReference favoriteDatabase;
    private FirebaseAuth firebaseAuth; //goes into firebase authentication
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        firebaseAuth = FirebaseAuth.getInstance();
        favoriteDatabase = FirebaseDatabase.getInstance().getReference().child("UserList");
        userID = firebaseAuth.getCurrentUser().getUid();

        favoriteDatabase.child(userID).child("Favorites").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dSnapshot : snapshot.getChildren()) {

//                    ModelClass img = snapshot.getValue(ModelClass.class);
//                    myAdapter.addElement(img);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Creation/Addition of list of favorites
        listView=(ListView)findViewById(R.id.listview_favorites);
        ArrayList<Profile> arrayList = new ArrayList<>();

        //arrayList.add(new Profile(""))

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        //Clickable list view item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //method parameter below: 'int i' gives position of element touched in listview
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(Favorites.this,"clicked item" + i + " " + arrayList.get(i).toString(),Toast.LENGTH_SHORT).show();
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
                        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Filter:
                        startActivity(new Intent(getApplicationContext(), Filter.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Favorites:
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
}