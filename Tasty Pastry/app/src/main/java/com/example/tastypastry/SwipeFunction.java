package com.example.tastypastry;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

@Layout(R.layout.pictures)
public class SwipeFunction {

    @View(R.id.PastryImage)
    private ImageView pictureView;

    @View(R.id.pictureName)
    private TextView pictureName;



    private Profile testProfile;
    private Context testContext;
    private SwipePlaceHolderView testSwipe;

    public SwipeFunction(Context context, Profile profile, SwipePlaceHolderView swipeView){
        testProfile = profile;
        testContext = context;
        testSwipe = swipeView;
    }

    //See what happens with images
    @Resolve
    private void onResolved(){
        Glide.with(testContext).load(testProfile.getImage()).into(pictureView);
        pictureName.setText(testProfile.getName());
    }


    //When card is rejected
    @SwipeOut
    private void SwipedOut(){
        Log.d("EVENT", "SwipedOut");
        testSwipe.addView(this);
    }

    //When card is put back
    @SwipeCancelState
    private void SwipeCancelState(){
        Log.d("EVENT", "SwipeCancelState");
    }

    //When card is accepted/liked
    @SwipeIn
    private void SwipeIn(){
        Log.d("EVENT", "SwipedIn");
        testSwipe.addView(this);
    }

    //Pings method til card is in Swiped in State
    @SwipeInState
    private void SwipeInState(){
        Log.d("EVENT", "SwipeInState");
    }

    //Pings method til card is in Swiped out state
    @SwipeOutState
    private void SwipeOutState(){
        Log.d("EVENT", "SwipeOutState");
    }

    //If we don't want to re add a view, then just put @NonReusable
}