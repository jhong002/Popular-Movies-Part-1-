package com.captech.jhong.popularmovies;

/**
 * Created by jhong on 12/17/15.
 */
public class GetDiscoverMovieResponseEvent {
    private GetDiscoverMovieResponse mNetworkResponse;
    private Integer httpStatusCode;


    public GetDiscoverMovieResponseEvent (GetDiscoverMovieResponse response){
        mNetworkResponse = response;
    }

    public GetDiscoverMovieResponse getNetworkResponse() {
        return mNetworkResponse;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public boolean isHttpStatusCodeSuccess() {
        return HttpResponseStatus.isHttpStatusCodeSuccess(httpStatusCode);
    }
}
