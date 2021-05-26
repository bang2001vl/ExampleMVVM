package com.example.mvvm;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Context MyContext;
    public void onCreate() {
        super.onCreate();
        MyApplication.MyContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.MyContext;
    }
}
