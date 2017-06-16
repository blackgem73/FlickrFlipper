package com.task.flickrflipper;

import android.app.Application;

import com.task.flickrflipper.network.Network;


/**
 * Created by rafi on 7/6/17.
 */

public class App extends Application{

    private static App sInstance;

    public static synchronized App getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Network.init(this);
    }

}
