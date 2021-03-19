//package com.example.tastypastry;
//
//import android.content.Context;
//import android.content.res.AssetManager;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class Utils {
//    private static final String TAG = "Utils";
//    private static DatabaseReference mDatabase;
//
//    //Adds json file stuff into Gson builder and makes a list of foods
//    public static List<Profile> loadPictures(Context context) {
//        try {
//            mDatabase = FirebaseDatabase.getInstance().getReference();
////        try{
////            GsonBuilder builder = new GsonBuilder();
////            Gson foodGson = builder.create();
////            JSONArray array = new JSONArray(loadJSONAsset(context, "test.json"));
////            List<Profile> foodList = new ArrayList<>();
////            for (int i = 0; i < array.length(); i++){
////                Profile profile = foodGson.fromJson(array.getString(i), Profile.class);
////                foodList.add(profile);
////            }
////            return foodList;
////        }catch (Exception e){
////            e.printStackTrace();
////            return null;
////        }
////    }
//            Gson gson = new Gson();
//            List<Profile> foodList = new ArrayList<>();
//            mDatabase.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    for (DataSnapshot postSnapShot : snapshot.getChildren()) {
//                        String json = new Gson().toJson(postSnapShot.getValue());
//                        Log.d(TAG, "json info: " + json);
//                        Profile profile = gson.fromJson(json, Profile.class);
//                        foodList.add(profile);
//                    }
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    System.out.println("Reading from database failed: " + error.getMessage());
//
//                }
//            });
//            return foodList;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    //Take information from the json file
//    private static String loadJSONAsset(Context context, String jsonFile) {
//        String json = null;
//        InputStream is = null;
//        try {
//            AssetManager manager = context.getAssets();
//            Log.d(TAG, "path" + jsonFile);
//            is = manager.open(jsonFile);
//            int size = is.available();
//            byte [] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex){
//            ex.printStackTrace();
//            return null;
//        }
//        Log.d(TAG, "json is : " + json );
//        return json;
//    }
//
//}
