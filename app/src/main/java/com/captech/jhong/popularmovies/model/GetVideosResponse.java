package com.captech.jhong.popularmovies.model;

import com.captech.jhong.popularmovies.network.VideosResults;
import com.captech.jhong.popularmovies.network.response.NetworkResponse;

import java.util.List;

/**
 * Created by jhong on 1/11/16.
 */
public class GetVideosResponse implements NetworkResponse{
    private int id;
    private List<VideosResults> mResults;

    public int getId() {
        return id;
    }

    public List<VideosResults> getResults() {
        return mResults;
    }
}
