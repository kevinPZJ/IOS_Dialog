package com.example.kevin.ios_dialog.Utils;

import android.app.Application;

/**
 * Created by pzj on 2017/1/19.
 */

public class MyApplication extends Application {;
    private static MyApplication instance;



    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }
}
