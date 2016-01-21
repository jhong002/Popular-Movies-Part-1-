package com.captech.jhong.popularmovies.model;

import android.content.Context;

import com.captech.jhong.popularmovies.AppConstants;
import com.captech.jhong.popularmovies.bus.BusProvider;
import com.captech.jhong.popularmovies.bus.GetReviewsResponseEvent;
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
public class GetReviewsRequest extends NetworkRequest implements Callback<GetReviewsResponse> {

    private static class GetReviewsRequestParams extends NetworkParams {
        private int id;
    }

    public static class Builder {
        private final String senderTag;
        private Boolean isAsync;
        private final GetReviewsRequestParams mParams;

        public Builder(NetworkRequestSender networkRequestSender) {
            super();
            this.senderTag = networkRequestSender != null ? networkRequestSender.getSenderTag() : null;
            mParams = new GetReviewsRequestParams();
        }

        public Builder setIsAsync(Boolean isAsync) {
            this.isAsync = isAsync;
            return this;
        }

        public Builder setId(int id) {
            mParams.id = id;
            return this;
        }

        public GetReviewsRequest build() {
            return new GetReviewsRequest(senderTag, isAsync, mParams);
        }
    }

    private GetReviewsRequest(String senderTag, Boolean isAsync, NetworkParams networkParams) {
        super(senderTag, isAsync, networkParams);
    }

    @Override
    public void makeNetworkRequest(Retrofit restAdapter, Context context) {
        GetReviewsRequestParams params = (GetReviewsRequestParams) getNetworkParams();
        TheMovieDbService service = restAdapter.create(TheMovieDbService.class);

        Boolean isAsync = getIsAsync();
        Call<GetReviewsResponse> call = service.getReviews(params.id, AppConstants.API_KEY);

        if (isAsync != null && isAsync) {
            call.enqueue(this);
        }
        else {
            try {
                Response<GetReviewsResponse> response = call.execute();
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
    public void onResponse(Response<GetReviewsResponse> response, Retrofit retrofit) {
        GetReviewsResponse reviewsResponse = response.body();
        if (reviewsResponse == null) {
            reviewsResponse = new GetReviewsResponse();
        }
        Bus bus = BusProvider.getInstance();

        if (response.isSuccess()) {
            GetReviewsResponseEvent event = new GetReviewsResponseEvent(reviewsResponse);
            event.setHttpStatusCode(response.code());
            bus.post(event);
        } else {
            GetReviewsResponseEvent event = new GetReviewsResponseEvent(reviewsResponse);
            event.setHttpStatusCode(response.code());
            bus.post(event);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        GetReviewsResponse reviewsResponse = new GetReviewsResponse();
        GetReviewsResponseEvent event = new GetReviewsResponseEvent(reviewsResponse);
        event.setHttpStatusCode(HttpResponseStatus.ERROR_REQUEST_TIMEOUT);
        Bus bus = BusProvider.getInstance();
        bus.post(event);
    }

}
