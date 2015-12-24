package com.captech.jhong.popularmovies;

import android.app.Application;

/**
 * Created by jhong on 12/21/15.
 */
public class PopularMoviesApp extends Application {
    private static PopularMoviesApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static PopularMoviesApp getInstance() {
        return instance;
    }
}
