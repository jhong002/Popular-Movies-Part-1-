package com.captech.jhong.popularmovies.network;

import com.captech.jhong.popularmovies.model.GetDiscoverMovieResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by jhong on 12/17/15.
 */
public interface TheMovieDbService {

    @GET("discover/movie")
    Call<GetDiscoverMovieResponse> getDiscoverMovies(@Query("sort_by") String sortyBy, @Query("api_key") String apiKey);

}
