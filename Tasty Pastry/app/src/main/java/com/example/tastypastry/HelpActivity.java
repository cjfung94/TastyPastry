package com.example.tastypastry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {
    private EditText nameSec, emailSec, subSec, commentSec;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        nameSec = findViewById(R.id.name_editText);
        emailSec = findViewById(R.id.email_editText);
        subSec = findViewById(R.id.subject_editText);
        commentSec = findViewById(R.id.comment_section_editText);
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
        String Comments = commentSec.getText().toString();

        //do something ... TBC

    }
}