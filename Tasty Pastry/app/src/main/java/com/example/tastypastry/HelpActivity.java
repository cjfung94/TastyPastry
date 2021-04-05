package com.example.tastypastry;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    }

    public void sendUserComment(){
        String userName = nameSec.getText().toString();
        String userEmail = emailSec.getText().toString();
        String subject = subSec.getText().toString();
        String comments = commentSec.getText().toString();

        progressDialog.setMessage("Thank you, "+userName+"! Your comment(s) have been sent!");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(true);

        //do something ... TBC

        //progressDialog.dismiss();

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