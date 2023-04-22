package com.example.silverstore_app.sharePreference;

import android.app.Application;

public class mApplication  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
