package com.ray.anywhere;

import android.app.Application;

import com.ray.anywhere.util.LoginHelper;

public class AppApplication extends Application {
    public static boolean APP_DEBUG = true;
    static AppApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        LoginHelper.init(this);
        AppApplication.instance = this;
    }

    public static AppApplication getInstance() {
        return instance;
    }
}
