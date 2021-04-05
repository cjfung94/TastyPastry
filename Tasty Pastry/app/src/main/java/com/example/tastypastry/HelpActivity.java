package com.example.tastypastry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class HelpActivity extends AppCompatActivity {
    private EditText nameSec, emailSec, subSec, commentSec;
    private Button sendButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        nameSec = findViewById(R.id.name_editText);
        emailSec = findViewById(R.id.email_editText);
        subSec = findViewById(R.id.subject_editText);
        commentSec = findViewById(R.id.comment_section_editText);
        progressDialog = new ProgressDialog(this);
        sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserComment();
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
                        startActivity((new Intent(getApplicationContext(), Settings.class)));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    public void sendUserComment(){
        String userName = nameSec.getText().toString();
        String userEmail = emailSec.getText().toString();
        String subject = subSec.getText().toString();
        String comments = commentSec.getText().toString();

        progressDialog.setMessage("Thank you, "+userName+"! Your comment(s) have been sent!");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(true);

        String creatorsEmail = "lcpljorgeesquivel@gmail.com";
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_EMAIL  , creatorsEmail);
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, comments);
        i.setType("message/rfc822");
        startActivity(Intent.createChooser(i,"Choose an email client :"));
//        try {
//            startActivity(Intent.createChooser(i, "Send mail..."));
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(HelpActivity.this, "No email clients installed.",   Toast.LENGTH_SHORT).show();
//        }
    }
}