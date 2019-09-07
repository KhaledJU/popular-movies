package com.example.movies;

import android.os.Looper;

import androidx.annotation.MainThread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Handler;

public class AppExecutors {
    private static final Object LOCK = new Object();
    private static AppExecutors instance;
    private final Executor diskIO;
    //private final Executor mainThread;
    private final Executor networkIO;

    public AppExecutors(Executor diskIO, Executor networkIO) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
    }

    public static AppExecutors getInstance() {
        if(instance == null){
            synchronized (LOCK){
                instance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3));
            }
        }
        return instance;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    /*public Executor getMainThread() {
        return mainThread;
    }*/

    public Executor getNetworkIO() {
        return networkIO;
    }

    /*private static class MainThreadExutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }*/

}

