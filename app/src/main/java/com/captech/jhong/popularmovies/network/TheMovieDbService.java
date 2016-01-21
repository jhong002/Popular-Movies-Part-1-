package com.captech.jhong.popularmovies.network;

import com.captech.jhong.popularmovies.model.GetDiscoverMovieResponse;
import com.captech.jhong.popularmovies.model.GetReviewsResponse;
import com.captech.jhong.popularmovies.model.GetVideosResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by jhong on 12/17/15.
 */
public interface TheMovieDbService {

    @GET("discover/movie")
    Call<GetDiscoverMovieResponse> getDiscoverMovies(@Query("sort_by") String sortyBy, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<GetVideosResponse> getVideos(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<GetReviewsResponse> getReviews(@Path("id") int id, @Query("api_key") String apiKey);

}
