package com.captech.jhong.popularmovies;

/**
 * Created by jhong on 12/21/15.
 */
public class RequestQueueUtils {
    public static void queueDiscoverMovieRequest(String sortBy, NetworkRequestSender sender){
        GetDiscoverMovieRequest getDiscoverMovieRequest = new GetDiscoverMovieRequest.Builder(sender)
                .setSortBy(sortBy)
                .setIsAsync(true)
                .build();
        NetworkUtils.queueNetworkRequest(getDiscoverMovieRequest);

    }
}
