package com.captech.jhong.popularmovies.bus;

import com.captech.jhong.popularmovies.model.GetVideosResponse;
import com.captech.jhong.popularmovies.network.HttpResponseStatus;

/**
 * Created by jhong on 1/11/16.
 */
public class GetVideosResponseEvent {
    private GetVideosResponse mNetworkResponse;
    private Integer httpStatusCode;


    public GetVideosResponseEvent (GetVideosResponse response){
        mNetworkResponse = response;
    }

    public GetVideosResponse getNetworkResponse() {
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
