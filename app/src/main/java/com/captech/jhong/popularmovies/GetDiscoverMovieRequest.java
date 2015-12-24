package com.captech.jhong.popularmovies;

import android.content.Context;
import android.util.Log;

import com.squareup.otto.Bus;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by jhong on 12/17/15.
 */
public class GetDiscoverMovieRequest extends NetworkRequest implements Callback<GetDiscoverMovieResponse> {
    public static final String POPULARITY = "popularity.desc", RATING = "vote_average.desc";

    private static class GetDiscoverMovieRequestParams extends NetworkParams {
        private String sortBy;
    }

    public static class Builder {
        private final String senderTag;
        private Boolean isAsync;
        private final GetDiscoverMovieRequestParams mParams;

        public Builder(NetworkRequestSender networkRequestSender) {
            super();
            this.senderTag = networkRequestSender != null ? networkRequestSender.getSenderTag() : null;
            mParams = new GetDiscoverMovieRequestParams();
        }

        public Builder setIsAsync(Boolean isAsync) {
            this.isAsync = isAsync;
            return this;
        }

        public Builder setSortBy(String sortBy) {
            mParams.sortBy = sortBy;
            return this;
        }

        public GetDiscoverMovieRequest build() {
            return new GetDiscoverMovieRequest(senderTag, isAsync, mParams);
        }
    }

    private GetDiscoverMovieRequest(String senderTag, Boolean isAsync, NetworkParams networkParams) {
        super(senderTag, isAsync, networkParams);
    }

    @Override
    public void makeNetworkRequest(Retrofit restAdapter, Context context) {
        GetDiscoverMovieRequestParams params = (GetDiscoverMovieRequestParams) getNetworkParams();
        TheMovieDbService service = restAdapter.create(TheMovieDbService.class);

        Boolean isAsync = getIsAsync();
        Call<GetDiscoverMovieResponse> call = service.getDiscoverMovies(params.sortBy, AppConstants.API_KEY);

        if (isAsync != null && isAsync) {
            call.enqueue(this);
        }
        else {
            try {
                Response<GetDiscoverMovieResponse> response = call.execute();
                onResponse(response, null);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBaseUrl() {
        return AppConstants.BASE_URL;
    }

    @Override
    public void onResponse(Response<GetDiscoverMovieResponse> response, Retrofit retrofit) {
        GetDiscoverMovieResponse movieDiscoverResponse = response.body();
        if (movieDiscoverResponse == null) {
            movieDiscoverResponse = new GetDiscoverMovieResponse();
        }
        Bus bus = BusProvider.getInstance();

        if (response.isSuccess()) {
            GetDiscoverMovieResponseEvent event = new GetDiscoverMovieResponseEvent(movieDiscoverResponse);
            event.setHttpStatusCode(response.code());
            bus.post(event);
        } else {
            int statusCode = response.code();
            if (BuildConfig.DEBUG){
                Log.i("LAC", "onResponse, else, " + statusCode);
            }
            GetDiscoverMovieResponseEvent event = new GetDiscoverMovieResponseEvent(movieDiscoverResponse);
            event.setHttpStatusCode(response.code());
            bus.post(event);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Log.i("FAIL", t.getMessage());
        GetDiscoverMovieResponse movieDiscoverResponse = new GetDiscoverMovieResponse();

        GetDiscoverMovieResponseEvent event = new GetDiscoverMovieResponseEvent(movieDiscoverResponse);
        event.setHttpStatusCode(HttpResponseStatus.ERROR_REQUEST_TIMEOUT);
        Bus bus = BusProvider.getInstance();
        bus.post(event);
    }



}
