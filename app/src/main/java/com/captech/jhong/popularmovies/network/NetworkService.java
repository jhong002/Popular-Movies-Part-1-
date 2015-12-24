package com.captech.jhong.popularmovies.network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.captech.jhong.popularmovies.BuildConfig;
import com.captech.jhong.popularmovies.network.request.NetworkRequest;
import com.squareup.okhttp.OkHttpClient;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class NetworkService extends IntentService {
    private static final String TAG = NetworkService.class.getSimpleName();

    private static final int CONNECT_TIMEOUT_MS = 10 * 1000;
    private static final int READ_TIMEOUT_MS = 10 * 1000;

    private HashMap<String, Retrofit> mRetrofits;
    private OkHttpClient mOkHttpClient;


    public NetworkService() {
        super(TAG);
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Log.v(TAG, "onCreate");
        }

        mRetrofits = new HashMap<>();
        mOkHttpClient = new OkHttpClient();

        mOkHttpClient.setConnectTimeout(CONNECT_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        mOkHttpClient.setReadTimeout(READ_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        mOkHttpClient.setFollowSslRedirects(true);
        mOkHttpClient.setCookieHandler(NetworkServiceUtils.getCookieManagerInstance());

    }

    /**
     * This method runs off the main UI thread, so it is OK to block and do long operations here.
     * This method will process every network request from the queue in the order of their priority
     * and make their corresponding network call, in a synchronous or asynchronous manner, depending
     * on the request settings. After all requests in the request queue have been sent, the service
     * destroys.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "onHandleIntent");
        }

        Context applicationContext = getApplicationContext();
        NetworkRequest networkRequest;

        while ((networkRequest = RequestQueue.pollRequest()) != null) {

            String baseUrl = networkRequest.getBaseUrl();

            // Use a unique key for each server URL and header interceptor combination
            String requestAdapterKey = baseUrl + "_" + TheMovieDbService.class.getSimpleName();
            Retrofit retrofit = mRetrofits.get(requestAdapterKey);

            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .client(mOkHttpClient)
                         .addConverterFactory(GsonConverterFactory.create())
                        .build();

                mRetrofits.put(requestAdapterKey, retrofit);
            }

            networkRequest.makeNetworkRequest(retrofit, applicationContext);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (BuildConfig.DEBUG) {
            Log.v(TAG, "onDestroy");
        }
    }



}