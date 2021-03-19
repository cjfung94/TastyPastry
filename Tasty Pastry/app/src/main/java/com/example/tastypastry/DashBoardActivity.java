package com.example.tastypastry;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

public class DashBoardActivity extends Activity {
    private Button logout;
    private SwipePlaceHolderView testSwipe;
    private Context testContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);



        //Swiping stuff
        testSwipe = (SwipePlaceHolderView)findViewById(R.id.swipeView);
        testContext = getApplicationContext();

        testSwipe.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f));
        for (Profile profile : Utils.loadPictures(this.getApplicationContext())){
            testSwipe.addView(new SwipeFunction(testContext, profile, testSwipe));
        }

    }
}

