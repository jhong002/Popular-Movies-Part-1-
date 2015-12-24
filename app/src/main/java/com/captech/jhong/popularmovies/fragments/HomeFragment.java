package com.captech.jhong.popularmovies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.captech.jhong.popularmovies.AppConstants;
import com.captech.jhong.popularmovies.bus.BusProvider;
import com.captech.jhong.popularmovies.model.GetDiscoverMovieRequest;
import com.captech.jhong.popularmovies.model.GetDiscoverMovieResponse;
import com.captech.jhong.popularmovies.bus.GetDiscoverMovieResponseEvent;
import com.captech.jhong.popularmovies.adapters.HomePageAdapter;
import com.captech.jhong.popularmovies.network.HttpResponseStatus;
import com.captech.jhong.popularmovies.network.request.NetworkRequestSender;
import com.captech.jhong.popularmovies.network.NetworkUtils;
import com.captech.jhong.popularmovies.model.PopularMoviesApp;
import com.captech.jhong.popularmovies.model.PopularMoviesCache;
import com.captech.jhong.popularmovies.R;
import com.captech.jhong.popularmovies.network.RequestQueueUtils;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by jhong on 12/15/15.
 */
public class HomeFragment extends Fragment implements OnClickListener, NetworkRequestSender, OnRefreshListener {

    public static final String TAG = HomeFragment.class.getSimpleName();
    private final static String KEY_STATE_SORT = "KEY_STATE_SORT";
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mError;
    private String mSelectedSort;

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();

        Bundle args = new Bundle();
        homeFragment.setArguments(args);

        return homeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_home);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress);
        mError = (TextView) rootView.findViewById(R.id.check_internet);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fragment_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //Default is by popularity
        mSelectedSort = GetDiscoverMovieRequest.POPULARITY;

        Bus bus = BusProvider.getInstance();
        bus.register(this);

        setListeners();

        if (savedInstanceState == null) {
            fireGetDiscoverMoveRequest();
        }
        else{
            mSelectedSort = (String) savedInstanceState.get(KEY_STATE_SORT);
            GetDiscoverMovieResponseEvent event = PopularMoviesCache.getGetDiscoverMovieResponseEvent();
            if (event != null && event.isHttpStatusCodeSuccess()){
                initGrid();
                mAdapter = new HomePageAdapter(event.getNetworkResponse());
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }
            else{
                fireGetDiscoverMoveRequest();
            }
        }
        return rootView;
    }
    private void initGrid(){
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        if(getContext() != null) {
            boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
            int spanCount = 0;
            if (tabletSize) {
                spanCount = AppConstants.SPAN_COUNT_TABLET;
            } else {
                spanCount = AppConstants.SPAN_COUNT_PHONE;
            }
            mLayoutManager = new GridLayoutManager(getContext(), spanCount);
        }
        mRecyclerView.setLayoutManager(mLayoutManager);

    }


    private void setListeners(){
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }

    @Subscribe
    public void handleGetDiscoverMovieResponse(GetDiscoverMovieResponseEvent event){
        mProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        if (event.isHttpStatusCodeSuccess()){
            GetDiscoverMovieResponse response = event.getNetworkResponse();
            PopularMoviesCache.setDiscoverMovieResponseEvent(event);
            //Display grid
            initGrid();

            mAdapter = new HomePageAdapter(response);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        else{
            PopularMoviesCache.setDiscoverMovieResponseEvent(event);
            mError.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public String getSenderTag() {
        return TAG;
    }

    @Override
    public void onRefresh() {
        //mProgressBar.setVisibility(View.VISIBLE);
        mError.setVisibility(View.GONE);
        fireGetDiscoverMoveRequest();
    }

    private void fireGetDiscoverMoveRequest(){
        if (NetworkUtils.isNetworkConnected(PopularMoviesApp.getInstance())) {
            mError.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.INVISIBLE);

            RequestQueueUtils.queueDiscoverMovieRequest(mSelectedSort, this);
            NetworkUtils.startNetworkService(getContext());
        }
        else{
            mProgressBar.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            mRecyclerView.setVisibility(View.INVISIBLE);
            mError.setVisibility(View.VISIBLE);

            // Put failed response into cache
            GetDiscoverMovieResponse movieDiscoverResponse = new GetDiscoverMovieResponse();
            GetDiscoverMovieResponseEvent event = new GetDiscoverMovieResponseEvent(movieDiscoverResponse);
            event.setHttpStatusCode(HttpResponseStatus.ERROR_REQUEST_TIMEOUT);
            PopularMoviesCache.setDiscoverMovieResponseEvent(event);

        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.sort_popularity:
                mSelectedSort = GetDiscoverMovieRequest.POPULARITY;
                break;
            case R.id.sort_rating:
                mSelectedSort = GetDiscoverMovieRequest.RATING;
                break;
        }
        mProgressBar.setVisibility(View.VISIBLE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        fireGetDiscoverMoveRequest();
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_STATE_SORT, mSelectedSort);
    }
}
