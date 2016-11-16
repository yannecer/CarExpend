package com.necer.carexpend.application;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by necer on 2016/11/16.
 */

public class CarApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Bmob.initialize(this, "9416b1587b7bf3347599bcfc848a62f9");
    }
}
