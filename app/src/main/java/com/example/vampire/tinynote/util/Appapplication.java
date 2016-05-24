package com.example.vampire.tinynote.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by Vampire on 2016/5/1.
 */
public class Appapplication extends Application {
    public static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
    }
}
