package com.captech.jhong.popularmovies;

import android.content.Context;

import retrofit.Retrofit;

/**
 * Created by jhong on 12/17/15.
 */
public abstract class NetworkRequest {
    private final Boolean mIsAsync;
    private final String mSenderTag;
    private NetworkParams mNetworkParams;


    protected NetworkRequest(String senderTag, Boolean isAsync, NetworkParams networkParams) {
        super();
        mSenderTag = senderTag;
        mIsAsync = (isAsync != null) ? isAsync : false;
        mNetworkParams = networkParams;
    }

    public abstract void makeNetworkRequest(Retrofit restAdapter, Context context);

    protected NetworkParams getNetworkParams() {
        return mNetworkParams;
    }

    protected String getSenderTag() {
        return mSenderTag;
    }

    protected Boolean getIsAsync() {
        return mIsAsync;
    }

    public abstract String getBaseUrl();


    }
