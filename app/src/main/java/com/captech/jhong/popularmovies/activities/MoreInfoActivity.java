package com.captech.jhong.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.captech.jhong.popularmovies.AppConstants;
import com.captech.jhong.popularmovies.fragments.MoreInfoFragment;
import com.captech.jhong.popularmovies.model.GetDiscoverMovieResponse;
import com.captech.jhong.popularmovies.bus.GetDiscoverMovieResponseEvent;
import com.captech.jhong.popularmovies.model.PopularMoviesCache;
import com.captech.jhong.popularmovies.R;
import com.captech.jhong.popularmovies.model.Result;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoreInfoActivity extends AppCompatActivity implements Callback{
    private Toolbar mToolbar;
    private ImageView mToolbarImage;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Result mResult;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        Intent intent = getIntent();
        mPosition = intent.getIntExtra("position", 0);

        if (savedInstanceState == null){
            MoreInfoFragment moreInfoFragment = MoreInfoFragment.newInstance(mPosition);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fl_more_info_activity, moreInfoFragment);
            transaction.commitAllowingStateLoss();
        }

        GetDiscoverMovieResponseEvent event = PopularMoviesCache.getGetDiscoverMovieResponseEvent();
        if (event != null){
            GetDiscoverMovieResponse response = event.getNetworkResponse();
            if (response != null){
                List<Result> resultList = response.getResults();
                if (resultList != null){
                    mResult = resultList.get(mPosition);
                }
            }
        }
        initToolbar(getString(R.string.app_name), true);

    }


    private void initToolbar(String title, boolean homeUpEnabled) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarImage = (ImageView) findViewById(R.id.collapsableImage);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        if (mCollapsingToolbarLayout != null) {
            if (mResult != null){
                mCollapsingToolbarLayout.setTitle(mResult.getTitle());
            }
            else{
                mCollapsingToolbarLayout.setTitle(title);
            }
            mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
            setSupportActionBar(mToolbar);
        }

        if (mToolbarImage != null){
            String pictureUrl = AppConstants.PICTURE_BASE_URL_500 + mResult.getPosterPath();
            Picasso.with(this).load(pictureUrl).placeholder(R.drawable.placeholder).into(mToolbarImage, this);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(homeUpEnabled);
        }
    }



    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }
}
