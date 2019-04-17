package com.example.moviedirectory.Util;

import android.content.SharedPreferences;
import android.app.Activity;
public class Prefs {
    SharedPreferences sharedPreferences;
    public Prefs(Activity activity){
        sharedPreferences =activity.getPreferences(activity.MODE_PRIVATE);
    }
    public void setSearch(String search)
    {
        sharedPreferences.edit().putString("search",search).commit();//saves the user preferences
    }
    public String getSearch(){
        return sharedPreferences.getString("search","SuperMan");
    }
}
