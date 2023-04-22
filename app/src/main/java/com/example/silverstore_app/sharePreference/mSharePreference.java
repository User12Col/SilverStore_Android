package com.example.silverstore_app.sharePreference;

import android.content.Context;
import android.content.SharedPreferences;

public class mSharePreference {
    private static final String MY_SHARE_PREFERENCES = "MY_SHARE_PREFERENCES";
    private Context mContext;

    public mSharePreference(Context context){
        this.mContext = context;
    }

    public void putStringValue(String key, String value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARE_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public String getStringValue(String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARE_PREFERENCES,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
}
