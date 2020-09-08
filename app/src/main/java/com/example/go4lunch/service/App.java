package com.example.go4lunch.service;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.widget.AppCompatSpinner;

import java.lang.ref.WeakReference;

public class App extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
         mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
