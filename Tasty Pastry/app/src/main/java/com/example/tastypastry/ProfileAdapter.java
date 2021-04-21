package com.example.tastypastry;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

//Custom adapter that has the ability to cache lookups so that it'll load faster
public class ProfileAdapter extends ArrayAdapter<Profile> {
    //View lookup cache

    private static DatabaseReference userDatabase;
    private static DatabaseReference favoriteDatabase;
    private FirebaseAuth firebaseAuth;
    private String userID;
    private String ProfileId;
    private DashBoardActivity dashBoardActivity;

    private static class ViewHolder {
        ImageView recipeImage;
        TextView recipeName;
        TextView recipe;
        TextView ingredients;
        private Button DeleteFromFavorites;
    }

    public ProfileAdapter(Context context, ArrayList<Profile> profiles) {
        super(context, R.layout.favorites_view, profiles);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Data item for the position
        Profile profile = getItem(position);

        //Check if existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; //view lookup cache stored in the tag

        if (convertView == null) {
            //If no view to re use then inflate a new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.favorites_view, parent, false);
            //Set an onClick to utilize this
            //  viewHolder.recipe = (TextView) convertView.findViewById(R.id.recipe);
            viewHolder.recipeName = (TextView) convertView.findViewById(R.id.recipeName);
            viewHolder.recipeImage = (ImageView) convertView.findViewById(R.id.recipeImage);
            convertView.setTag(viewHolder);

        } else {
            //View is recycled, retrieve the object from tag // it's cached
            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.DeleteFromFavorites = convertView.findViewById(R.id.DeleteFromFav);
        viewHolder.DeleteFromFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setCancelable(true);
                builder.setTitle("Warning!");
                builder.setMessage("Are you sure you want to delete this?");

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProfileId = profile.getKey();
                        firebaseAuth = FirebaseAuth.getInstance();
                        userID = firebaseAuth.getCurrentUser().getUid();
                        favoriteDatabase = FirebaseDatabase.getInstance().getReference().child("UserList").child(userID).child("Favorites");
                        favoriteDatabase.child(ProfileId).removeValue();
                        remove(profile);
                        notifyDataSetChanged();
                    }
                });

                builder.show();

//                ProfileId = profile.getKey();
//                firebaseAuth = FirebaseAuth.getInstance();
//                userID = firebaseAuth.getCurrentUser().getUid();
//                favoriteDatabase = FirebaseDatabase.getInstance().getReference().child("UserList").child(userID).child("Favorites");
//                favoriteDatabase.child(ProfileId).removeValue();
//                remove(profile);
//                notifyDataSetChanged();
            }
        });

        Glide.with(getContext()).load(profile.getImage()).into(viewHolder.recipeImage);
        //Populate data from the data object via viewHolder obj
        viewHolder.recipeName.setText(profile.getName());
        //    viewHolder.recipe.setText(profile.getRecipe());

        return convertView;

    }
}
