package com.example.mvparms;

import android.app.Application;

import com.jess.arms.base.BaseApplication;

import lombok.Getter;

/**
 * Description:
 * <p>
 * CreateTime：2020/12/28  10:38
 */
public class MyApp extends BaseApplication {
    @Getter
    public static Application myApp;

    @Override
    public void onCreate() {
        super.onCreate();
    }

}