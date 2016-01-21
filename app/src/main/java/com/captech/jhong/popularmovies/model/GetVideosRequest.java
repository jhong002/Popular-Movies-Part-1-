package com.captech.jhong.popularmovies.model;

import android.content.Context;

import com.captech.jhong.popularmovies.AppConstants;
import com.captech.jhong.popularmovies.bus.BusProvider;
import com.captech.jhong.popularmovies.bus.GetVideosResponseEvent;
import com.captech.jhong.popularmovies.network.HttpResponseStatus;
import com.captech.jhong.popularmovies.network.NetworkParams;
import com.captech.jhong.popularmovies.network.TheMovieDbService;
import com.captech.jhong.popularmovies.network.request.NetworkRequest;
import com.captech.jhong.popularmovies.network.request.NetworkRequestSender;
import com.squareup.otto.Bus;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by jhong on 1/11/16.
 */
public class GetVideosRequest extends NetworkRequest implements Callback<GetVideosResponse> {

    private static class GetVideosRequestParams extends NetworkParams {
        private int id;
    }

    public static class Builder {
        private final String senderTag;
        private Boolean isAsync;
        private final GetVideosRequestParams mParams;

        public Builder(NetworkRequestSender networkRequestSender) {
            super();
            this.senderTag = networkRequestSender != null ? networkRequestSender.getSenderTag() : null;
            mParams = new GetVideosRequestParams();
        }

        public Builder setIsAsync(Boolean isAsync) {
            this.isAsync = isAsync;
            return this;
        }

        public Builder setId(int id) {
            mParams.id = id;
            return this;
        }

        public GetVideosRequest build() {
            return new GetVideosRequest(senderTag, isAsync, mParams);
        }
    }

    private GetVideosRequest(String senderTag, Boolean isAsync, NetworkParams networkParams) {
        super(senderTag, isAsync, networkParams);
    }

    @Override
    public void makeNetworkRequest(Retrofit restAdapter, Context context) {
        GetVideosRequestParams params = (GetVideosRequestParams) getNetworkParams();
        TheMovieDbService service = restAdapter.create(TheMovieDbService.class);

        Boolean isAsync = getIsAsync();
        Call<GetVideosResponse> call = service.getVideos(params.id, AppConstants.API_KEY);

        if (isAsync != null && isAsync) {
            call.enqueue(this);
        }
        else {
            try {
                Response<GetVideosResponse> response = call.execute();
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
    public void onResponse(Response<GetVideosResponse> response, Retrofit retrofit) {
        GetVideosResponse videosResponse = response.body();
        if (videosResponse == null) {
            videosResponse = new GetVideosResponse();
        }
        Bus bus = BusProvider.getInstance();

        if (response.isSuccess()) {
            GetVideosResponseEvent event = new GetVideosResponseEvent(videosResponse);
            event.setHttpStatusCode(response.code());
            bus.post(event);
        } else {
            GetVideosResponseEvent event = new GetVideosResponseEvent(videosResponse);
            event.setHttpStatusCode(response.code());
            bus.post(event);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        GetVideosResponse videosResponse = new GetVideosResponse();

        GetVideosResponseEvent event = new GetVideosResponseEvent(videosResponse);
        event.setHttpStatusCode(HttpResponseStatus.ERROR_REQUEST_TIMEOUT);
        Bus bus = BusProvider.getInstance();
        bus.post(event);
    }

}