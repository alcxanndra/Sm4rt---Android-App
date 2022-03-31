package com.example.sm4rt;

import android.app.Application;

import androidx.room.Room;

public class MyApplication extends Application {

    private static MyApplication mInstance;
    private static AppDatabase mAppDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        mAppDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "sm4rt-database").build();
    }

    public static MyApplication getInstance(){
        return mInstance;
    }

    public static AppDatabase getAppDatabase(){
        return mAppDatabase;
    }
}