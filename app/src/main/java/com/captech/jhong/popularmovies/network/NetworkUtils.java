package com.captech.jhong.popularmovies.network;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.captech.jhong.popularmovies.network.request.NetworkRequest;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static synchronized boolean queueNetworkRequest(NetworkRequest networkRequest) {
        return RequestQueue.queueRequest(networkRequest);
    }

    public static synchronized void startNetworkService(Context context) {
        if (context != null) {
            context.startService(new Intent(context, NetworkService.class));
        }
    }

    public static synchronized boolean isNetworkConnected(Context context) {

        if (context != null) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                    return true;
                }
            }
        }
        return false;
    }
}