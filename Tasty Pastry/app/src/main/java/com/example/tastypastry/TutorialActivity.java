package com.example.tastypastry;

import android.app.Activity;
import android.graphics.Typeface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TutorialActivity extends Activity {

    private TextView tutorialText1;
    private TextView tutorialText2;
    private TextView tutorialText3;
    private Button LetsStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);
        tutorialText1 = (TextView) findViewById(R.id.TutorialText1);
        tutorialText2 = (TextView) findViewById(R.id.TutorialText2);
        tutorialText3 = (TextView) findViewById(R.id.TutorialText3);
        String text1 = "Swipe picture to the left to delete the pastry and to see the next one";
        String text2 = "Swipe picture to the right to save the pastry to Favorites\nand to see the next one";
        String text3 = "Click on the picture to see the recipe";
        SpannableString ss1 = new SpannableString(text1);
        SpannableString ss2 = new SpannableString(text2);
        SpannableString ss3 = new SpannableString(text3);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        StyleSpan boldItalSpan = new StyleSpan(Typeface.BOLD_ITALIC);
        ss1.setSpan(boldSpan, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(boldItalSpan, 21, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(boldSpan, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(boldItalSpan, 21, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss3.setSpan(boldSpan, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tutorialText1.setText(ss1);
        tutorialText1.setText(ss1);
        tutorialText2.setText(ss2);
        tutorialText2.setText(ss2);
        tutorialText3.setText(ss3);

        LetsStart = findViewById(R.id.letsStart);
        LetsStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorialActivity.this, DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

