package com.captech.jhong.popularmovies.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.captech.jhong.popularmovies.R;
import com.captech.jhong.popularmovies.bus.BusProvider;
import com.captech.jhong.popularmovies.bus.GetDiscoverMovieResponseEvent;
import com.captech.jhong.popularmovies.bus.GetReviewsResponseEvent;
import com.captech.jhong.popularmovies.model.GetDiscoverMovieResponse;
import com.captech.jhong.popularmovies.model.GetReviewsResponse;
import com.captech.jhong.popularmovies.model.PopularMoviesCache;
import com.captech.jhong.popularmovies.model.Result;
import com.captech.jhong.popularmovies.network.NetworkUtils;
import com.captech.jhong.popularmovies.network.RequestQueueUtils;
import com.captech.jhong.popularmovies.network.ReviewsResult;
import com.captech.jhong.popularmovies.network.request.NetworkRequestSender;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

public class MoreInfoFragment extends Fragment implements OnClickListener, NetworkRequestSender {

    private static final String KEY_ARGS_POSITION = "KEY_ARGS_POSITION";
    public static final String TAG = HomeFragment.class.getSimpleName();
    public Result mResult;
    public TextView mTitle, mSummary, mRelease, mRating;

    public static MoreInfoFragment newInstance(int position) {
        MoreInfoFragment moreInfoFragment = new MoreInfoFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_ARGS_POSITION, position);
        moreInfoFragment.setArguments(args);

        return moreInfoFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_more_info, container, false);
        mTitle = (TextView) rootView.findViewById(R.id.movie_title_tv);
        mSummary = (TextView) rootView.findViewById(R.id.movie_summary_tv);
        mRelease = (TextView) rootView.findViewById(R.id.movie_release_tv);
        mRating = (TextView) rootView.findViewById(R.id.movie_rating_tv);

        // Register bus, unregister onDestroyView
        Bus bus = BusProvider.getInstance();
        bus.register(this);


        Bundle args = getArguments();
        int position = 0;
        if (args != null){
            position = args.getInt(KEY_ARGS_POSITION);
        }

        GetDiscoverMovieResponseEvent event = PopularMoviesCache.getGetDiscoverMovieResponseEvent();
        if (event != null){
            GetDiscoverMovieResponse response = event.getNetworkResponse();
            if (response != null){
                List<Result> resultList = response.getResults();
                if (resultList != null){
                    mResult = resultList.get(position);
                    Context context = getContext();
                    if (context != null) {
                        Log.i("POPULAR MOVIES", "FIRED");
                        RequestQueueUtils.queueReviewsRequest(mResult.getId(), this);
                        NetworkUtils.startNetworkService(context);
                    }
                }
            }
        }


        setText();
        return rootView;
    }


    private void setText(){
        if (mResult != null){
            mTitle.setText(mResult.getTitle());
            mSummary.setText(mResult.getOverview());
            mRating.setText(Float.toString(mResult.getVoteAverage()));
            mRelease.setText(mResult.getReleaseDate());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }

    @Override
    public String getSenderTag() {
        return TAG;
    }

    @Subscribe
    public void handleGetReviewsRequest(GetReviewsResponseEvent event){
        if (event.isHttpStatusCodeSuccess()){
            GetReviewsResponse response = event.getNetworkResponse();
            //Display grid
            List<ReviewsResult> results = response.getResults();
            for (ReviewsResult result : results){
                if (result != null) {
                    String content = result.getContent();
                    Log.i("POPULAR MOVIES", content);

                }
            }
        }
        else{

        }

    }
}
