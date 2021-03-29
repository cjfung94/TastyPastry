package com.example.tastypastry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Settings extends AppCompatActivity {
    private Button aboutButton, helpButton, lFBButton, signOutButton;
    private TextView textView;
    Bundle extras;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        extras = getIntent().getExtras();
//        if(extras != null){
//            textView.setText(extras.getString("username"));
//        }
//        if(extras == null){
//            textView.setText(extras.getString("emailAddy"));
//        }
//        Log.d("Email", "This"+extras.getString("emailAddy"));



        aboutButton = (Button) findViewById(R.id.About_button);
        helpButton = (Button) findViewById(R.id.Help_button);
        lFBButton = (Button) findViewById(R.id.Leave_feedback_button);
        signOutButton = (Button) findViewById(R.id.Sign_out_button);
        textView = (TextView) findViewById(R.id.Username_textView);
        //userName = extras.getString("username");
//        textView = findViewById(R.id.Username_textView);
//        textView.setText(userName);
        textView.setText(extras.getString("emailAddy"));

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAboutActivity();
            }
        });
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelpActivity();
            }
        });
        lFBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLFBActivity();
            }
        });
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
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

    private void openAboutActivity() {
        Intent intent =  new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void openHelpActivity() {
        Intent intent =  new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    private void openLFBActivity(){
        Intent intent = new Intent(this, LFBActivity.class);
        startActivity(intent);
    }

    private void signOut() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}