package com.example.tastypastry;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
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


import org.json.JSONObject;

import java.io.Serializable;

//Put this back after done testing
//@NonReusable
@Layout(R.layout.pictures)
public class SwipeFunction {

    @View(R.id.PastryImage)
    private ImageView pictureView;

    @View(R.id.pictureName)
    private TextView pictureName;

    protected Profile testProfile; //Changed to protected temp
    protected Profile prevTestProfile;
    private Context testContext;
    private SwipePlaceHolderView testSwipe;
    private String swipeKey;
    private boolean undo;
    DashBoardActivity dashBoardActivity = new DashBoardActivity();

    public SwipeFunction(Context context, Profile profile, SwipePlaceHolderView swipeView, String nodeKey) {
        testProfile = profile;
        testContext = context;
        testSwipe = swipeView;
        swipeKey = nodeKey;
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
        intent.putExtra("key", testProfile.getKey());
        intent.putExtra("image", testProfile.getImage());
        String s = (new Gson().toJson(testProfile));
        intent.putExtra("profile", s);
        //Starts activity from context rather than a class
        testContext.startActivity(intent);
    }

    @Resolve
    private void onResolved() {
//        undo = dashBoardActivity.getUndo();
//        Log.d("undo","undo" + undo);
//        if(undo)
//        {
//            testProfile = prevTestProfile;
//        }

        Glide.with(testContext).load(testProfile.getImage()).into(pictureView);
        pictureName.setText(testProfile.getName());
    }


//    private void chooseProfile(String profile){
//        if(profile.equals("previous"))
//        {
//
//        }
//
//    }

    // When card is rejected
    @SwipeOut
    private void SwipedOut() {
        Log.d("EVENT", "SwipedOut");
        dashBoardActivity.deleteFromUserListRecipe(swipeKey);
        prevTestProfile = testProfile;
    }

    // When card isn't swiped completely left or right
    @SwipeCancelState
    private void SwipeCancelState() {
        Log.d("EVENT", "SwipeCancelState");
    }

    @SwipeIn
    private void SwipeIn() {
        Log.d("EVENT", "SwipedIn");
        dashBoardActivity.addRecipeToDatabase(testProfile);
        dashBoardActivity.deleteFromUserListRecipe(swipeKey);
        prevTestProfile = testProfile;
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

    private Profile getTestProfile(){
        return testProfile;
    }
}
