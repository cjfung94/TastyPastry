package com.example.tastypastry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {
    private EditText nameSec, emailSec, subSec, commentSec;
    private TextView helpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
    }
}