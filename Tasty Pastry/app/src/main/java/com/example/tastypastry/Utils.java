package com.example.tastypastry;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static final String TAG = "Utils";

    //Adds json file stuff into Gson builder and makes a list of foods
    public static List<Profile> loadPictures(Context context){
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson foodGson = builder.create();
            JSONArray array = new JSONArray(loadJSONAsset(context, "test.json"));
            List<Profile> foodList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++){
                Profile profile = foodGson.fromJson(array.getString(i), Profile.class);
                foodList.add(profile);
            }
            return foodList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //Take information from the json file
    private static String loadJSONAsset(Context context, String jsonFile) {
        String json = null;
        InputStream is = null;
        try {
            AssetManager manager = context.getAssets();
            Log.d(TAG, "path" + jsonFile);
            is = manager.open(jsonFile);
            int size = is.available();
            byte [] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
