package com.example.tastypastry;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

//Put this back after done testing
//@NonReusable
@Layout(R.layout.pictures)
public class SwipeFunction {

    @View(R.id.PastryImage)
    private ImageView pictureView;

    @View(R.id.pictureName)
    private TextView pictureName;

    private Profile testProfile;
    private Context testContext;
    private SwipePlaceHolderView testSwipe;
    DashBoardActivity dashBoardActivity = new DashBoardActivity();


    public SwipeFunction(Context context, Profile profile, SwipePlaceHolderView swipeView) {
        testProfile = profile;
        testContext = context;
        testSwipe = swipeView;
    }

    // See what happens with images
    @Click(R.id.PastryImage)

    public void openRecipe() {
        Intent intent = new Intent(testContext, DisplayPastryRecipe.class);
        //Add flags so I can go into another window without having an Activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Saves information onto a bundle so other classes can access it
        intent.putExtra("recipe", testProfile.getRecipe());
        intent.putExtra("pastryName", testProfile.getName());
        intent.putExtra("ingredients", testProfile.getIngredients());
        //Starts activity from context rather than a class
        testContext.startActivity(intent);
    }

    @Resolve
    private void onResolved() {
        Glide.with(testContext).load(testProfile.getImage()).into(pictureView);
        pictureName.setText(testProfile.getName());
    }

    // When card is rejected
    @SwipeOut
    private void SwipedOut() {
        Log.d("EVENT", "SwipedOut");
        //testSwipe.removeView(this); --> this is for when delete is implemented
        //might need to use @NonReusable

    }

    // When card isn't swiped completely left or right
    @SwipeCancelState
    private void SwipeCancelState() {
        Log.d("EVENT", "SwipeCancelState");
    }

    // When card is accepted/liked

    @SwipeIn
    private void SwipeIn() {
        Log.d("EVENT", "SwipedIn");

        dashBoardActivity.addRecipeToDatabase(testProfile);

    }

    // Pings method til card is in Swiped in State
    @SwipeInState
    private void SwipeInState() {
        Log.d("EVENT", "SwipeInState");
    }

    // Pings method til card is in Swiped out state
    @SwipeOutState
    private void SwipeOutState() {
        Log.d("EVENT", "SwipeOutState");
    }

    // If we don't want to re add a view, then just put @NonReusable

}
