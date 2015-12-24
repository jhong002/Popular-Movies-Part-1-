package com.captech.jhong.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MoreInfoFragment extends Fragment implements OnClickListener {

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



}
