package com.captech.jhong.popularmovies.network;

import com.captech.jhong.popularmovies.model.GetDiscoverMovieRequest;
import com.captech.jhong.popularmovies.network.request.NetworkRequestSender;

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
