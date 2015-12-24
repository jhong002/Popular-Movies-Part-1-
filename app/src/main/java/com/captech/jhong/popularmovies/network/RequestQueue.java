package com.captech.jhong.popularmovies.network;

import com.captech.jhong.popularmovies.network.request.NetworkRequest;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jhong on 12/17/15.
 */
public class RequestQueue extends LinkedBlockingQueue<NetworkRequest> {
    private static final int INITIAL_QUEUE_CAPACITY = 10;

    private static RequestQueue sInstance;

    private RequestQueue() {
        super(INITIAL_QUEUE_CAPACITY);
    }

    private synchronized static RequestQueue getInstance() {
        if (sInstance == null) {
            sInstance = new RequestQueue();
        }
        return sInstance;
    }


    public synchronized static boolean queueRequest(NetworkRequest networkRequest) {
        return getInstance().add(networkRequest);
    }

    public synchronized static NetworkRequest pollRequest() {
        return getInstance().poll();
    }

}

