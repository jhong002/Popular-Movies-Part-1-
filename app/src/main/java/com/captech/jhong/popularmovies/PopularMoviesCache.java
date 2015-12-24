package com.captech.jhong.popularmovies;

/**
 * Created by jhong on 12/23/15.
 */
public enum PopularMoviesCache {
    INSTANCE;
    private static GetDiscoverMovieResponseEvent getDiscoverMovieResponseEvent;

    public static GetDiscoverMovieResponseEvent getGetDiscoverMovieResponseEvent() {
        return getDiscoverMovieResponseEvent;
    }
    public static void setDiscoverMovieResponseEvent(GetDiscoverMovieResponseEvent event){
        getDiscoverMovieResponseEvent = event;
    }
}
