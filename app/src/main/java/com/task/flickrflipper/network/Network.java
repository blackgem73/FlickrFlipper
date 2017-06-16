package com.task.flickrflipper.network;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.ExecutorDelivery;
import com.android.volley.RequestQueue;
import com.android.volley.ResponseDelivery;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.task.flickrflipper.App;

import java.io.File;
import java.util.concurrent.Executors;

public class Network {
    private static final String SMALL_CACHE_DIR = "app-data-cache";
    private static final String LARGE_CACHE_DIR = "app-image-cache";
    private static final int SMALL_CACHE_SIZE = 5 * 1024 * 1024;
    private static final int LARGE_CACHE_SIZE = 50 * 1024 * 1024;
    public static final int REQUEST_TIMEOUT = 60 * 1000;
    public static final int UPLOAD_TIMEOUT = 5 * 60 * 1000;

    private static RequestQueue sGeneralRequestQueue;

    private static RequestQueue sSingleRequestQueue;

    public static void init(Context context) {

        File cacheDir1 = new File(context.getCacheDir(), SMALL_CACHE_DIR);
        Cache sDiskCache1 = new DiskBasedCache(cacheDir1, SMALL_CACHE_SIZE);
        File cacheDir2 = new File(context.getCacheDir(), LARGE_CACHE_DIR);
        Cache sDiskCache2 = new DiskBasedCache(cacheDir2, LARGE_CACHE_SIZE);

        ResponseDelivery delivery = new ExecutorDelivery(Executors.newFixedThreadPool(4));

        ResponseDelivery singleDelivery = new ExecutorDelivery(Executors.newFixedThreadPool(1));

        sGeneralRequestQueue = new RequestQueue(sDiskCache1, new BasicNetwork(new OkHttpStack(App.getInstance())), 4, delivery);
        sGeneralRequestQueue.start();

        sSingleRequestQueue = new RequestQueue(sDiskCache1, new BasicNetwork(new HurlStack()), 1, singleDelivery);
        sSingleRequestQueue.start();

        RequestQueue sImageQueue = new RequestQueue(sDiskCache2, new BasicNetwork(new HurlStack()), 4);
        sImageQueue.start();
    }

    public static RequestQueue getGeneralRequestQueue() {
        return sGeneralRequestQueue;
    }

    public static RequestQueue getSingleRequestQueue() {
        return sSingleRequestQueue;
    }

}
