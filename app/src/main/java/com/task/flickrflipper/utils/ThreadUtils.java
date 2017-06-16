package com.task.flickrflipper.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtils {
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static ExecutorService defaultExecutorService = new ThreadPoolExecutor(
            NUMBER_OF_CORES, // Initial pool size
            NUMBER_OF_CORES, // Max pool size
            1, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());

    private ThreadUtils() {
    }

    public static ExecutorService getDefaultExecutorService() {
        return defaultExecutorService;
    }

}
