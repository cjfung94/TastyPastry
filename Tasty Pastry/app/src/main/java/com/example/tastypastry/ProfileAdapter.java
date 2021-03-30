package com.example.tastypastry;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

//Custom adapter that has the ability to cache lookups so that it'll load faster
public class ProfileAdapter extends ArrayAdapter<Profile> {
    //View lookup cache
    private static class ViewHolder{
        ImageView recipeImage;
        TextView recipeName;
        TextView recipe;

    }

    public ProfileAdapter(Context context, ArrayList<Profile> profiles){
        super(context, R.layout.favorites_view, profiles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Data item for the position
        Profile profile = getItem(position);
        //Check if existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; //view lookup cache stored in the tag

        if (convertView == null){
            //If no view to re use then inflate a new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.favorites_view, parent, false);
//            viewHolder.recipe = (TextView) convertView.findViewById(R.id.recipe);
            viewHolder.recipeName = (TextView) convertView.findViewById(R.id.recipeName);
            viewHolder.recipeImage = (ImageView) convertView.findViewById(R.id.recipeImage);
            convertView.setTag(viewHolder);

        }
        else{
            //View is recycled, retrieve the object from tag // it's cached
            viewHolder = (ViewHolder) convertView.getTag();

        }
        Glide.with(getContext()).load(profile.getImage()).into(viewHolder.recipeImage);
        //Populate data from the data object via viewHolder obj
        viewHolder.recipeName.setText(profile.getName());
//        viewHolder.recipe.setText(profile.getRecipe());

        return convertView;

    }

}
