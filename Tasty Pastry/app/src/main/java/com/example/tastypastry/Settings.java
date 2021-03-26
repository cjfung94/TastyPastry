package com.example.tastypastry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Settings extends AppCompatActivity {
    private Button aboutButton, helpButton, lFBButton, signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        aboutButton = findViewById(R.id.About_button);
        helpButton = findViewById(R.id.Help_button);
        lFBButton = findViewById(R.id.Leave_feedback_button);
        signOutButton = findViewById(R.id.Sign_out_button);

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Something
            }
        });
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Something
            }
        });
        lFBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Something
            }
        });
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Something
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.Settings);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
                        overridePendingTransition(0, 0);
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
                        return true;
                }
                return false;
            }
        });
    }
}